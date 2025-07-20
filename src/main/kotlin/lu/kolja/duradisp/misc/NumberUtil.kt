package lu.kolja.duradisp.misc

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.round

object NumberUtil {
    private val SUFFIXES = arrayOf("", "K", "M", "B", "T", "P", "E", "Z", "Y")

    /**
     * Formats positive numbers with suffixes (K, M, B, T, etc.) with one decimal place.
     * Negative numbers will be returned as-is without formatting.
     */
    fun formatNumber(number: Double): String {
        var number = number

        if (number < 0) {
            return number.toString()
        }

        var suffixIndex = 0

        while (number >= 1000 && suffixIndex < SUFFIXES.size - 1) {
            number /= 1000.0
            suffixIndex++
        }

        // Truncate to two decimal places
        val truncated = floor(number * 100) / 100.0

        // Format without trailing zeros
        val df = DecimalFormat("#.##")
        val result = df.format(truncated)

        return result + SUFFIXES[suffixIndex]
    }

    /**
     * Formats a number as a percentage with one decimal place.
     * @param number The number to format as percentage (where 1.0 = 100%)
     * @return Formatted percentage string with one decimal place
     */
    fun formatPercentage(number: Double): String {
        val percentage = number * 100

        var result = String.format("%.1f", round(percentage * 10) / 10.0)

        // Remove trailing zero if the decimal part is exactly .0
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length - 2)
        }

        return "$result%"
    }
}