package de.stpnb.stpnb;

import java.util.Arrays;
import java.util.Collection;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static junit.framework.Assert.assertEquals;


@RunWith(Parameterized.class)
public class StpParserSnippetBatchTest extends StpParserTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"<% if $MetaTitle %>$MetaTitle<% end_if %>",
             "(template (tag (ifTag <%  if (expr (variable $MetaTitle))  %> (tagContent (variable $MetaTitle)) <%  end_ if  %>)))"
            }     
        });
    }

    private final String inputString;
    private final String expectedParseResult;

    public StpParserSnippetBatchTest(String inputString, String expectedParseResult) {
        this.inputString = inputString;
        this.expectedParseResult = expectedParseResult;
    }

    @Test
    public void test() {
        assertEquals(expectedParseResult, getParserTree(inputString));
    }
}
