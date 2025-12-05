fun main() {
    val input = readInput("Day05")

    val data = Day05.parse(input)
    println(Day05.part1(data))
    println(Day05.part2(data))
}

private object Day05 {

    fun parse(input: List<String>): Data {
        val ranges = mutableListOf<LongRange>()
        val products = mutableListOf<Long>()

        input.forEach { line ->
            val range = line.split("-")
            if (range.size == 2) {
                ranges.add(
                    LongRange(
                        range[0].toLong(),
                        range[1].toLong(),
                    )
                )
            } else if (line.isNotEmpty()) {
                products.add(
                    line.toLong()
                )
            }
        }

        return Data(
            freshRanges = ranges,
            availableProducts = products,
        )
    }

    fun part1(data: Data): Int =
        data.availableProducts.count { product ->
            data.freshRanges.any { product in it }
        }

    fun part2(data: Data): Long =
        normalizedRanges(data.freshRanges)
            .sumOf { it.last - it.first + 1 }

    private fun normalizedRanges(ranges: List<LongRange>): List<LongRange> {
        val normalizedRanges = mutableListOf<LongRange>()

        ranges.sortedBy { it.first }.forEach { range ->
            var rangeCandidate = range
            while (true) {
                val newRange = checkOverlap(normalizedRanges, rangeCandidate)
                if (newRange.isEmpty()) {
                    break
                }
                if (newRange == rangeCandidate) {
                    normalizedRanges.add(rangeCandidate)
                    break
                }
                rangeCandidate = newRange
            }
        }

        return normalizedRanges
    }

    private fun checkOverlap(ranges: List<LongRange>, range: LongRange): LongRange {
        var newRange = range
        val overlapStart = ranges.find { newRange.first in it }
        if (overlapStart != null) {
            newRange = LongRange(overlapStart.last + 1, newRange.last)
        }
        val overlapEnd = ranges.find { newRange.last in it }
        if (overlapEnd != null) {
            newRange = LongRange(newRange.first, overlapEnd.first - 1)
        }
        return newRange
    }

    class Data(
        val freshRanges: List<LongRange>,
        val availableProducts: List<Long>
    )
}
