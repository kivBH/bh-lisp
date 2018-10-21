package cz.bh.lisp.lib.conditionals

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeMacro
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * Defines the {@code cond} macro.
 * @version 2018-10-21
 * @author Josef Baloun
 */
class Cond extends NativeMacro {

    @Override
    String getSymbol() {
        return "cond"
    }

    @Override
    String getDoc() {
        return "[test true-branch]+ Evaluates tests. Returns the value of first true-branch where test is successful or nil."
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParametersAtLeast(parameters, 2)
        Preconditions.requireParametersMultipleOf(parameters, 2)

        Iterator<Node> it = parameters.iterator()
        while (it.hasNext()) {
            def test = interpreter.eval(it.next(), context)
            if (test != false && !(test instanceof Nil)) {
                // true branch
                return interpreter.eval(it.next(), context)
            } else {
                // false branch, skip body
                it.next()
            }
        }

        return Nil.wrap(null)
    }
}