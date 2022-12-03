import org.junit.jupiter.api.Test

class Day03 {

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day03.txt")?.readText()!!
        val rucksacks = inputText
            .split(System.lineSeparator())
        fun getPriority(sym: Char) =
            if (sym.isLowerCase()) { sym.code - 'a'.code + 1 } else { sym.code - 'A'.code + 27 }
        //Part One
        fun solve1() {
            val sumOfPriorities = rucksacks.sumOf { r ->
                val compartment1 = r.take(r.length / 2).toSet()
                val compartment2 = r.takeLast(r.length / 2).toSet()
                val wrongItem = compartment1.intersect(compartment2).first()
                val priority = getPriority(wrongItem)
                priority
            }
            println("Part One: sum of priorities = $sumOfPriorities")
        }
        solve1()
        //Part Two
        fun solve2() {
            val sumOfPriorities = rucksacks.chunked(3).sumOf { group ->
                val common = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet()).first()
                getPriority(common)
            }
            println("Part One: sum of priorities = $sumOfPriorities")
        }
        solve2()
    }
}