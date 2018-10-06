package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Preconditions {

    static void requireParameters(List parameters, int count) {
        if (parameters.size() != count) {
            throw new IllegalArgumentException(
                    "Expected number of parameters: ${count}, but was: ${parameters.size()}")
        }
    }

    static void requireParametersAtLeast(List parameters, int count) {
        if (parameters.size() < count) {
            throw new IllegalArgumentException(
                    "Expected number of parameters: ${count}+, but was: ${parameters.size()}")
        }
    }

    static <T> T requireType(instance, Class<T> clazz) {
        if (!clazz.isInstance(instance)) {
            throw new IllegalArgumentException(
                    "Expected parameter type: ${clazz}, but was: ${instance.class}")
        }
        return (T) instance
    }

}
