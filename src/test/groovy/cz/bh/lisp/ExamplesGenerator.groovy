package cz.bh.lisp

/**
 *
 * @version 2018-12-10
 * @author Patrik Harag
 */
class ExamplesGenerator {

    static void main(def args) {
        def text = new File("src/test/resources/examples.txt").text
        text.split('\n\r').each {
            println "```clojure"
            println it.trim()
            println "```"
            println ""
        }
    }

}
