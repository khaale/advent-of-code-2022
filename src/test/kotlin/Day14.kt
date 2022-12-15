import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

class Day14 {

    @Test
    fun solve() {
        // Load input
        val lines = this.javaClass.getResource("Day14_Test.txt")?.readText()!!
            .split(System.lineSeparator())
            .map { line ->
                line.split(" -> ").map { point ->
                    val (x, y) = point.split(",")
                    x.toInt() to y.toInt()
                }
            }
        val yMin = 0
        val yMax = lines.flatten().maxOf { it.second } + 2
        val xMin = 500 - (yMax) - 1
        val xMax = 500 + (yMax) + 1

        println("${xMax - xMin} x ${yMax - yMin}")

        fun isFilled(point: Pair<Int,Int>) =
            lines.any { line ->
                line.indices.drop(1).any { i ->
                    val x1 = min(line[i-1].first, line[i].first)
                    val x2 = max(line[i-1].first, line[i].first)
                    val y1 = min(line[i-1].second, line[i].second)
                    val y2 = max(line[i-1].second, line[i].second)
                    val x = point.first + xMin
                    val y = point.second + yMin
                    val filled = (x in x1..x2) && (y in y1..y2)
                    filled
                }
            }
        fun solveInternal(map: Array<Array<Char>>): Int {
            fun printMap() {
                map.forEach { l -> l.forEach { p -> print(p) }; println() }; println()
            }
            fun getMapState(p: Pair<Int,Int>) =
                if (p.second in (yMin .. yMax) && p.first in (xMin .. xMax))
                    map[p.second - yMin][p.first - xMin]
                else '.'
            fun setMapState(p: Pair<Int,Int>, c:Char) {
                map[p.second - yMin][p.first - xMin] = c
            }
            fun fall(p: Pair<Int,Int>): Boolean {
                if (!(p.second in (yMin .. yMax) && p.first in (xMin .. xMax))) {
                    return false
                }
                return if (getMapState(p.first to p.second + 1) == '.') {
                    fall(p.first to p.second + 1)
                } else if (getMapState(p.first - 1 to p.second + 1) == '.') {
                    fall(p.first - 1 to p.second + 1)
                } else if (getMapState(p.first + 1 to p.second + 1) == '.') {
                    fall(p.first + 1 to p.second + 1)
                } else {
                    setMapState(p, 'o')
                    !(p.first == 500 && p.second == 0)
                }
            }
            var i = 0
            while(fall(500 to 0)) {
                i++
            }
            printMap()
            return i
        }
        //Part One
        val bottomlessMap = Array(yMax - yMin + 1) { y -> Array(xMax - xMin + 1) { x ->
            if (isFilled(x to y)) '#'
            else '.'
        } }
        solveInternal(bottomlessMap).let { println("Part One: $it") }
        //Part Two
        val mapWithFloor = Array(yMax - yMin + 1) { y -> Array(xMax - xMin + 1) { x ->
            if (y == yMax) '#'
            else if (isFilled(x to y)) '#'
            else '.'
        } }
        solveInternal(mapWithFloor).let { println("Part Two: ${it+1}") }
    }
}