package cz.bh.lisp.lib.macro

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * Defines the {@code if} macro.
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class If extends NativeMacro {

    @Override
    String getSymbol() {
        return "if"
    }

    @Override
    String getDoc() {
        return "[test true-branch false-branch] Evaluates test."
    }

    @Override
    Object run(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParameters(parameters, 3)

        def test = interpreter.eval(parameters[0], context)
        if (test != false && test != Context.NIL) {
            // true branch
            return interpreter.eval(parameters[1], context)
        } else {
            // false branch
            return interpreter.eval(parameters[2], context)
        }
    }
}
