import org.junit.jupiter.api.Test

class Day01 {

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day1.txt")?.readText()!!
        val elves = inputText
            .split("\n\n")
            .map { inv ->
                inv.split("\n").map { invLine -> invLine.toInt() }
            }
        //Part One
        val maxInv = elves
            .map { line -> line.sum() }
            .maxOf { inv  -> inv }
        println("Part One: max inventory = $maxInv")
        //Part Two
        val sumOfTop3MaxInv = elves
            .map { inv -> inv.sum() }
            .sortedDescending()
            .take(3)
            .sum()
        println("Part Two: sum of top 3 max inventories = $sumOfTop3MaxInv")
    }
}