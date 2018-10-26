package cz.bh.lisp.parser.sexp

/**
 * Class representing list literal []
 * Node that contains child Nodes
 *
 * @author Josef Baloun
 */
class ListLiteralNode extends LNode{
    ListLiteralNode(int line) {
        super(line)
    }

    /**
     * converts [] to (list)
     *
     * @return ListNode that contains SymbolNode(list) at first place
     */
    public ListNode convertToListNode() {
        ListNode ln = new ListNode(this.line)
        ln.list.add(new SymbolNode("list", this.line))
        ln.list.addAll(this.list)
        return ln
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append("[")
        for (n in getList()) {
            sb.append(n.toString())
            sb.append(" ")
        }
        sb.deleteCharAt(sb.length() - 1)
        sb.append("]")
        return sb.toString()
    }
}