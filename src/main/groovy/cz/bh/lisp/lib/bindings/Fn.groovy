package cz.bh.lisp.lib.bindings

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * Defines the {@code fn} macro.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class Fn extends NativeMacro {

    @Override
    String getSymbol() {
        return "fn"
    }

    @Override
    String getDoc() {
        return "[[params*] body] " +
                "Creates anonymous function and returns it."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParameters(parameters, 2)
        List<Node> params = Preconditions.requireListLiteralNodeForBindingParameterMultipleOf(parameters.first(), 1)
        Node body = parameters.get(1)

        return new UserDefinedFunction(null, params, body)
    }
}