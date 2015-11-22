package de.stpnb.stpnb;

import de.stpnb.stpnb.antlr.StpLexer;
import de.stpnb.stpnb.antlr.StpParser;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.IOUtils;


public abstract class StpParserTest {
    protected StpParser getParser(String inputString) {
        ANTLRInputStream input;
        InputStream is = IOUtils.toInputStream(inputString);    
        try {
            input = new ANTLRInputStream(is);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not read input string");
        }
        StpLexer lexer = new StpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new StpParser(tokens);
    }

    protected String getParserTree(String inputString) {
        StpParser parser = getParser(inputString);
        return parser.template().toStringTree(parser);
    }

}
