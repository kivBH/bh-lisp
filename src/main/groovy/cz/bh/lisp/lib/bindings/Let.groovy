package cz.bh.lisp.lib.bindings

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 * Defines the {@code let} macro.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class Let extends NativeMacro {

    @Override
    String getSymbol() {
        return "let"
    }

    @Override
    String getDoc() {
        return "[(binding*) exprs*] " + System.lineSeparator() +
                "binding => var_name var_value " + System.lineSeparator() +
                "Evaluates the exprs in a lexical context given by binding and returns the value of the last." +
                " If no expressions are supplied, returns nil."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)
        List<Node> bindings = Preconditions.requireListNodeForBindingParameterMultipleOf(parameters.first(), 2)

        Context localContext = new Context(context)
        Iterator<Node> it = bindings.iterator()
        while (it.hasNext()) {
            String symbolName = Preconditions.requireType(it.next(), SymbolNode).val
            def symbolValue = interpreter.eval(it.next(), localContext)
            localContext.setValue(symbolName, symbolValue)
        }

        List vals = parameters.drop(1).collect { interpreter.eval(it, localContext) }

        return vals.isEmpty() ? Nil.VALUE : vals.last()
    }
}
