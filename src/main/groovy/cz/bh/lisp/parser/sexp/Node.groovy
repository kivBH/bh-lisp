package cz.bh.lisp.parser.sexp

abstract class Node {
    Object val
    final int line

    Node(Object val, int line) {
        this.val = val
        this.line = line
    }

    public <T> T getVal(Class<T> requiredType) {
        return (T) val
    }

    @Override
    public String toString() {
        return val.toString()
    }
}
