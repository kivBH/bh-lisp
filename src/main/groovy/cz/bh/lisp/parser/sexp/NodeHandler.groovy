package cz.bh.lisp.parser.sexp

import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType

import java.util.regex.Matcher
import java.util.regex.Pattern

class NodeHandler {
    Pattern integerPattern
    Pattern doublePattern
    Pattern stringPattern

    NodeHandler() {
     /*   integerPattern = Pattern.compile("\d+")
        doublePattern = Pattern.compile("\d+[.,]\d+")
        stringPattern = Pattern.compile("\"[\s\S]*\"")*/
    }

    Node handleToken(Token token) {
   /*     if (token.type == TokenType.UNKNOWN) {  // ocekavany typ jinak jako symbol

            if (doublePattern.matcher(token.val).matches()) {
                Double d = Double.parseDouble(token.val)
                return new DoubleNode(d, token.linePosition)
            }

            if (integerPattern.matcher(token.val).matches()) {
                Integer i = Integer.parseInt(token.val)
                return new IntegerNode(i, token.linePosition)
            }

            if (stringPattern.matcher(token.val).matches()) {
                String s = token.val.substring(1, token.val.length() - 1)
                return new StringNode(s, token.linePosition)
            }
        }
*/
        // nerozpoznane je symbol
        return new SymbolNode(token.val, token.linePosition)
    }
}
