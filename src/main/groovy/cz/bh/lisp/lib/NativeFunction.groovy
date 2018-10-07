package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.SimpleFunction

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
abstract class NativeFunction extends SimpleFunction implements Definition, Documented {

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
