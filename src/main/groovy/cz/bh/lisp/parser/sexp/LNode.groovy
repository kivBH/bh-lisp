package cz.bh.lisp.parser.sexp

/**
 * Node that contains child Nodes
 *
 * @author Josef Baloun
 */
abstract class LNode extends Node<ArrayList<Node>> {
    LNode(int line) {
        super(new ArrayList<Node>(), line)
    }

    ArrayList<Node> getList() {
        return val
    }

}
