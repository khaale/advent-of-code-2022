import org.junit.jupiter.api.Test

class Day08 {

    @Test
    fun solve() {
        // Load input
        val lines = this.javaClass.getResource("Day08.txt")?.readText()!!.split(System.lineSeparator())
        val size = lines.size
        val map = Array(size) { Array(size) { 0 }}
        lines.forEachIndexed { i, line ->
            line.forEachIndexed { j, h ->
                map[i][j] = h.digitToInt()
            }
        }
        fun getVisibilityAndScore(point: Pair<Int, Int>): List<Pair<Boolean, Long>>  {
            val h = map[point.first][point.second]
            fun getVisibilityAndDistanceInDirection(direction: Pair<Int,Int>): Pair<Boolean,Long> {
                var current = point
                var distance: Long = -1
                while (with (current) {
                        (first in 0 until size) && (second in 0 until size)
                    }) {
                    distance++
                    if (current != point && map[current.first][current.second] >= h)
                        return false to distance
                    current = (current.first + direction.first) to (current.second + direction.second)

                }
                return true to distance
            }
            return listOf(
                getVisibilityAndDistanceInDirection(-1 to 0),
                getVisibilityAndDistanceInDirection(0 to -1),
                getVisibilityAndDistanceInDirection(0 to 1),
                getVisibilityAndDistanceInDirection(1 to 0)
            )
        }
        //Part One
        fun solve1() {
            var visibleCount = 4 + (size-2)*4
            for (i in 1 until size-1) {
                for (j in 1 until size-1) {
                    if (getVisibilityAndScore(i to j).any { it.first }) visibleCount++
                }
            }
            println("Part One: visible trees count = $visibleCount")
        }
        solve1()
        //Part Two
        fun solve2() {
            val bestScenicScore = map.indices.flatMap { i ->
                map.indices.map { j ->
                    getVisibilityAndScore(i to j).map { it.second }.reduce{ acc,score -> acc*score }
                }
            }.maxOf { it }
            println("Part Two: best scenic score = $bestScenicScore")
        }
        solve2()
    }
}