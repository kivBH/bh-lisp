package cz.bh.lisp.parser.sexp

/**
 * Class representing int input
 *
 * @author Josef Baloun
 */
class IntegerNode extends Node<BigInteger> {
    IntegerNode(BigInteger val, int line) {
        super(val, line)
    }
}
