package de.tinysite.stpnb.antlr.visitor;

import de.tinysite.stpnb.antlr.StpParser;
import de.tinysite.stpnb.antlr.StpParserBaseVisitor;
import de.tinysite.stpnb.lexer.SilverstripeTokenId;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class BraceMatchingVisitor extends StpParserBaseVisitor<Object> {

    private final List<Integer> matchedRegions = new ArrayList<>();
    
    public List<Integer> getMatchedRegions() {
        return matchedRegions;
    }
    
    private final int position;

    public BraceMatchingVisitor(int position) {
        this.position = position;
    }
    
    @Override
    public Object visitPairedTag(StpParser.PairedTagContext ctx) {
        RuleContext tag = (RuleContext) ctx.getChild(0);
        Token firstOTag =  searchTokenFromHead(tag, SilverstripeTokenId.OTAG);
        Token firstCTag = searchTokenFromHead(tag, SilverstripeTokenId.CTAG);
        Token secondOTag = searchTokenFromTail(tag, SilverstripeTokenId.OTAG);
        Token secondCTag = searchTokenFromTail(tag, SilverstripeTokenId.CTAG);
        boolean offsetMatched = (position >= firstOTag.getStartIndex() && position <= firstCTag.getStopIndex()) ||
                (position >= secondOTag.getStartIndex() && position <= secondCTag.getStopIndex());
        if(offsetMatched) {
            matchedRegions.add(firstOTag.getStartIndex());
            matchedRegions.add(firstCTag.getStopIndex()+1);
            matchedRegions.add(secondOTag.getStartIndex());
            matchedRegions.add(secondCTag.getStopIndex()+1);
        }
        return super.visitPairedTag(ctx);
    }
    
    private Token searchTokenFromHead(RuleContext ctx, SilverstripeTokenId token) {
        for(int i=0; i<ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if(child instanceof TerminalNode && ((TerminalNode)child).getSymbol().getType() == token.getId()) {
                return ((TerminalNode)child).getSymbol();
            }
        }
        throw new IllegalArgumentException("Illegal arguments");
    }

    private Token searchTokenFromTail(RuleContext ctx, SilverstripeTokenId token) {
        for(int i=ctx.getChildCount()-1; i>=0; i--) {
            ParseTree child = ctx.getChild(i);
            if(child instanceof TerminalNode && ((TerminalNode)child).getSymbol().getType() == token.getId()) {
                return ((TerminalNode)child).getSymbol();
            }
        }
        throw new IllegalArgumentException("Illegal arguments");
    }
}
