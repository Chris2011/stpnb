package de.stpnb.stpnb.parser;

import de.stpnb.stpnb.antlr.StpParser;
import de.stpnb.stpnb.antlr.StpParser.TemplateContext;
import de.stpnb.stpnb.type.CharRange;
import de.stpnb.stpnb.antlr.visitor.FoldVisitor;
import de.stpnb.stpnb.parser.SilverstripeParser.SilverstripeParserResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;


public class SilverstripeStructureScanner implements StructureScanner {

    @Override
    public List<? extends StructureItem> scan(ParserResult info) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, List<OffsetRange>> folds(ParserResult info) {
        Map<String, List<OffsetRange>> folds = new HashMap<>();
        SilverstripeParserResult result = (SilverstripeParserResult)info;
        if(!result.getParserExceptions().isEmpty()) {
            return folds;
        }

        TemplateContext templateContext = result.getTemplateContext();
        final String src = result.getSnapshot().getText().toString();
        FoldVisitor foldVisitor = new FoldVisitor();
        foldVisitor.visit(templateContext);
        List<CharRange> rawFolds = foldVisitor.getFolds();
        List<OffsetRange> ranges = new ArrayList<>();
        for(CharRange range: rawFolds) {
            ranges.add(new OffsetRange(findNextNl(src, range.getStart()), range.getStop()-1));
        }
        folds.put("othercodeblocks", ranges);
        return folds;
    }

    private int findNextNl(String src, int offset) {
        int pos = src.indexOf("\n", offset);
        return pos != -1 ? pos : offset;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

}
