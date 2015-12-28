package de.tinysite.stpnb.antlr.visitor;

import de.tinysite.stpnb.antlr.StpParser;
import de.tinysite.stpnb.antlr.StpParserBaseVisitor;
import de.tinysite.stpnb.type.CharRange;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;


public class FoldVisitor  extends StpParserBaseVisitor<Object> {

    private final List<CharRange> folds = new ArrayList<>();

    @Override
    public Object visitPairedTag(StpParser.PairedTagContext ctx) {
        addToFolds(ctx);
        return super.visitPairedTag(ctx);
    }

    public List<CharRange> getFolds() {
        return folds;
    }

    private void addToFolds(ParserRuleContext ctx) {
        if(ctx.getStart().getLine() == ctx.getStop().getLine()) {
            return;
        }
        final CharRange range = new CharRange(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
        folds.add(range);
    }

}
