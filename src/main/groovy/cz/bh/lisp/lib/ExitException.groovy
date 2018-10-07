package cz.bh.lisp.lib

import cz.bh.lisp.LispException

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class ExitException extends LispException {

    final int exitCode

    ExitException(int exitCode) {
        super("Program terminated with exit code " + exitCode)
        this.exitCode = exitCode
    }
}
