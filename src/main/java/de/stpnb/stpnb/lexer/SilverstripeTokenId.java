package de.stpnb.stpnb.lexer;

import de.stpnb.stpnb.antlr.StpParser;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public enum SilverstripeTokenId implements TokenId {

    WS("WS", SilverstripeTokenIdCategory.WHITESPACE, StpParser.WS),
    TEXT("TEXT", SilverstripeTokenIdCategory.TEXT, StpParser.TEXT),
    COMMENT("COMMENT", SilverstripeTokenIdCategory.COMMENT, StpParser.COMMENT),
    OTAG("OTAG", SilverstripeTokenIdCategory.KEYWORD, StpParser.OTAG),
    UNKNOWN("UNKNOWN", SilverstripeTokenIdCategory.OTHER, StpParser.UNKNOWN),
    NUMBER("NUMBER", SilverstripeTokenIdCategory.NUMBER, StpParser.NUMBER),
    SUBPROPERTY("SUBPROPERTY", SilverstripeTokenIdCategory.FIELD, StpParser.SUBPROPERTY),
    PROPERTY("PROPERTY", SilverstripeTokenIdCategory.FIELD, StpParser.PROPERTY),
    IDENTIFIER("IDENTIFIER", SilverstripeTokenIdCategory.IDENTIFIER, StpParser.IDENTIFIER),
    OP_NOT("OP_NOT", SilverstripeTokenIdCategory.OPERATOR, StpParser.OP_NOT),
    OP_AND("OP_AND", SilverstripeTokenIdCategory.OPERATOR, StpParser.OP_AND),
    OP_OR("OP_OR", SilverstripeTokenIdCategory.OPERATOR, StpParser.OP_OR),
    OP_EQUAL("OP_EQUAL", SilverstripeTokenIdCategory.OPERATOR, StpParser.OP_EQUAL),
    OP_NOT_EQUAL("OP_NOT_EQUAL", SilverstripeTokenIdCategory.OPERATOR, StpParser.OP_NOT_EQUAL),
    CTAG("CTAG", SilverstripeTokenIdCategory.KEYWORD, StpParser.CTAG),
    END_PREFIX("END_PREFIX", SilverstripeTokenIdCategory.KEYWORD, StpParser.END_PREFIX),
    INCLUDE("INCLUDE", SilverstripeTokenIdCategory.KEYWORD, StpParser.INCLUDE),
    WITH("WITH", SilverstripeTokenIdCategory.KEYWORD, StpParser.WITH),
    REQUIRE("REQUIRE", SilverstripeTokenIdCategory.KEYWORD, StpParser.REQUIRE),
    IF("IF", SilverstripeTokenIdCategory.KEYWORD, StpParser.IF),
    ELSE("ELSE", SilverstripeTokenIdCategory.KEYWORD, StpParser.ELSE),
    ELSE_IF("ELSE_IF", SilverstripeTokenIdCategory.KEYWORD, StpParser.ELSE_IF),
    LOOP("LOOP", SilverstripeTokenIdCategory.KEYWORD, StpParser.LOOP),
    CACHEBLOCK("CACHEBLOCK", SilverstripeTokenIdCategory.KEYWORD, StpParser.CACHEBLOCK),
    UNCACHED("UNCACHED", SilverstripeTokenIdCategory.KEYWORD, StpParser.UNCACHED),
    CONTROL("CONTROL", SilverstripeTokenIdCategory.KEYWORD, StpParser.CONTROL),
    BASE("BASE", SilverstripeTokenIdCategory.KEYWORD, StpParser.BASE),
    RESOURCE("RESOURCE", SilverstripeTokenIdCategory.KEYWORD, StpParser.RESOURCE),
    QUOTED_STRING("QUOTED_STRING", SilverstripeTokenIdCategory.STRING, StpParser.QUOTED_STRING),
    OPEN_BRACKET("OPEN_BRACKET", SilverstripeTokenIdCategory.BRACKET, StpParser.OPEN_BRACKET),
    CLOSING_BRACKET("CLOSING_BRACKET", SilverstripeTokenIdCategory.BRACKET, StpParser.CLOSING_BRACKET),
    COMMA("COMMA", SilverstripeTokenIdCategory.SEPARATOR, StpParser.COMMA),
    DOT("DOT", SilverstripeTokenIdCategory.SEPARATOR, StpParser.DOT),
    QUOTED_PARAM("QUOTED_PARAM", SilverstripeTokenIdCategory.STRING, StpParser.QUOTED_PARAM),
    ERROR_CHARACTER("ERROR_CHARACTER", SilverstripeTokenIdCategory.ERROR, StpParser.ERROR_CHARACTER)
    ;

    public static Language getLanguage() {
        return new SilverstripeLanguageHierarchy().language();
    }
   
	private final String name;

	private final SilverstripeTokenIdCategory primaryCategory;

	private final int id;

    private static Map<Integer, SilverstripeTokenId> idToToken = new HashMap<>();

    static {
        for(SilverstripeTokenId token: SilverstripeTokenId.values()) {
            idToToken.put(token.id, token);
        }
    }

	SilverstripeTokenId(String name, SilverstripeTokenIdCategory primaryCategory, int id) {
		this.name = name;
		this.primaryCategory = primaryCategory;
		this.id = id;
	}

    @Override
    public String primaryCategory() {
        return primaryCategory.name().toLowerCase();
    }

    public static SilverstripeTokenId valueOf(int id) {
        return idToToken.get(id);
    }

}
