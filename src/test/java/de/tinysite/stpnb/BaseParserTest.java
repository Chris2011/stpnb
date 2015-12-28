package de.tinysite.stpnb;

import de.tinysite.stpnb.antlr.StpLexer;
import de.tinysite.stpnb.antlr.StpParser;
import de.tinysite.stpnb.parser.SilverstripeParserErrorListener;
import de.tinysite.stpnb.parser.SilverstripeParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.junit.Assert.fail;


public abstract class BaseParserTest {
    private static final Logger LOG = Logger.getLogger(BaseParserTest.class.getSimpleName());

    protected StpParser getParser(String inputString) {
        ANTLRInputStream input;
        InputStream is = IOUtils.toInputStream(inputString);    
        try {
            input = new ANTLRInputStream(is);
        } catch (IOException ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            throw new IllegalArgumentException("Could not read input string");
        }
        StpLexer lexer = new StpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new StpParser(tokens);
    }

    protected String getParserTree(String inputString) {
        StpParser parser = getParser(inputString);
        parser.removeErrorListeners();
        parser.addErrorListener(new SilverstripeParserErrorListener());
        return parser.template().toStringTree(parser);
    }

}
