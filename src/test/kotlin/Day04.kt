import org.junit.jupiter.api.Test

class Day04 {

    @Test
    fun solve() {
        // Load input
        fun<T> String.splitOnPair(sep: String, mapper: (value:String) -> T): Pair<T,T> {
            val tokens = split(sep)
            return mapper(tokens[0]) to mapper(tokens[1])
        }
        val inputText = this.javaClass.getResource("Day04.txt")?.readText()!!
        val assignments = inputText
            .split(System.lineSeparator())
            .map { a ->
                a.splitOnPair(",") { ea ->
                    ea.splitOnPair("-") { x -> x.toInt() }
                }
            }
        //Part One
        fun solve1() {
            val count = assignments.count { a ->
                (a.first.first <= a.second.first && a.first.second >= a.second.second) ||
                        (a.second.first <= a.first.first && a.second.second >= a.first.second)

            }
            println("Part One: count = $count")
        }
        solve1()
        //Part Two
        fun solve2() {
            val count = assignments.count { a ->
                (a.first.first <= a.second.second) && (a.first.second >= a.second.first)
            }
            println("Part Two: count = $count")
        }
        solve2()
    }
}