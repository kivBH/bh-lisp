package cz.bh.lisp.parser.lexer


import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

import java.util.regex.Pattern

class NodeHandler {
    Pattern integerPattern
    Pattern doublePattern

    NodeHandler() {
        integerPattern = Pattern.compile("-?\\d+")
        doublePattern = Pattern.compile("-?\\d+[.]\\d+")
    }

    Node handle(String val, int linePosition) {
        if (doublePattern.matcher(val).matches()) {
            BigDecimal d = new BigDecimal(val)
            return new DoubleNode(d, linePosition)
        }

        if (integerPattern.matcher(val).matches()) {
            BigInteger i = new BigInteger(val)
            return new IntegerNode(i, linePosition)
        }

        // nerozpoznane jako symbol
        return new SymbolNode(val, linePosition)
    }
}
