package cz.bh.lisp

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class ConsoleRepl extends Repl {

    void start(Reader reader, Writer writer) {
        def interpreter = createInterpreter(writer)

        BufferedReader br = new BufferedReader(reader)
        String input
        while ((input = br.readLine()) != null && input != 'exit') {
            interpreter.eval(input)
        }

        // TODO: dělat to podle závorek, ne podle newline
        /*
        def builder = new StringBuilder()
        int next
        while ((next = reader.read()) != -1) {
            if (next == ')' as char) {
            }
        }
        */
    }

}
