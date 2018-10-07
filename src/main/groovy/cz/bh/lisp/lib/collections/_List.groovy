package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunction

/**
 * Defines the {@code list} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _List extends NativeFunction {

    @Override
    String getSymbol() {
        return "list"
    }

    @Override
    String getDoc() {
        return "Creates a new list containing the items."
    }

    @Override
    def run(List parameters) {
        return new ArrayList<>(parameters)
    }
}
