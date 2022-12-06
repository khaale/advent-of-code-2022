import org.junit.jupiter.api.Test

class Day05 {

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day05.txt")?.readText()!!
        val initialStackLines = inputText
            .split(System.lineSeparator() + System.lineSeparator())[0]
            .split(System.lineSeparator())
        val stepRegex = Regex("""move (\d+) from (\d+) to (\d+)""")
        val steps = inputText
            .split(System.lineSeparator() + System.lineSeparator())[1]
            .split(System.lineSeparator())
            .map { line ->
                val (move, from, to) = stepRegex.find(line)!!.destructured
                Triple(move.toInt(), from.toInt()-1, to.toInt()-1)
            }
        //Part One
        fun solve1() {
            val stacks = initializeStacks(initialStackLines)
            println(stacks)
            steps.forEach { step ->
                for (i in 0 until step.first) {
                    stacks[step.third].addLast(stacks[step.second].removeLast())
                }
            }
            println(stacks)
            val lastCrates = stacks.map { s -> s.last() }.joinToString("")
            println("Part One: Last crates - $lastCrates")
        }
        solve1()
        //Part Two
        fun solve2() {
            val stacks = initializeStacks(initialStackLines)
            println(stacks)
            steps.forEach { step ->
                val stackFrom = stacks[step.second]
                val stackTo = stacks[step.third]
                val initialLen = stackFrom.size
                for (i in 0 until step.first) {
                    stackTo.addLast(stackFrom.removeAt(initialLen - step.first))
                }
                //println(stacks)
            }
            println(stacks)
            val lastCrates = stacks.map { s -> s.last() }.joinToString("")
            println("Part Two: last crates - $lastCrates")
        }
        solve2()
    }

    private fun initializeStacks(initialStackLines: List<String>): List<ArrayDeque<Char>> {
        val stacksCount = initialStackLines.takeLast(1)[0].replace(" ", "").length
        val stacks: List<ArrayDeque<Char>> = List(stacksCount) { ArrayDeque() }
        initialStackLines
            .dropLast(1)
            .forEach { stackLine ->
                var pos = 0
                while (pos < stackLine.length) {
                    if (stackLine[pos] == '[') {
                        val stackIndex = pos / 4
                        stacks[stackIndex].addFirst(stackLine[pos + 1])
                        pos += 3;
                    }
                    pos++
                }
            }
        return stacks
    }
}