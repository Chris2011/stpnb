package de.tinysite.stpnb.hyperlink;

import com.google.common.base.Optional;
import de.tinysite.stpnb.antlr.StpLexer;
import de.tinysite.stpnb.antlr.StpParser;
import de.tinysite.stpnb.lexer.SilverstripeTokenId;
import de.tinysite.stpnb.type.Resource;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.editor.BaseDocument;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;


@MimeRegistration(mimeType="text/x-ss", service = HyperlinkProvider.class)
public class ResourceLinkProvider  implements HyperlinkProvider {

    private static final String TEMPLATES_FOLDER_NAME = "templates";
    private static final String INCLUDES_FOLDER_NAME = "Includes";
    private static final String THEMES_FOLDER_NAME = "themes";    
    
    private Resource type;
    private String target;
    private int targetStart;
    private int targetEnd;

    @Override
    public boolean isHyperlinkPoint(Document doc, int offset) {
        return verifyAndCalculateState(doc, offset);    
    }


    @Override
    public int[] getHyperlinkSpan(Document doc, int offset) {
        if(verifyAndCalculateState(doc, offset)) {
            return new int[]{targetStart, targetEnd};
        }
        return new int[]{};
    }

    @Override
    public void performClickAction(Document doc, int offset) {
        if(verifyAndCalculateState(doc, offset)) {
            FileObject fo;
            switch(type) {
                case TEMPLATE:
                    fo = findInclude(doc, target);
                    break;
                case JAVASCRIPT:
                    fo = findResource(doc, target);
                    break;

                case CSS:
                    fo = findResource(doc, target);
                    break;
                   
                case THEMED_CSS:
                    fo = findThemedCss(doc, target);
                    break;

                default:
                    return;
            }

            if(null != fo) {
                openFile(fo);
            }
        }
    }

    public void openFile(FileObject file) {
        DataObject fileDo;
        try {
            fileDo = DataObject.find(file);
            EditCookie editCookie = fileDo.getLookup().lookup(EditCookie.class);
            if(null != editCookie) {
                editCookie.edit();
            } else {
                OpenCookie openCookie = fileDo.getLookup().lookup(OpenCookie.class);
                if(null != openCookie) {
                    openCookie.open();
                }
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    
    private boolean verifyAndCalculateState(Document doc, int offset) {
        if(!(doc instanceof BaseDocument)) {
            return false;
        } 
        TokenSequence<SilverstripeTokenId> ts = null;
        ((BaseDocument)doc).readLock();
        try {
            TokenHierarchy th = TokenHierarchy.get(doc);
            ts = th.tokenSequence(SilverstripeTokenId.getLanguage());
        } finally {
            ((BaseDocument)doc).readUnlock();
        }
        if(ts == null) {
            return false;    
        }
        ts.move(offset);
        ts.moveNext();
        target = ts.token().text().toString();
        targetStart = ts.offset();
        targetEnd = targetStart + ts.token().length();
        move(ts, -1);
        Token<SilverstripeTokenId> token = ts.token();
        if(token.id().getId() == StpParser.INCLUDE) {
            type = Resource.TEMPLATE;
            move(ts, 1);
            return true;
            
        }
        move(ts, -2);
        if(ts.token().id().getId() == StpParser.REQUIRE) {
            move(ts, 1);
            type = Resource.fromString(ts.token().text().toString());
            return true;
        }

        return false;
    }

    private FileObject findInclude(Document doc, String includeName) {
        Optional<FileObject> templateDir = getParentWithName(doc , TEMPLATES_FOLDER_NAME);
        if(!templateDir.isPresent()) {
            return null;
        }
        FileObject fo = templateDir.get().getFileObject(INCLUDES_FOLDER_NAME + getSeparator() + includeName + ".ss");
        if(null != fo && !fo.isFolder()) {
            return fo;
        }
        return null;
        
    }

    private FileObject findResource(final Document doc, final String target) {
        final FileObject src = NbEditorUtilities.getFileObject(doc);
        if(null == src) {
            return null;
        }
        final String projectDir = getProjectDirectory(src).getPath();
        final String fileName = removeQuotes(target);
        final Path p = Paths.get(fileName);
        FileObject docDir = src.getParent();
        while(null != docDir) {
            final Path currentPath = Paths.get(docDir.getPath());
            final Path resolved = currentPath.resolve(p);
            if(Files.exists(resolved)) {
                return FileUtil.toFileObject(resolved.toFile());
            }
            if(projectDir.equals(docDir.getPath())) {
                break;
            }
            docDir = docDir.getParent();
        }
        return null;
    }

    private FileObject findThemedCss(final Document doc, final String target) {
        final String fileName = removeQuotes(target);
        final FileObject src = NbEditorUtilities.getFileObject(doc);
        FileObject docDir = src.getParent();
        while(docDir != null) {
            if(null != docDir.getParent().getPath() && docDir.getParent().getName().equals(THEMES_FOLDER_NAME)) {
                Path themedCss = Paths.get(docDir.getPath() + getSeparator() + "css" + getSeparator() + fileName + ".css");
                if(Files.exists(themedCss)) {
                    return FileUtil.toFileObject(themedCss.toFile());
                }
            }
            docDir = docDir.getParent();
        }
        return null;
    }

    private static String removeQuotes(String str) {
        return str.replaceAll("^\"|\"$|^'|'$", ""); 
    }  

    private Optional<FileObject> getParentWithName(Document doc, String parentName) {
        FileObject fo = NbEditorUtilities.getFileObject(doc);
        if(null == fo) {
            return Optional.absent();
        }
        while(null != fo.getParent()) {
            if(fo.getParent().getName().equals(parentName)) {
                return Optional.of(fo.getParent());
            }
            fo = fo.getParent();
        }
        return Optional.absent();
    }

    private FileObject getProjectDirectory(FileObject fo) {
        return ProjectUtils.getInformation(FileOwnerQuery.getOwner(fo)).getProject().getProjectDirectory();
    }
    

    
    private void move(TokenSequence<SilverstripeTokenId> ts, int count) {
        if(count >= 0) {
            for(int i = 0; i < count; i++) {
                do {
                    ts.moveNext();
                } while(ts.token().id().getId() == StpLexer.WS);
            }
        } else {
            for(int i = count; i < 0; i++) {
                do {
                    ts.movePrevious();
                } while(ts.token().id().getId() == StpLexer.WS);
            }
        }
    }

    private static String getSeparator() {
        return FileSystems.getDefault().getSeparator();
    }

}
