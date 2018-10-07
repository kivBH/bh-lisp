package cz.bh.lisp.parser.sexp

import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType

import java.util.regex.Matcher
import java.util.regex.Pattern

class NodeHandler {
    Pattern integerPattern
    Pattern doublePattern

    NodeHandler() {
        integerPattern = Pattern.compile("-?\\d+")
        doublePattern = Pattern.compile("-?\\d+[.]\\d+")
    }

    Node handleToken(Token token) {
        if (token.type == TokenType.STRING) {
            return new StringNode(token.val, token.linePosition)
        }

        return handleUnknownToken(token)
    }

    private Node handleUnknownToken(Token token) {
        if (doublePattern.matcher(token.val).matches()) {
            Double d = Double.parseDouble(token.val)
            return new DoubleNode(d, token.linePosition)
        }

        if (integerPattern.matcher(token.val).matches()) {
            Integer i = Integer.parseInt(token.val)
            return new IntegerNode(i, token.linePosition)
        }


    // nerozpoznane jako symbol
        return new SymbolNode(token.val, token.linePosition)
    }
}
