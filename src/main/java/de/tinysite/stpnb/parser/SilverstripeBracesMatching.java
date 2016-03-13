package de.tinysite.stpnb.parser;

import de.tinysite.stpnb.antlr.visitor.BraceMatchingVisitor;
import de.tinysite.stpnb.lexer.SilverstripeTokenId;
import de.tinysite.stpnb.parser.SilverstripeParser.SilverstripeParserResult;
import java.util.Collections;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apache.commons.lang3.ArrayUtils;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.parsing.api.ParserManager;
import org.netbeans.modules.parsing.api.ResultIterator;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.api.UserTask;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.BracesMatcherFactory;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;
import org.openide.util.Exceptions;

public class SilverstripeBracesMatching implements BracesMatcher, BracesMatcherFactory {
    
    private final MatcherContext context;

    public SilverstripeBracesMatching() {
        this(null);
    }
    public SilverstripeBracesMatching(MatcherContext context) {
        this.context = context;
    }
    
    @Override
    public int[] findOrigin() throws InterruptedException, BadLocationException {
        
        if(MatcherContext.isTaskCanceled()) {
            return null;
        }
        int offset = context.getSearchOffset();
        TokenHierarchy th = TokenHierarchy.get(context.getDocument());
        TokenSequence<SilverstripeTokenId> ts = th.tokenSequence(SilverstripeTokenId.getLanguage());
        if(ts == null) {
            return null;
        }
        ts.move(offset);
        if(ts.moveNext() && SilverstripeTokenId.hasMatches(ts.token().id())) {
            return new int[] {ts.token().offset(th), ts.token().offset(th) + ts.token().length()};
        }
        return null;
    }

    @Override
    public int[] findMatches() throws InterruptedException, BadLocationException {
        Document doc = context.getDocument();
        if(MatcherContext.isTaskCanceled()) {
            return null;
        }
        final Source source = Source.create(doc);
        if(source == null) {
            return null;
        }
        final int[][] ret = new int[1][];
        try {
            ParserManager.parse(Collections.singleton(source), new UserTask() {
                @Override
                public void run(ResultIterator resultIterator) throws Exception {
                    if(MatcherContext.isTaskCanceled()) {
                        return;
                    }
                    int searchOffset = context.getSearchOffset();
                    if(resultIterator == null) {
                        ret[0] = new int[]{searchOffset, searchOffset};
                        return;
                    }
                    SilverstripeParserResult parserResult = (SilverstripeParserResult)resultIterator.getParserResult();
                    if(!parserResult.getParserExceptions().isEmpty()) {
                        return;
                    }
                    BraceMatchingVisitor visitor = new BraceMatchingVisitor(searchOffset);
                    visitor.visit(parserResult.getTemplateContext());
                    if(visitor.getMatchedRegions().isEmpty()) {
                        ret[0] = new int[]{searchOffset, searchOffset};
                    } else {
                        ret[0] = ArrayUtils.toPrimitive(visitor.getMatchedRegions().toArray(new Integer[visitor.getMatchedRegions().size()]));
                    }
                }
                
            });
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        return ret[0];
    }

    @Override
    public BracesMatcher createMatcher(final MatcherContext context) {
        final SilverstripeBracesMatching[] ret = {null};
        context.getDocument().render(new Runnable() {
            @Override
            public void run() {
                TokenHierarchy<Document> hierarchy = TokenHierarchy.get(context.getDocument());
                if(hierarchy.tokenSequence().language() == SilverstripeTokenId.getLanguage()) {
                    ret[0] = new SilverstripeBracesMatching(context);
                }
            }
            
        });
        return ret[0];
    }

}
