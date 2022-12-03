import org.junit.jupiter.api.Test

class Day02 {

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day2.txt")?.readText()!!
        val strategy = inputText
            .split("\n")
            .map { round ->
                val choices = round.split(" ")
                (choices[0] to choices[1])
            }
        // Part One
        fun solve1() {
            val myScoreMap = mapOf("X" to 1, "Y" to 2, "Z" to 3)
            val drawSet = setOf("A" to "X", "B" to "Y", "C" to "Z")
            val winSet = setOf("A" to "Y", "B" to "Z", "C" to "X")
            val totalScore = strategy.sumOf { round ->
                myScoreMap[round.second]!! +
                        (if (drawSet.contains(round)) 3 else 0) +
                        (if (winSet.contains(round)) 6 else 0)
            }
            println("Part One: score = $totalScore")
        }
        solve1()
        // Part Two
        fun solve2() {
            val scoreMap = mapOf("A" to 1, "B" to 2, "C" to 3)
            val winMap = mapOf("A" to "B", "B" to "C", "C" to "A")
            val loseMap = mapOf("A" to "C", "B" to "A", "C" to "B")
            val totalScore = strategy.sumOf { round ->
                val opponentChoice = round.first
                when (round.second) {
                    "Z" -> { scoreMap[winMap[opponentChoice]]!! + 6 }
                    "Y" -> { scoreMap[opponentChoice]!! + 3 }
                    else -> { scoreMap[loseMap[opponentChoice]]!! }
                }
            }
            println("Part Two: score = $totalScore")
        }
        solve2()
    }
}