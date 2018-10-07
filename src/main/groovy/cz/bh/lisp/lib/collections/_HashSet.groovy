package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunction

/**
 * Defines the {@code hash-set} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _HashSet extends NativeFunction {

    @Override
    String getSymbol() {
        return "hash-set"
    }

    @Override
    String getDoc() {
        return "Returns a new hash set with supplied keys."
    }

    @Override
    def run(List parameters) {
        return new HashSet<>(parameters)
    }
}
