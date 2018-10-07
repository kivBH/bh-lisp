package cz.bh.lisp.parser.sexp

class StringNode extends Node<String> {
    StringNode(String val, int line) {
        super(val, line)
    }

    @Override
    public String toString() {
        return "\"" + val + "\""
    }
}
