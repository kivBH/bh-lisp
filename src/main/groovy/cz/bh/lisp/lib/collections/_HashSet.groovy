package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeSimpleFunction

/**
 * Defines the {@code hash-set} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _HashSet extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "hash-set"
    }

    @Override
    String getDoc() {
        return "[i*] Returns a new hash set with supplied keys."
    }

    @Override
    def run(List parameters) {
        return new HashSet<>(parameters)
    }
}
