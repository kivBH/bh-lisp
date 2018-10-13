package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.HighOrderFunction

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
abstract class NativeHighOrderFunction extends HighOrderFunction implements Definition, Documented {

    @Override
    abstract String getSymbol()

    @Override
    String getDoc() {
        return null
    }

    @Override
    Object getValue() {
        return this
    }
}
