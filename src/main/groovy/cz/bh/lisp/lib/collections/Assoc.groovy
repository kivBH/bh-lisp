package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.CloningUtils
import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code assoc} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Assoc extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "assoc"
    }

    @Override
    String getDoc() {
        return "[coll k v] When applied to a map, returns a new map that contains the mapping" +
                " of key to val. When applied to a list, returns a new list" +
                " that contains value v at index k."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 3)
        def coll = parameters[0]
        def key = parameters[1]
        def value = parameters[2]

        if (coll instanceof List) {
            def list = CloningUtils.clone(coll)
            list.set(Preconditions.requireType(key, Number).intValue(), value)
            return list
        } else if (coll instanceof Map) {
            def map = CloningUtils.clone(coll)
            map.put(key, value)
            return map
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
