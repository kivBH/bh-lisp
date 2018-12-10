package cz.bh.lisp

import cz.bh.lisp.lib.Documented
import cz.bh.lisp.lib.LibLoader

/**
 *
 * @version 2018-12-10
 * @author Patrik Harag
 */
class FunctionListGenerator {

    static void main(def args) {
        def variables = LibLoader.createGlobalContext().collectVariables()

        removeAndPrint(variables, 'cz.bh.lisp.lib.math', 'Math')
        removeAndPrint(variables, 'cz.bh.lisp.lib.collections', 'Collections')
        removeAndPrint(variables, 'cz.bh.lisp.lib.conditionals', 'Conditionals')
        removeAndPrint(variables, 'cz.bh.lisp.lib.bindings', 'Bindings')
        removeAndPrint(variables, 'cz.bh.lisp.lib.meta', 'Meta')
        removeAndPrint(variables, 'cz.bh.lisp.lib.flow', 'Flow')
        removeAndPrint(variables, 'cz.bh.lisp.lib.exceptions', 'Exceptions')
    }

    static removeAndPrint(Map<String, ?> variables, String pkg, String title) {
        def removed = remove(variables, pkg)

        println "### $title"
        println "| Name | Description |"
        println "| --- | --- |"
        removed.findAll { it.value instanceof Documented }.each {
            println "| `${ escape(it.key) }` | ${ escape(it.value.doc) } |"
        }
    }

    static Map<String, ?> remove(Map<String, ?> variables, String pkg) {
        def removed = variables.findAll { it.value.class.name.startsWith(pkg) }
        removed.each {
            variables.remove(it.key)
        }
        removed
    }

    static escape(String text) {
        text.replaceAll('\\s+', ' ')
            .replace('[', '\\[')
            .replace(']', '\\]')
    }

}
