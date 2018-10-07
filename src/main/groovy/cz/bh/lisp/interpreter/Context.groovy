package cz.bh.lisp.interpreter

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Context {

    static final def NIL = new Object()

    private final Context parentContext
    private final Map<String, Object> variables = [:]

    Context(Context parentContext) {
        this.parentContext = parentContext
    }

    Context() {
        this.parentContext = null
    }

    void setValue(String symbol, Object value) {
        assert value != null && symbol != null
        variables.put(symbol, value)
    }

    Object getValue(String symbol) {
        def value = variables.get(symbol)
        return (value == null && parentContext) ? parentContext.getValue(symbol) : value
    }

}
