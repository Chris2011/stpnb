package de.tinysite.stpnb;

import de.tinysite.stpnb.parser.SilverstripeParserException;
import org.junit.Test;

public class InvalidInputParserTest extends BaseParserTest {

    @Test(expected=SilverstripeParserException.class)
    public void endPrefixWithSpace() {
        getParserTree("<% if Foo %>BAR<% end_ if %>");
    }
}
