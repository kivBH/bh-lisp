package cz.bh.lisp.parser.sexp

class SymbolNode extends Node<String> {
    SymbolNode(String val, int line) {
        super(val, line)
    }
}
