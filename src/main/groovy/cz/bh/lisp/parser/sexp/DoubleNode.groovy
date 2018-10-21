package cz.bh.lisp.parser.sexp

/**
 * Class representing real input
 *
 * @author Josef Baloun
 */
class DoubleNode extends Node<BigDecimal> {
    DoubleNode(BigDecimal val, int line) {
        super(val, line)
    }
}
