package cz.bh.lisp.parser.sexp

abstract class Node {
    Object val
    int line

    Node(Object val, int line) {
        this.val = val
        this.line = line
    }

    Object getVal() {
        return val
    }

    int getLine() {
        return line
    }

    @Override
    public String toString() {
        return val.toString()
    }
}
