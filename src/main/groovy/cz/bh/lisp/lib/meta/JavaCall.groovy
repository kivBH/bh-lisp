package cz.bh.lisp.lib.meta

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 * Defines the {@code .} macro.
 *
 * @version 2018-10-12
 * @author Patrik Harag
 */
class JavaCall extends NativeMacro {

    @Override
    String getSymbol() {
        return "."
    }

    @Override
    String getDoc() {
        return "[method-name instance par*] Evaluates Java method."
    }

    @Override
    def execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParametersAtLeast(parameters, 2)
        def methodName = parameters[0]
        if (!(methodName instanceof SymbolNode)) {
            throw new IllegalArgumentException("Symbol expected")
        }

        def evaluatedInstance = interpreter.eval(parameters[1], context)
        def evaluatedParameters = parameters.drop(2).collect {
            interpreter.eval(it, context)
        }

        def result = evaluatedInstance."$methodName.val"(*evaluatedParameters)
        return Nil.map(result)
    }
}
