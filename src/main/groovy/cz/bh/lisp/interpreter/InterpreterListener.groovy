package cz.bh.lisp.interpreter

import cz.bh.lisp.LispException
import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
interface InterpreterListener {

    void onExpressionParsed(Node node)
    void onResult(result)
    void onUnhandledException(LispException e)
    void onUnhandledError(Exception e)

}
