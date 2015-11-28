package de.tinysite.stpnb.parser;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;


public class SilverstripeParserException extends RuntimeException {
    
    private final int line;
    private final int posInLine;
    private final transient RecognitionException ex;
    private final transient CommonToken token;

    public SilverstripeParserException(int line, int posInLine, RecognitionException ex, CommonToken token) {
        this.line = line;
        this.posInLine = posInLine;
        this.ex = ex;
        this.token = token;
    }

    public int getLine() {
        return line;
    }

    public int getPosInLine() {
        return posInLine;
    }

    public RecognitionException getEx() {
        return ex;
    }

    public CommonToken getToken() {
        return token;
    }
}
