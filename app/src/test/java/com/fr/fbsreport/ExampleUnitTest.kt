package com.fr.fbsreport

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val time = "2018-07-01"
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = format1.parse(time)
        val c = Calendar.getInstance()
        c.time = dt1
        println(c.get(Calendar.DAY_OF_WEEK))
    }
}
