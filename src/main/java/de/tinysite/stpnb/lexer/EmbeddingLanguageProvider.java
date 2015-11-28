package de.tinysite.stpnb.lexer;

import static com.google.common.base.Preconditions.checkNotNull;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service=LanguageProvider.class)
public class EmbeddingLanguageProvider extends LanguageProvider {

    private Language embeddedLanguage;
    
    @Override
    public Language<?> findLanguage(String mimeType) {
        return null;
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath languagePath, InputAttributes inputAttributes) {
        if(embeddedLanguage == null) {
            initLanguage();
        }
        if(token.id() instanceof SilverstripeTokenId && token.text() != null) {
            SilverstripeTokenId silverstripeToken = (SilverstripeTokenId)token.id();
            if(silverstripeToken == SilverstripeTokenId.TEXT) {
                return LanguageEmbedding.create(embeddedLanguage, 0, 0, true);
            }
        }
        return null;
    }

    private void initLanguage() {
        embeddedLanguage = MimeLookup.getLookup("text/html").lookup(Language.class);
        checkNotNull(embeddedLanguage, "html support must be enabled");
    }

}
