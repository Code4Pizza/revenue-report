package com.fr.fbsreport

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("Start")
//        launch {
//            //delay(1000)
//            println("Hello")
//        }
        runBlocking {
            //delay(1000)
            println("Hello") 
        }
        println("Stop")
    }
}
