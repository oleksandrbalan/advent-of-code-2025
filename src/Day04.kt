fun main() {
    val input = readInput("Day04")

    val data = Day04.parse(input)
    println(Day04.part1(data))
    println(Day04.part2(data))
}

private object Day04 {

    fun parse(input: List<String>): Data =
        Data(
            map = input.map { it.toCharArray().toMutableList() }.toMutableList(),
        )

    fun part1(data: Data): Int =
        data.indices.count { (row, column) ->
            data.get(row, column) == '@' && data.getAdjacentRollsCount(row, column) < 4
        }

    fun part2(data: Data): Int =
        data.indices.sumOf { (row, column) ->
            data.removeRollIfPossible(row, column)
        }

    private fun getAdjacent(row: Int, column: Int): List<Pair<Int, Int>> =
        adjacentOffsets.map {
            (it.first + row) to (it.second + column)
        }

    class Data(
        private val map: MutableList<MutableList<Char>>,
    ) {
        val width: Int = map.firstOrNull()?.size ?: 0

        val height: Int = map.size

        val indices: List<Pair<Int, Int>> = List(width * height) { it / width to it % width }

        fun get(row: Int, column: Int): Char? =
            map.elementAtOrNull(row)?.elementAtOrNull(column)

        fun getAdjacentRollsCount(row: Int, column: Int): Int =
            getAdjacent(row, column)
                .count { (aRow, aColumn) ->
                    get(aRow, aColumn) == '@'
                }

        fun removeRollIfPossible(row: Int, column: Int): Int {
            if (get(row, column) != '@') {
                return 0
            }

            val adjacent = getAdjacent(row, column)
            val count = adjacent.count { (aRow, aColumn) -> get(aRow, aColumn) == '@' }
            if (count >= 4) {
                return 0
            }
            map[row][column] = '.'
            return 1 + adjacent.sumOf { (aRow, aColumn) ->
                removeRollIfPossible(aRow, aColumn)
            }
        }
    }

    private val adjacentOffsets = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1,
    )
}
