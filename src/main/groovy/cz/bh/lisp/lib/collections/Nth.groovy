package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code nth} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Nth extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "nth"
    }

    @Override
    String getDoc() {
        return "Returns the value at the index. Throws an OutOfBoundsException." +
                " Works for List and String." +
                " Usage: (nth coll index)"
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 2)
        def coll = parameters[0]
        def index = Preconditions.requireType(parameters[1], Number).intValue()

        if (coll instanceof String) {
            return "" + coll.charAt(index)
        } else if (coll instanceof List) {
            return coll.get(index)
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
