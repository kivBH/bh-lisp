package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code >=} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class GreaterThanOrEquals extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return ">="
    }

    @Override
    String getDoc() {
        return "[n+] Returns non-nil if nums are in monotonically non-increasing order, otherwise false."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        Number last = Preconditions.requireType(parameters.first(), Number)
        for (int i = 1; i < parameters.size(); i++) {
            if (!(last >= (last = Preconditions.requireType(parameters[i], Number))))
                return false
        }
        return true
    }
}
