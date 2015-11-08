package de.stpnb.stpnb.lexer;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class SilverstripeLanguageHierarchy extends LanguageHierarchy<SilverstripeTokenId>{

    @Override
    protected Collection<SilverstripeTokenId> createTokenIds() {
        return EnumSet.allOf(SilverstripeTokenId.class);
    }

    @Override
    protected Lexer<SilverstripeTokenId> createLexer(LexerRestartInfo<SilverstripeTokenId> lri) {
        return new SilverstripeLexer(lri);
    }

    @Override
    protected String mimeType() {
        return "text/x-ss";
    }
}
