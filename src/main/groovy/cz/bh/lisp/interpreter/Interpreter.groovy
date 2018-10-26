package cz.bh.lisp.interpreter

import cz.bh.lisp.LispException
import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.parser.SExpressionBuilder
import cz.bh.lisp.parser.sexp.ClassNode
import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.ListLiteralNode
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.StringNode
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class Interpreter {

    private final Context globalContext
    private final InterpreterListener listener
    PrintStream stdOut

    Interpreter(Context globalContext, InterpreterListener listener) {
        this.globalContext = globalContext
        this.listener = listener
        this.stdOut = System.out
    }

    void eval(String sourceCode) {
        eval(new StringReader(sourceCode))
    }

    void eval(Reader sourceCode) {
        def builder = new SExpressionBuilder(sourceCode)

        while (true) {
            try {
                def next = builder.build()
                if (next == null) {
                    return
                }
                listener.onExpressionParsed(next)
                def result = eval(next, globalContext)
                listener.onResult(result)
            } catch (LispException e) {
                listener.onUnhandledException(e)
            } catch (Exception e) {
                listener.onUnhandledError(e)
            }
        }
    }

    def eval(Node node, Context context) {
        switch (node) {
            case ListLiteralNode:
                return handleList(context, ((ListLiteralNode)node).convertToListNode())
            case ListNode:
                return handleList(context, node as ListNode)
            case SymbolNode:
                return handleSymbol(context, node as SymbolNode)
            case IntegerNode:
            case DoubleNode:
            case StringNode:
            case ClassNode:
                return node.val
            default:
                throw new UnsupportedOperationException("Not implemented yet")
        }
    }

    private def handleList(Context context, ListNode node) {
        if (node.val.isEmpty()) {
            return Nil.VALUE
        }
        def firstNode = node.val.first()
        def evaluatedFirstNode = eval(firstNode, context)
        if (evaluatedFirstNode instanceof Executable) {
            List<Node> parameters
            if (node.val.size() == 1) {
                parameters = Collections.emptyList()
            } else {
                parameters = node.val.drop(1)
            }
            try {
                return evaluatedFirstNode.execute(this, context, parameters)
            } catch (ExitException e) {
                throw e
            } catch (Exception e) {
                String msg = "Exception while evaluating function"
                if (firstNode instanceof SymbolNode) {
                    msg += " '$firstNode.val'"
                }
                throw new LispException(msg, node.line, e)
            }
        } else {
            throw new LispException("Function expected, but was: $evaluatedFirstNode", node.line)
        }
    }

    private def handleSymbol(Context context, SymbolNode node) {
        def value = context.getValue(node.val)
        if (value != null) {
            return value
        } else {
            throw new LispException("Symbol not defined: '$node.val'", node.line)
        }
    }

}
