package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.Context

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class LibLoader {

    static Context createGlobalContext() {
        def loader = ServiceLoader.load(Definition)

        def context = new Context()
        loader.each {
            context.setValue(it.symbol, it.value)
        }
        return context
    }

}
