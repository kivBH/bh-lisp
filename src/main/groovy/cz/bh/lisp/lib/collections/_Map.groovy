package cz.bh.lisp.lib.collections

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Function
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code map} function.
 *
 * @version 2018-10-26
 * @author Patrik Harag
 */
class _Map extends NativeFunction {

    @Override
    String getSymbol() {
        return "map"
    }

    @Override
    String getDoc() {
        return "[f coll+] Returns a lazy sequence consisting of the result of applying f to" +
                " the set of first items of each coll, followed by applying f to the" +
                " set of second items in each coll, until any one of the colls is" +
                " exhausted. Any remaining items in other colls are ignored."
    }

    @Override
    def run(Interpreter interpreter, Context context, List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 2)

        def f = Preconditions.requireType(parameters[0], Function)
        def iterators = parameters.drop(1).collect {
            Preconditions.requireType(it, Collection).iterator()
        }

        def result = []
        while (iterators.every { it.hasNext() }) {
            result << f.run(interpreter, context, iterators.collect { it.next() })
        }
        return result
    }
}
