package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.CloningUtils
import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code conj} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Conj extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "conj"
    }

    @Override
    String getDoc() {
        return "[xs coll] Returns a new collection with the xs 'added'." +
                " (conj nil item) returns (item). The 'addition' may" +
                " happen at different 'places' depending on the concrete type."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 2)
        def element = parameters[0]
        def coll = Preconditions.requireType(parameters[1], Collection)

        def clone = CloningUtils.clone(coll)
        clone.add(element)
        return clone
    }
}
