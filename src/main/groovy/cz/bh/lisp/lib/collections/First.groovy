package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code first} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class First extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "first"
    }

    @Override
    String getDoc() {
        return "Returns the first item in coll."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)
        def coll = parameters[0]

        if (coll instanceof String) {
            return "" + coll.charAt(0)
        } else if (coll instanceof List) {
            return coll.get(0)
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
