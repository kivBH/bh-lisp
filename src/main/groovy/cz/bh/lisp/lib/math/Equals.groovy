package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code =} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Equals extends NativeFunction {

    @Override
    String getSymbol() {
        return "="
    }

    @Override
    String getDoc() {
        return "Equality. Returns true if x equals y, false if not. Same as" +
                " Java x.equals(y) except it also works for nil."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        for (int i = 1; i < parameters.size(); i++) {
            if (!(parameters[i - 1] == parameters[i]))
                return false
        }
        return true
    }
}
