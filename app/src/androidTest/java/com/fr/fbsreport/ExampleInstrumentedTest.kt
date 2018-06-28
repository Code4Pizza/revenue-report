package com.fr.fbsreport

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.SparseArray
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.fr.fbsreport", appContext.packageName)
    }

    @Test
    fun testMap() {
        val maps = SparseArray<Float>()
        maps.put(12, 1F)
        maps.put(1, 3F)
        maps.put(2, 5F)
        maps.put(3, 1F)
        maps.put(4, 7F)
        maps.put(5, 12F)
        maps.put(6, 9F)

        for (i in 0 until maps.size()) {
            val key = maps.keyAt(i)
            println(key)
            println(maps[key])
        }
    }
}
