import kotlin.math.abs

fun main() {
    val input = readInput("Day01")

    val data = Day01.parse(input)
    println(Day01.part1(data))
    println(Day01.part2(data))
}

private object Day01 {

    data class Rotation(val value: Int)

    fun parse(input: List<String>): List<Rotation> =
        input.map {
            val value = it.substring(1).toInt()

            when {
                it.startsWith("R") -> Rotation(+value)
                it.startsWith("L") -> Rotation(-value)
                else -> error("Unknown rotation for $it")
            }
        }

    fun part1(rotations: List<Rotation>): Int {
        var counter = 0
        var value = 50
        rotations.forEach {
            value = (value + it.value) % 100
            if (value == 0) {
                counter += 1
            }
        }

        return counter
    }

    fun part2(rotations: List<Rotation>): Int {
        val oneByOneRotations = rotations.flatMap {
            val value = abs(it.value)
            if (it.value > 0) {
                List(value) { Rotation(+1) }
            } else {
                List(value) { Rotation(-1) }
            }
        }

        return part1(oneByOneRotations)
    }
}
