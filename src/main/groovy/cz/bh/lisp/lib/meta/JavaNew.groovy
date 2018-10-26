package cz.bh.lisp.lib.meta

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Nil
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code new} function.
 *
 * @version 2018-10-14
 * @author Patrik Harag
 */
class JavaNew extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "new"
    }

    @Override
    String getDoc() {
        return "[class par*] Creates Java instance."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        def clazz = Preconditions.requireType(parameters[0], Class)
        def params = parameters.drop(1).collect { Nil.unwrap(it) }.toArray()
        return clazz.newInstance(params)
    }
}
