package com.fr.fbsreport

import io.reactivex.Observable
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val list = ArrayList<String>()


        Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(400)
            it.onNext(2)
            Thread.sleep(100)
            it.onNext(3)
            Thread.sleep(200)
            it.onNext(4)
        }
                .subscribe({
                    println("Subscriber thread " + Thread.currentThread().name)
                    println(it)
                    println("On next")
                }, {
                    println("Something wrong")
                }, {
                    println("On completed")
                })
    }
}
