package de.tinysite.stpnb.lexer;

import static com.google.common.base.Preconditions.checkNotNull;
import de.tinysite.stpnb.antlr.AntlrCharStream;
import de.tinysite.stpnb.antlr.StpLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.IntegerList;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;


public class SilverstripeLexer implements Lexer<SilverstripeTokenId>{

    private final StpLexer lexer;

    private final LexerRestartInfo<SilverstripeTokenId> lri;

    public SilverstripeLexer(LexerRestartInfo<SilverstripeTokenId> lri) {
        this.lri = lri;
        final AntlrCharStream charStream = new AntlrCharStream(lri.input(), "SilverstripeEditor");
        this.lexer = new StpLexer(charStream);
        applyLexerState(lri.state());
    }

    @Override
    public Token<SilverstripeTokenId> nextToken() {
        org.antlr.v4.runtime.Token token = lexer.nextToken();
        checkNotNull(token, "Token must not be null");
        if(token.getType() == CharStream.EOF) {
            return null;
        }
        return lri.tokenFactory().createToken(SilverstripeTokenId.valueOf(token.getType()));
    }

    @Override
    public Object state() {
        return new SilverstripeLexerState(lexer._mode, lexer._modeStack);
    }

    @Override
    public void release() {
        //PENDING
    }

    private void applyLexerState(Object state) {
        if(!(state instanceof SilverstripeLexerState)) {
            return;
        }
        SilverstripeLexerState lexerState = (SilverstripeLexerState)state;
        this.lexer._mode = lexerState.mode;
        this.lexer._modeStack.clear();
        this.lexer._modeStack.addAll(lexerState.modeStack);
    }

    private static class SilverstripeLexerState {
        private final int mode;
        private final IntegerList modeStack;

        public SilverstripeLexerState(int mode, IntegerList modeStack) {
            this.mode = mode;
            this.modeStack = new IntegerList();
            this.modeStack.addAll(modeStack);
        }
        
    }

}
