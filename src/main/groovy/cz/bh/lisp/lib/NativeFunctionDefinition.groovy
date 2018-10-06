package cz.bh.lisp.lib

import cz.bh.lisp.interpret.NativeFunction

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
abstract class NativeFunctionDefinition extends NativeFunction implements Definition {

    @Override
    Object getValue() {
        return this
    }
}
