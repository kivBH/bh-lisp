package cz.bh.lisp.lib.collections

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Function
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code reduce} function.
 *
 * @version 2018-10-26
 * @author Patrik Harag
 */
class Reduce extends NativeFunction {

    @Override
    String getSymbol() {
        return "reduce"
    }

    @Override
    String getDoc() {
        return "[f init? coll] Applies a function to an accumulator and" +
                " each element in a sequence, building a final result." +
                " The f should be a function of 2 arguments."
    }

    @Override
    def run(Interpreter interpreter, Context context, List parameters) {
        Preconditions.requireParameters(parameters, 2..3)

        def f = Preconditions.requireType(parameters[0], Function)
        if (parameters.size() == 2) {
            def coll = Preconditions.requireType(parameters[1], Collection)
            return coll.inject { acc, i -> f.run(interpreter, context, [acc, i]) }
        } else {
            def init = parameters[1]
            def coll = Preconditions.requireType(parameters[2], Collection)
            return coll.inject(init) { acc, i -> f.run(interpreter, context, [acc, i]) }
        }
    }
}
