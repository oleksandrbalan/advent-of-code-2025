fun main() {
    val input = readInput("Day03")

    val data = Day03.parse(input)
    println(Day03.part1(data))
    println(Day03.part2(data))
}

private object Day03 {

    data class Bank(
        val values: List<Int>,
    )

    data class Joltage(
        val size: Int,
    ) {
        private val values: MutableList<Int> = MutableList(size) { -1 }

        fun toLong(): Long =
            values.joinToString(separator = "").toLong()

        fun get(index: Int): Int = values[index]

        fun update(index: Int, value: Int) {
            values[index] = value
            if (index != values.lastIndex) {
                update(index + 1, -1)
            }
        }
    }

    fun parse(input: List<String>): List<Bank> =
        input.map { line ->
            Bank(
                line.map {
                    it.digitToInt()
                }
            )
        }

    fun part1(banks: List<Bank>): Long =
        banks.sumOf { bank ->
            getJoltage(bank, 2)
        }

    fun part2(banks: List<Bank>): Long =
        banks.sumOf { bank ->
            getJoltage(bank, 12)
        }

    private fun getJoltage(bank: Bank, size: Int): Long {
        val joltage = Joltage(size)

        bank.values.forEachIndexed { i, jolt ->
            repeat(joltage.size) { j ->
                if (i <= bank.values.size - size + j && jolt > joltage.get(j)) {
                    joltage.update(j, jolt)
                    return@forEachIndexed
                }
            }
        }

        return joltage.toLong()
    }
}
