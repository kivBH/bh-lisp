package cz.bh.lisp.lib.bindings

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * Defines the {@code defn} macro.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class Defn extends NativeMacro {

    @Override
    String getSymbol() {
        return "defn"
    }

    @Override
    String getDoc() {
        return "[name [params*] body] " +
                "Creates function with given name. Returns function."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParameters(parameters, 3)
        String funcName = Preconditions.requireSymbolNodeForBinding(parameters.first())
        List<Node> params = Preconditions.requireListLiteralNodeForBindingParameterMultipleOf(parameters.get(1), 1)
        Node body = parameters.get(2)

        def func = new UserDefinedFunction(funcName, params, body)
        context.getRootContext().setValue(funcName, func)
        return func
    }
}