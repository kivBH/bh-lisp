package cz.bh.lisp.parser.sexp

class ListNode extends Node<ArrayList<Node>>{
    ListNode(int line) {
        super(new ArrayList<Node>(), line)
    }

    ArrayList<Node> getList() {
        return val
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
