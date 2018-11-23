package cz.bh.lisp.lib.exceptions

import cz.bh.lisp.LispException
import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.ClassNode
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 * Defines the {@code try} macro.
 * @version 2018-11-23
 * @author Josef Baloun
 */
class Try extends NativeMacro {

    class CatchClause {
        Class clazz
        String excName
        Node body

        CatchClause(Class clazz, String excName, Node body) {
            this.clazz = clazz
            this.excName = excName
            this.body = body
        }

        boolean catches(exc) {
            return clazz.isInstance(exc)
        }
    }

    @Override
    String getSymbol() {
        return "try"
    }

    @Override
    String getDoc() {
        return "[body catch-clause* finally-clause?]\n" +
                "catch-clause => ('catch' class name body)\n" +
                "finally-clause => ('finally' body)\n" +
                "If an exception occurs in body and catch-clauses are provided, the first for which the thrown exception is an instance of the class is considered a matching catch-clause. " +
                "Exception is stored as name." +
                "If there is no matching catch-clause, the exception propagates out of the function. " +
                "If finally-clause is provided, it will be executed finally."
    }



    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        Node body = parameters.first()
        parameters.remove(0)
        Node finally_body = null
        List<CatchClause> catch_clauses = new LinkedList<>()

        if (!parameters.isEmpty()) {    // finally-clause
            ListNode last = Preconditions.requireType(parameters.last(), ListNode)
            if (!last.val.isEmpty() && last.val.first().val == "finally") {
                try {
                    Preconditions.requireParameters(last.val, 2)
                } catch (Exception e) {
                    throw new LispException("Exception in finally-clause", last.line, e)
                }
                finally_body = last.val.last()
                parameters.removeLast()
            }
        }

        if (!parameters.isEmpty()) {    // catch-clauses
            for (p in parameters) {
                ListNode ln = Preconditions.requireType(p, ListNode)
                catch_clauses.add(checkAndGetCatchClause(ln))
            }
        }

        try {
            return interpreter.eval(body, context)
        }
        catch (Exception e) {
            def re = getRootException(e)

            for (cc in catch_clauses) {
                if (cc.catches(re)) {
                    Context localContext = new Context(context)
                    localContext.setValue(cc.excName, re)
                    return interpreter.eval(cc.body, localContext)
                }
            }

            throw e
        }
        finally {
            if (finally_body != null) {
                interpreter.eval(finally_body, context)
            }
        }
    }

    CatchClause checkAndGetCatchClause(ListNode ln) {
        try {
            Preconditions.requireParameters(ln.val, 4)
            if (ln.val.first().val != "catch") {
                throw new IllegalArgumentException("catch expected, but was: " + ln.val.first().val)
            }

            Class clazz = Preconditions.requireType(ln.val.get(1), ClassNode).val
            String excName = Preconditions.requireType(ln.val.get(2), SymbolNode).val
            Node body = ln.val.get(3)
            return new CatchClause(clazz, excName, body)

        } catch (Exception e) {
            throw new LispException("Exception in catch-clause", ln.line, e)
        }
    }

    static Throwable getRootException(Throwable exception){
        Throwable rootException=exception
        while(rootException.getCause()!=null){
            /*if (rootException == rootException.getCause()) {  // implemented in getCause
                return rootException
            }*/
            rootException = rootException.getCause()
        }
        return rootException
    }
}