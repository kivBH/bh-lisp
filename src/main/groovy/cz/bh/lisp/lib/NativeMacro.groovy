package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.Executable

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
abstract class NativeMacro implements Definition, Executable, Documented {

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
