package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.HighOrderFunction
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.parser.sexp.Node

/**
 * User defined function.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class UserDefinedFunction extends HighOrderFunction {
    final List<String> paramNames
    final Node body

    UserDefinedFunction(List<Node> params, Node body) {
        this.body = body
        paramNames = params.collect { Preconditions.requireSymbolNodeForBinding(it) }
    }

    @Override
    def run(Interpreter interpreter, Context context, List parameters) {
        Preconditions.requireParameters(parameters, paramNames.size())

        Context localContext = new Context(context)
        Iterator<String> names = paramNames.iterator()
        Iterator<Node> params = parameters.iterator()

        while (names.hasNext()) {
            localContext.setValue(names.next(), params.next())
        }

        return interpreter.eval(body, localContext)
    }

    @Override
    String toString() {
        return "UserDefinedFunction{" +
                "paramNames=" + paramNames +
                ", body=" + body +
                '}'
    }
}
