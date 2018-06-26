package com.fr.fbsreport

import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val lady: String by lazy {
        val name = Random().nextInt(10).toString()
        name
    }

    open class ViewTest<T> where T : String {
        fun ok(): String {
            return "okf"
        }
    }

    class ViewChild<T : String> : ViewTest<T>() {
        var a: Int = 0
        fun fail() {

        }
    }

    lateinit var test: ViewTest<String>

    @Test
    fun addition_isCorrect() {
        test = ViewChild()
        println(test.ok())
    }
}
