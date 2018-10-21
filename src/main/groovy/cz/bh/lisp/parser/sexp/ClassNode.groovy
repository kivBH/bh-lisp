package cz.bh.lisp.parser.sexp

/**
 * Class representing class input
 *
 * @author Josef Baloun
 */
class ClassNode extends Node<Class>{
    ClassNode(Class val, int line) {
        super(val, line)
    }

    @Override
    public String toString() {
        return "@" + val
    }
}
