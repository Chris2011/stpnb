package de.tinysite.stpnb;

import de.tinysite.stpnb.lexer.SilverstripeLexer;
import de.tinysite.stpnb.lexer.SilverstripeTokenId;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.junit.NbTestCase;


public class SilverstripeLexerBaseTest extends NbTestCase{

    public SilverstripeLexerBaseTest(String name) {
        super(name);
    }

    @Test
    public void testBaseTag() {
        TokenSequence<?> ts = seqForText("<% base_tag %>");
        assertEquals(3, ts.tokenCount());
        assertTrue(ts.isValid());
        ts.moveStart();
        assertTrue(ts.moveNext());
        Token<?> tok = ts.offsetToken();
        assertNotNull(tok);
        assertEquals("OTAG", ts.token().id().name());
        assertOpenAndClosingTag(ts);
    } 


    @Override
    protected void setUp() throws Exception {
        Logger.getLogger(SilverstripeLexer.class.getSimpleName()).setLevel(Level.ALL);
    }

    protected TokenSequence<?> seqForText(String text) {
        TokenHierarchy<?> tokenHierarchy = TokenHierarchy.create(text, SilverstripeTokenId.getLanguage());
        return tokenHierarchy.tokenSequence();
    }

    protected void assertOpenAndClosingTag(TokenSequence ts) {
        ts.moveStart();
        assertTrue(ts.moveNext());
        assertEquals(SilverstripeTokenId.OTAG, ts.token().id());
        ts.moveEnd();
        assertTrue(ts.movePrevious());
        assertEquals(SilverstripeTokenId.CTAG, ts.token().id());
    }
}
