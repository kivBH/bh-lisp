package cz.bh.lisp.interpret

import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
interface Executable {

    Object run(Interpreter interpreter, Context context, List<Node> parameters)

}
