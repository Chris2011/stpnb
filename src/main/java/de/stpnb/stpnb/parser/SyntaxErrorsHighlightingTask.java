package de.stpnb.stpnb.parser;

import de.stpnb.stpnb.parser.SilverstripeParser.SilverstripeParserResult;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.antlr.v4.runtime.CommonToken;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.text.NbDocument;


public class SyntaxErrorsHighlightingTask extends ParserResultTask {

    private static final int PRIORITY = 100;

    @Override
    public void run(Parser.Result result, SchedulerEvent event) {
        SilverstripeParserResult parserResult = (SilverstripeParserResult) result;
        List<SilverstripeParserException> exceptions = parserResult.getParserExceptions();
        Document document = result.getSnapshot().getSource().getDocument(false);
        List<ErrorDescription> errors = new ArrayList<>();
        for(SilverstripeParserException ex:exceptions) {
            CommonToken token = ex.getToken();
            int start = NbDocument.findLineOffset((StyledDocument) document, token.getLine()-1) + token.getCharPositionInLine();
            int tokenLength = 0;
            if(token.getText() != null && token.getType() != -1) {
                tokenLength = token.getText().length();
            }
            int end = start + tokenLength;
            try {
                ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
                        Severity.ERROR,
                        ex.getMessage() != null ? ex.getMessage() : "Non-parseable source",
                        document,
                        document.createPosition(start),
                        document.createPosition(end)
                );
                errors.add(errorDescription);
            } catch (BadLocationException ex1) {
                //TODO Fix this
                System.out.println("Bad location at:"  + ex1.getMessage());
                throw new RuntimeException(ex1.getMessage());
            }
        }
        HintsController.setErrors(document, "ss-template", errors);
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    @Override
    public void cancel() {
        //do nothing.
    }

}
