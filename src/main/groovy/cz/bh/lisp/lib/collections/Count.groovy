package cz.bh.lisp.lib.collections

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code count} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Count extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "count"
    }

    @Override
    String getDoc() {
        return "[coll] Returns the number of items in the collection. (count nil) returns 0." +
                " Also works on strings, and Java Collections and Maps."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        def coll = parameters[0]

        if (coll == Context.NIL) {
            return BigInteger.ZERO
        } else if (coll instanceof String) {
            return BigInteger.valueOf(coll.length())
        } else if (coll instanceof Collection) {
            return BigInteger.valueOf(coll.size())
        } else if (coll instanceof Map) {
            return BigInteger.valueOf(coll.size())
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
