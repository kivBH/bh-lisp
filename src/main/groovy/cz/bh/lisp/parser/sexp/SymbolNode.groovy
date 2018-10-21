package cz.bh.lisp.parser.sexp

/**
 * Class representing symbol input
 *
 * @author Josef Baloun
 */
class SymbolNode extends Node<String> {
    SymbolNode(String val, int line) {
        super(val, line)
    }
}
