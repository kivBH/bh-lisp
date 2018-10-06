package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code last} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Last extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "last"
    }

    @Override
    String getDoc() {
        return "Returns the last item in coll."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)
        def coll = parameters[0]

        if (coll instanceof String) {
            return "" + coll.charAt(coll.length() - 1)
        } else if (coll instanceof List) {
            return coll.get(coll.size() - 1)
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
