parser grammar StpParser;

options { tokenVocab=StpLexer; }

template	        :	(comment | tag | htmlContent | variable)* ;
tagContent	        :	(tag | htmlContent | variable | comment )+ ;              

translation         :   TRANSLATION | OLD_TRANSLATION | SPRINTF_TRANSLATION;
oTag                :   ELSE | ELSE_IF ;
cTag                :   UNCACHED ;
tag		            :	ifTag                                  # pairedTag
                    |   withTag                                # pairedTag
                    |   controlTag                             # pairedTag
                    |   loopTag                                # pairedTag
                    |   cacheblockTag                          # pairedTag
                    |   cachedTag                              # pairedTag
                    |   uncachedTag                            # pairedTag
                    |   includeTag                             # unpairedTag 
                    |   requireTag                             # unpairedTag 
                    |   baseTag                                # unpairedTag 
                    |   translation                            # unpairedTag 
		            |	openTag                                # unpairedTag

		            ;
comment             :   COMMENT ;

operator            :   OP_EQUAL | OP_AND | OP_NOT | OP_OR | OP_NOT_EQUAL ;
            
expr                :   variable
                    |   number
                    |   QUOTED_STRING
                    |   expr operator expr
                    |   operator expr ;

ifTag               :   OTAG IF params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX IF) CTAG ;
withTag             :   OTAG WITH params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX WITH) CTAG ;
controlTag          :   OTAG CONTROL params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX CONTROL) CTAG ;
loopTag             :   OTAG LOOP params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX LOOP) CTAG ;
cacheblockTag       :   OTAG CACHEBLOCK params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX CACHEBLOCK) CTAG ;
cachedTag           :   OTAG CACHED params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX CACHED) CTAG ;
uncachedTag         :   OTAG UNCACHED params? (expr | param)*? CTAG tagContent* OTAG (END_PREFIX UNCACHED) CTAG ;
includeTag          :   OTAG INCLUDE includeName (expr | param)*? CTAG ; 
requireTag          :   OTAG REQUIRE RESOURCE params? (expr | param)*? CTAG ; 
baseTag             :   OTAG BASE CTAG ;
openTag             :   OTAG oTag params? (expr | param)*? CTAG ;

variable            :   (PROPERTY params? (SUBPROPERTY params?)*?)
                    ;
htmlContent         :   (TEXT | UNKNOWN | operator)+ ;   

param               :   QUOTED_PARAM | param OP_EQUAL QUOTED_PARAM | number | variable ;
includeName         :   param ;

params              :   OPEN_BRACKET param (COMMA param)* CLOSING_BRACKET ; 
paramsWithoutParens :   param (COMMA param)* ;

number              :   NUMBER ;

