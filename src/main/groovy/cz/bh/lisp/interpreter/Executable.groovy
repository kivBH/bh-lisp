package cz.bh.lisp.interpreter

import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
interface Executable {

    Object execute(Interpreter interpreter, Context context, List<Node> parameters)

}
