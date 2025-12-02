import kotlin.math.log10
import kotlin.math.pow

fun main() {
    val input = readInput("Day02")

    val data = Day02.parse(input)
    println(Day02.part1(data))
    println(Day02.part2(data))
}

private object Day02 {

    fun parse(input: List<String>): List<LongRange> =
        input.flatMap { line ->
            line.split(",").map { pair ->
                val range = pair.split("-")
                val from = range[0].toLong()
                val to = range[1].toLong()
                LongRange(from, to)
            }
        }

    fun part1(ranges: List<LongRange>): Long =
        ranges.sumOf { range ->
            range.sumOf { number ->
                if (isSimpleSillyPattern(number)) {
                    number
                } else {
                    0
                }
            }
        }

    fun part2(ranges: List<LongRange>): Long =
        ranges.sumOf { range ->
            range.sumOf { number ->
                if (isAdvancedSillyPattern(number)) {
                    number
                } else {
                    0
                }
            }
        }

    private fun isSimpleSillyPattern(number: Long): Boolean {
        val length = log10(number.toFloat()).toInt() + 1
        if (length % 2 != 0) {
            return false
        }

        val base = 10f.pow(length / 2).toInt()
        val firstHalf = number / base
        val secondHalf = number % base
        return firstHalf == secondHalf
    }

    private fun isAdvancedSillyPattern(number: Long): Boolean {
        val length = log10(number.toFloat()).toInt() + 1

        (1..length / 2).forEach { size ->
            val windows = number.toString().windowed(size, size, true)
            val last = windows.last()
            if (last.length == size && windows.all { it == last }) {
                return true
            }
        }

        return false
    }
}
