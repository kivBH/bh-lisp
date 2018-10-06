package cz.bh.lisp.lib.math

import org.junit.Test

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class MinusTest {

    def minus = new Minus()

    @Test(expected = IllegalArgumentException)
    void testEmpty() {
        minus.run([])
    }

    @Test
    void testUnary() {
        def result = minus.run([BigInteger.ONE])
        assert result == -1
        assert result instanceof BigInteger
    }

    @Test
    void testInt() {
        def result = minus.run([10, 5, 2])
        assert result == 3
        assert result instanceof Integer
    }

    @Test
    void testFloat() {
        def result = minus.run([10, 5.5])
        assert result == 4.5
        assert result instanceof BigDecimal
    }
}
