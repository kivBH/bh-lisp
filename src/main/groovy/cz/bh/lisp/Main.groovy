package cz.bh.lisp

import cz.bh.lisp.interpret.Interpreter
import cz.bh.lisp.lib.LibLoader

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Main {

    static void main(String... args) {
        println "BH Lisp REPL"

        def interpreter = new Interpreter(LibLoader.createGlobalContext(), {
            println ">> $it"
        })

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        String input
        while ((input = br.readLine()) != null && input != 'exit') {
            interpreter.eval(input)
        }
    }

}
