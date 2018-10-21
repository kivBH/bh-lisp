package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.exceptions.LexerException
import cz.bh.lisp.parser.sexp.ClassNode
import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

import java.util.regex.Pattern

class NodeHandler {
    Pattern integerPattern
    Pattern doublePattern
    Pattern doubleExpPattern

    NodeHandler() {
        integerPattern = Pattern.compile("[+-]?\\d+")
        doublePattern = Pattern.compile("[+-]?\\d+[,.]\\d+")
        doubleExpPattern = Pattern.compile("[+-]?\\d+([,.]\\d+)?[eE][+-]?\\d+")
    }

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

    ClassNode handleClass(String val, int linePosition) {
        Class c
        try {
            c = Class.forName(val)
        }
        catch (ClassNotFoundException e) {
            throw new LexerException("Class " + val + " not found", linePosition)
        }
        catch (Exception e) {
            throw new LexerException("Can not create Class object from " + val, linePosition)
        }
        return new ClassNode(c, linePosition)
    }
}
