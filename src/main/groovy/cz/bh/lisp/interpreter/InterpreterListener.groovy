package cz.bh.lisp.interpreter

import cz.bh.lisp.LispException

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
interface InterpreterListener {

    void onResult(result)
    void onUnhandledException(LispException e)
    void onUnhandledError(Exception e)

}
