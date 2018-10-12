package cz.bh.lisp.lib.meta

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code new} function.
 *
 * @version 2018-10-12
 * @author Patrik Harag
 */
class JavaNew extends NativeFunction {

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
        return clazz.newInstance(parameters.drop(1).toArray())
    }
}
