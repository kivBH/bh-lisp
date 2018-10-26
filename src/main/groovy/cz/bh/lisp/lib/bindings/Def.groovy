package cz.bh.lisp.lib.bindings

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * Defines the {@code def} macro.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class Def extends NativeMacro {

    @Override
    String getSymbol() {
        return "def"
    }

    @Override
    String getDoc() {
        return "[binding+] " +
                "(binding => name value) " +
                "Creates a global variable given by binding and returns the value of the last."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParametersAtLeast(parameters, 2)
        Preconditions.requireParametersMultipleOf(parameters, 2)

        Context globalContext = context.getRootContext()
        Iterator<Node> it = parameters.iterator()
        def symbolValue = Nil.VALUE
        while (it.hasNext()) {
            String symbolName = Preconditions.requireSymbolNodeForBinding(it.next())
            symbolValue = interpreter.eval(it.next(), context)
            globalContext.setValue(symbolName, symbolValue)
        }

        return symbolValue
    }
}