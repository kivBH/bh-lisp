package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeSimpleFunction

/**
 * Defines the {@code list} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _List extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "list"
    }

    @Override
    String getDoc() {
        return "[i*] Creates a new list containing the items."
    }

    @Override
    def run(List parameters) {
        return new ArrayList<>(parameters)
    }
}
