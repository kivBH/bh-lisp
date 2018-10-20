package cz.bh.lisp.parser.sexp

class DoubleNode extends Node<BigDecimal> {
    DoubleNode(BigDecimal val, int line) {
        super(val, line)
    }
}
