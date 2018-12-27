package cz.bh.lisp.parser.lexer

/**
 * Class for wrapping reader.
 * Allows ignoring characters when reading.
 *
 * @author Josef Baloun
 */
class ReaderWrapper {
    Reader reader

    ReaderWrapper(Reader reader) {
        this.reader = reader
    }

    public int read() {
        int c
        while ((c = reader.read()) == '\r') {   // ignoring \r
        }
        return c
    }
}
