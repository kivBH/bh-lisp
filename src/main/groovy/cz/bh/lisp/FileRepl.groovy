package cz.bh.lisp

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class FileRepl extends Repl {

    void start(Reader reader, Writer writer) {
        def interpreter = createInterpreter(writer)
        interpreter.eval(wrapReader(reader, writer))
    }

    private Reader wrapReader(Reader reader, Writer writer) {
        // writes the loaded characters to the output
        new Reader() {
            @Override
            int read(char[] cbuf, int off, int len) throws IOException {
                def result = reader.read(cbuf, off, len)
                writer.write(cbuf, off, len)
                writer.flush()
                return result
            }

            @Override
            void close() throws IOException {
                reader.close()
            }
        }
    }

}
