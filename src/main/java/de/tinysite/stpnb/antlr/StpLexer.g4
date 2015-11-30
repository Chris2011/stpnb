lexer grammar StpLexer;

WS              :   (' '|'\t'|'\r'? '\n')+ -> channel(HIDDEN) ;
TEXT            :   ('<' ~'%' ~['<'|'"'|'$' |'>']*?'>'? ) | (~['<'|'$']+) ;
COMMENT         :   '<%--' .*? '--%>' ;

OLD_TRANSLATION :   '<% _t' .*? '%>' ;
SPRINTF_TRANSLATION : '<% sprintf' .*? '%>' ;
TRANSLATION     :   '<%t' .*? '%>' ;
OTAG	        :   '<%' WS?   -> pushMode(INSIDE_TAG) ;

UNKNOWN         :   .+? ;

NUMBER          : '0' | [1-9] DIGIT* ;

SUBPROPERTY     : '.' IDENTIFIER ;
PROPERTY        : '$'? IDENTIFIER ;
IDENTIFIER      :  (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE | '.')+ ;

OP_NOT          : 'not' ;
OP_AND          : '&&' ;
OP_OR           : '|' '|'? ;
OP_EQUAL        :  '=' '='? ;
OP_NOT_EQUAL    : '!=' ;
OP_GREATER_THAN : '>' ;
OP_LESS_THAN    : '<' ;

fragment
LETTER          : [a-zA-Z] ;



mode INSIDE_TAG;

NUMBER_INSIDE_TAG           : ('0' | [1-9] DIGIT*) -> type(NUMBER) ;
OP_NOT_INSIDE_TAG           : 'not' -> type(OP_NOT) ;
OP_AND_INSIDE_TAG           : '&&' -> type(OP_AND) ;
OP_OR_INSIDE_TAG            : '|' '|'? -> type(OP_OR) ;
OP_EQUAL_INSIDE_TAG         : '=' '='? -> type(OP_EQUAL) ;
OP_NOT_EQUAL_INSIDE_TAG     : '!=' -> type(OP_NOT_EQUAL) ;
OP_GREATER_THAN_INSIDE_TAG  : '>' ->type(OP_GREATER_THAN) ;
OP_LESS_THAN_INSIDE_TAG     : '<' ->type(OP_LESS_THAN) ;
I_COMMENT                   : '<%--' .*? ' --%>' -> type(COMMENT) ;
CTAG	                    : ' %>'   -> popMode ;

WS_INSIDE_TAG               :   (' '|'\t'|'\r'? '\n')+ -> channel(HIDDEN), type(WS) ;
END_PREFIX                  :   'end_' ;

INCLUDE                     :   'include' ;
WITH                        :   'with' ;
REQUIRE                     :   'require' ;
IF                          :   'if' ;
ELSE                        :   'else' ;
ELSE_IF                     :   'else_if' ;
LOOP                        :   'loop' ;
CACHEBLOCK                  :   'cacheblock' ;
CACHED                      :   'cached' ;
UNCACHED                    :   'uncached' ;
CONTROL                     :   'control' ;
BASE                        :   'base_tag' ;

RESOURCE                    :   'css' | 'themedCSS' | 'javascript' ;


SUBPROPERTY_INSIDE_TAG      : '.' ( LETTER | UNDERSCORE ) (LETTER | DIGIT|UNDERSCORE)* -> type(SUBPROPERTY) ;
PROPERTY_INSIDE_TAG         : '$'? ( LETTER | UNDERSCORE )(LETTER | DIGIT | UNDERSCORE )*  {!getText().startsWith("end_")}? -> type(PROPERTY);

QUOTED_STRING               :  ('\'' (ESC | ~'\'')* '\'') | ('"' ~'"'* '"') ;




OPEN_BRACKET                 : '(' -> pushMode(INSIDE_PARAMS) ;

UNKNOWN_INSIDE_TAG          : .+? -> type(UNKNOWN) ;




mode INSIDE_PARAMS ;
OPEN_BRACKET_INSIDE_PARAMS   : '(' ->type(OPEN_BRACKET), pushMode(INSIDE_PARAMS) ;
CLOSING_BRACKET              : ')' -> popMode ;
COMMA                       : ',' ; 
DOT                         : '.' ;
QUOTED_PARAM                :  ('\'' ( ESC | ~'\'')* '\'') | ('"' ( ESC | ~'"' )* '"') ;
NUMBER_INSIDE_PARAMS        : ('0' | [1-9] DIGIT*) -> type(NUMBER) ;
WS_INSIDE_PARAMS            :   (' '|'\t'|'\r'? '\n')+ -> channel(HIDDEN), type(WS) ;
SUBPROPERTY_INSIDE_PARAMS   : '.' LETTER (LETTER | DIGIT | UNDERSCORE | SLASH )* -> type(SUBPROPERTY) ;
PROPERTY_INSIDE_PARAMS      : '$'? (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE | SLASH )*  {!getText().startsWith("end_")}? -> type(PROPERTY);

fragment
UNDERSCORE                  :   '_' ;

fragment
DIGIT                       :   [0-9] ;

fragment SLASH              : '/' ;
fragment
ESC                         : '\\\'' | '\\"' ;

ERROR_CHARACTER : . ;
