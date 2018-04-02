package au.com.letspay.letspayapp

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var temp: Float = 0.0f

    @Test
    fun testTets() {
        temp = -2.5f
        val ss = NumberFormat.getCurrencyInstance()
        ss.currency = Currency.getInstance(Locale.getDefault())
        ss.format(temp)
        Assert.assertEquals("-$2.50", ss.format(temp))
    }

    fun get(): String {
        return if (temp < 0.0f) {
            val str = temp.toString()
            str[0] + "$" + str.drop(1)
        } else {
            temp.toString()
        }

    }

}
