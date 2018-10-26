package cz.bh.lisp.parser.sexp

/**
 * Class representing list
 * Node that contains child Nodes
 *
 * @author Josef Baloun
 */
class ListNode extends LNode{
    ListNode(int line) {
        super(line)
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append("(")
        for (n in getList()) {
            sb.append(n.toString())
            sb.append(" ")
        }
        sb.deleteCharAt(sb.length() - 1)
        sb.append(")")
        return sb.toString()
    }
}
