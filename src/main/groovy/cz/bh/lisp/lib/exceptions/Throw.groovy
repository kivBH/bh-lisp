package cz.bh.lisp.lib.exceptions

import cz.bh.lisp.LispException
import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.StringNode

/**
 * Defines the {@code throw} macro.
 * @version 2018-11-23
 * @author Josef Baloun
 */
class Throw extends NativeMacro {

    @Override
    String getSymbol() {
        return "throw"
    }

    @Override
    String getDoc() {
        return "[message] or [instance] " +
                "Throws LispException with given message or instance of Throwable."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParameters(parameters, 1)
        Node n = parameters.first()
        if (n instanceof StringNode) {
            throw new LispException(n.val)
        }

        def e = interpreter.eval(n, context)
        if (e instanceof Throwable) {
            throw e
        }

        throw new IllegalArgumentException("Parameter is not Throwable: " + e)
    }
}