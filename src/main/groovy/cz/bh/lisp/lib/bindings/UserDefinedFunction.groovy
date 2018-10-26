package cz.bh.lisp.lib.bindings

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Executable
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.Preconditions
import cz.bh.lisp.parser.sexp.Node

/**
 * User defined function.
 * @version 2018-10-26
 * @author Josef Baloun
 */
class UserDefinedFunction implements Executable {
    String name
    List<String> paramNames
    Node body

    UserDefinedFunction(String name, List<Node> params, Node body) {
        this.name = name
        this.body = body
        paramNames = new ArrayList<String>(params.size())

        Iterator<Node> it = params.iterator()
        while (it.hasNext()) {
            String symbolName = Preconditions.requireSymbolNodeForBinding(it.next())
            paramNames.add(symbolName)
        }
    }

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        Preconditions.requireParameters(parameters, paramNames.size())

        Context localContext = new Context(context)
        Iterator<String> names = paramNames.iterator()
        Iterator<Node> params = parameters.iterator()

        while (names.hasNext()) {
            localContext.setValue(names.next(), interpreter.eval(params.next(), context))
        }

        return interpreter.eval(body, localContext)
    }


    @Override
    public String toString() {
        return "UserDefinedFunction{" +
                "name='" + name + '\'' +
                ", paramNames=" + paramNames +
                ", body=" + body +
                '}'
    }
}
