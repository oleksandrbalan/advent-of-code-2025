fun main() {
    val input = readInput("Day06")

    val data = Day06.parse(input)
    println(Day06.part1(data))
    println(Day06.part2(data))
}

private object Day06 {

    fun parse(input: List<String>): Data {
        val problems = mutableListOf<Problem>()

        var prevOperationIndex = -1
        input[input.lastIndex].forEachIndexed { index, operation ->
            if (operation != ' ') {
                if (prevOperationIndex != -1) {
                    problems.add(
                        Problem(
                            numbers = List(input.size - 1) { row ->
                                input[row].substring(prevOperationIndex, index - 1)
                            },
                            operation = input[input.lastIndex][prevOperationIndex]
                        )
                    )
                }

                prevOperationIndex = index
            }
        }

        problems.add(
            Problem(
                numbers = List(input.size - 1) { row ->
                    input[row].substring(prevOperationIndex)
                },
                operation = input[input.lastIndex][prevOperationIndex]
            )
        )

        return Data(problems)
    }

    fun part1(data: Data): Long =
        data.problems.sumOf { problem ->
            solve(
                numbers = problem.numbers.map { it.trim().toLong() },
                operation = problem.operation
            )
        }

    fun part2(data: Data): Long =
        data.problems.sumOf { problem ->
            val count = problem.numbers[0].length
            val reversedNumbers = List(count) { column ->
                problem.numbers.map { number ->
                    number[column]
                }.joinToString(separator = "")
            }

            solve(
                numbers = reversedNumbers.map { it.trim().toLong() },
                operation = problem.operation
            )
        }

    class Data(
        val problems: List<Problem>,
    )

    data class Problem(
        val numbers: List<String>,
        val operation: Char
    )

    private fun solve(numbers: List<Long>, operation: Char): Long =
        when (operation) {
            '*' -> numbers.fold(1L) { acc, value -> acc * value }
            '+' -> numbers.fold(0L) { acc, value -> acc + value }
            else -> error("Not supported operation")
        }
}
