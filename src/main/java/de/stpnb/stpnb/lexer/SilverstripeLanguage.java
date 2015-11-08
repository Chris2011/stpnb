package de.stpnb.stpnb.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;


@LanguageRegistration(mimeType="text/x-ss")
public class SilverstripeLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return SilverstripeTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "SS";
    }

}
