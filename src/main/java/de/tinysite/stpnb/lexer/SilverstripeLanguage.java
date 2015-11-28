package de.tinysite.stpnb.lexer;

import de.tinysite.stpnb.parser.SilverstripeStructureScanner;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;


@LanguageRegistration(mimeType="text/x-ss")
public class SilverstripeLanguage extends DefaultLanguageConfig {

    @Override
    public boolean hasStructureScanner() {
        return true;
    }

    @Override
    public StructureScanner getStructureScanner() {
        return new SilverstripeStructureScanner();
    }

    

    @Override
    public Language getLexerLanguage() {
        return SilverstripeTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "SS";
    }

}
