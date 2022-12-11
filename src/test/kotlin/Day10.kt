import org.junit.jupiter.api.Test
import java.util.*

class Day10 {

    @Test
    fun solve() {
        // Load input
        val commands = this.javaClass.getResource("Day10.txt")?.readText()!!.split(System.lineSeparator())
        //Part One
        fun solve1() {
            var sumOfStrengths = 0
            val cycles: Queue<Int> = LinkedList(listOf(20,60,100,140,180,220))
            var currentCycle = 0
            var x = 1
            fun checkCycle(): Boolean {
                if (cycles.size == 0) {
                    return false
                }
                else if (currentCycle >= cycles.first()) {
                    val cycle = cycles.remove()
                    sumOfStrengths += cycle * x
                    println("$cycle: $x")
                }
                return true
            }
            for (command in commands) {
                if (command == "noop") {
                    currentCycle++
                    if (!checkCycle()) break
                }
                else {
                    val inc = command.replace("addx ", "").toInt()
                    currentCycle += 2
                    if (!checkCycle()) break
                    x += inc
                }
            }
            println("Part One: sum of strenghts: $sumOfStrengths")
        }
        solve1()
        //Part Two
        fun solve2() {
            var x = 1
            var xIncrement = 0
            val commandsQueue = LinkedList(commands)
            for (cycle in 0 until 240) {
                if (cycle % 40 == 0) println()
                val pixel = if ((cycle%40) in (x-1)..(x+1)) "#" else '.'
                print(pixel)
                if (xIncrement != 0) {
                    x += xIncrement
                    xIncrement = 0
                } else {
                    val command = commandsQueue.remove()
                    if (command != "noop") {
                        xIncrement = command.replace("addx ", "").toInt()
                    }
                }
            }
        }
        solve2()
    }
}