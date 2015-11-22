package de.stpnb.stpnb.parser;

import de.stpnb.stpnb.antlr.StpLexer;
import de.stpnb.stpnb.antlr.StpParser;
import de.stpnb.stpnb.antlr.StpParser.TemplateContext;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

public class SilverstripeParser extends Parser {

    private Snapshot snapshot;

    private StpParser parser;

    private TemplateContext templateContext;

    private List<SilverstripeParserException> parserExceptions;

    public SilverstripeParser() {}

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
        this.snapshot = snapshot;
        parserExceptions = new ArrayList<>();
        ANTLRInputStream is = new ANTLRInputStream(snapshot.getText().toString());
        StpLexer lexer = new StpLexer(is);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        parser = new StpParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new SilverstripeParserErrorListener());
        try {
            templateContext = parser.template();
        } catch(SilverstripeParserException ex) {
            parserExceptions.add(ex);
        } catch (RuntimeException e) {
            e.getStackTrace();
        }
    }

    @Override
    public Result getResult(Task task) throws ParseException {
        return new SilverstripeParserResult(snapshot, templateContext, parser, parserExceptions);
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

    public static class SilverstripeParserResult extends ParserResult {

        private boolean valid = true;
        private final TemplateContext templateContext;
        private final StpParser parser;
        private final List<SilverstripeParserException> parserExceptions;

        public SilverstripeParserResult(Snapshot snapshot, TemplateContext templateContext, StpParser parser, List<SilverstripeParserException> parserExceptions) {
            super(snapshot);
            this.templateContext = templateContext;
            this.parser = parser;
            this.parserExceptions = parserExceptions;
        }
        
        
        
        @Override
        public List<? extends Error> getDiagnostics() {
            return new ArrayList<>();
        }

        public List<SilverstripeParserException> getParserExceptions() {
            return parserExceptions;
        }

        @Override
        protected void invalidate() {
            this.valid = false;
        }

        public TemplateContext getTemplateContext() {
            return templateContext;
        }

        
        
    }

}
