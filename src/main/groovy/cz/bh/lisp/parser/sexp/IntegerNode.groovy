package cz.bh.lisp.parser.sexp

class IntegerNode extends Node<BigInteger> {
    IntegerNode(BigInteger val, int line) {
        super(val, line)
    }
}
