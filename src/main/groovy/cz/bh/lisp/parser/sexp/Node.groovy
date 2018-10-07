package cz.bh.lisp.parser.sexp

abstract class Node<T> {
    final T val
    final int line

    Node(T val, int line) {
        this.val = val
        this.line = line
    }

    @Override
    public String toString() {
        return val.toString()
    }
}
