package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.sexp.ClassNode
import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

import java.util.regex.Pattern

/**
 * Class for handling patterns
 *
 * @author Josef Baloun
 */
class NodeHandler {
    Pattern integerPattern
    Pattern doublePattern
    Pattern doubleExpPattern

    NodeHandler() {
        integerPattern = Pattern.compile("[+-]?\\d+")
        doublePattern = Pattern.compile("[+-]?\\d+[,.]\\d+")
        doubleExpPattern = Pattern.compile("[+-]?\\d+([,.]\\d+)?[eE][+-]?\\d+")
    }

    /**
     * Handles unrecognized symbol
     */
    Node handle(String val, int linePosition) {
        if (doublePattern.matcher(val).matches() || doubleExpPattern.matcher(val).matches()) {
            BigDecimal d = new BigDecimal(val.replace(',', '.'))
            return new DoubleNode(d, linePosition)
        }

        if (integerPattern.matcher(val).matches()) {
            BigInteger i = new BigInteger(val)
            return new IntegerNode(i, linePosition)
        }

        // nerozpoznane jako symbol
        return new SymbolNode(val, linePosition)
    }

    /**
     * Handles class input
     */
    ClassNode handleClass(String val, int linePosition) {
        Class c
        try {
            c = Class.forName(val)
        }
        catch (ClassNotFoundException e) {
            throw new ParserException("Class " + val + " not found", linePosition)
        }
        catch (Exception e) {
            throw new ParserException("Can not create Class object from " + val, linePosition)
        }
        return new ClassNode(c, linePosition)
    }
}
