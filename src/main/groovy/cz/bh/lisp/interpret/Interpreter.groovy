package cz.bh.lisp.interpret

import cz.bh.lisp.parser.SExpressionBuilder
import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.StringNode
import cz.bh.lisp.parser.sexp.SymbolNode

import java.util.function.Consumer

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class Interpreter {

    private final Context globalContext
    private final Consumer consumer

    Interpreter(Context globalContext, Consumer consumer) {
        this.globalContext = globalContext
        this.consumer = consumer
    }

    void eval(String sourceCode) {
        eval(new StringReader(sourceCode))
    }

    void eval(Reader sourceCode) {
        def builder = new SExpressionBuilder(sourceCode)
        builder.each {
            def result = eval(it, globalContext)
            consumer.accept(result)
        }
    }

    def eval(Node node, Context context) {
        switch (node) {
            case ListNode:
                return handleList(context, node as ListNode)
            case SymbolNode:
                return handleSymbol(context, node as SymbolNode)
            case IntegerNode:
            case DoubleNode:
            case StringNode:
                return node.val
            default:
                throw new UnsupportedOperationException("Not implemented yet")
        }
    }

    private def handleList(Context context, ListNode node) {
        if (node.val.isEmpty()) {
            return Context.NIL
        }
        def first = eval(node.val.first(), context)
        if (first instanceof Executable) {
            List<Node> parameters
            if (node.val.size() == 1) {
                parameters = Collections.emptyList()
            } else {
                parameters = node.val.subList(1, node.val.size())
            }
            return first.run(this, context, parameters)
        } else {
            throw new RuntimeException("Function expected, but was: " + first)
        }
    }

    private def handleSymbol(Context context, SymbolNode node) {
        def value = context.getValue(node.val)
        if (value != null) {
            return value
        } else {
            throw new RuntimeException("Symbol not defined: '$node.val'")
        }
    }

}
