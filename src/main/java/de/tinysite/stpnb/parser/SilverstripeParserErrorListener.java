package de.tinysite.stpnb.parser;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


public class SilverstripeParserErrorListener extends BaseErrorListener {

    private static final Logger LOG = Logger.getLogger(SilverstripeParserErrorListener.class.getSimpleName());
    
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
            String msg, RecognitionException e) 
    {
        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        LOG.log(Level.WARNING, "rule stack: " + stack);
        LOG.log(Level.WARNING, "line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg);
        if(offendingSymbol instanceof CommonToken ) {
            throw new SilverstripeParserException(line, charPositionInLine, e, (CommonToken) offendingSymbol);
        }
    }

}
