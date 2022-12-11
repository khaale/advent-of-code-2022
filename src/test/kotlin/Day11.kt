import org.junit.jupiter.api.Test
import java.util.*
import kotlin.math.floor

class Day11 {

    data class MonkeyNote(
        val startingItems:List<Long>,
        val operation: (Long) -> Long,
        val testDivider: Long,
        val throwToIfTrue: Int,
        val throwToIfFalse: Int
    )
    data class MonkeyState(
        val currentItems: LinkedList<Long>,
        var inspectedItems: Int
    )

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day11.txt")?.readText()!!
        val monkeyNotes = inputText
            .split(System.lineSeparator() + System.lineSeparator())
            .map {
                val lines = it.split(System.lineSeparator())
                val startingItems = lines[1]
                    .trim().replace("Starting items: ", "")
                    .split(", ")
                    .map { it.toLong() }
                val (operand1, operator, operand2) = lines[2]
                    .trim().replace("Operation: new = ", "")
                    .split(" ")
                val operation = { old:Long ->
                    fun getOperand(operand:String) = when(operand) { "old" -> old; else -> operand.toLong() }
                    val op: (Long,Long) -> Long = when (operator) { "+" -> { o1,o2 -> o1 + o2 }; else -> { o1,o2 -> o1*o2 } }
                    op(getOperand(operand1), getOperand(operand2))
                }
                val testDivider = lines[3]
                    .trim().replace("Test: divisible by ", "")
                    .toLong()
                val throwToIfTrue = lines[4]
                    .trim().replace("If true: throw to monkey ","").toInt()
                val throwToIfFalse = lines[5]
                    .trim().replace("If false: throw to monkey ","").toInt()
                MonkeyNote(startingItems, operation, testDivider, throwToIfTrue, throwToIfFalse)
            }
        fun solveInternal(rounds: Int, stressManage: (Long) -> Long) {
            val monkeyStates = monkeyNotes.map {
                MonkeyState(LinkedList(it.startingItems), 0)
            }
            fun changeState(monkeyId: Int, stressManage: (Long) -> Long) {
                val state = monkeyStates[monkeyId]
                val note = monkeyNotes[monkeyId]

                while (state.currentItems.size > 0) {
                    val oldLevel = state.currentItems.remove()
                    val newLevel = stressManage(note.operation(oldLevel))
                    val throwTo = if (newLevel % note.testDivider == 0L) note.throwToIfTrue else note.throwToIfFalse
                    monkeyStates[throwTo].currentItems.add(newLevel)
                    state.inspectedItems = state.inspectedItems + 1
                    //println("Monkey #$monkeyId throws $newLevel (ex $oldLevel) to monkey #$throwTo")
                }
            }
            //monkeyNotes.forEach { println(it) }
            repeat(rounds) {
                monkeyStates.indices.forEach{ i -> changeState(i) { stressManage(it) } }
            }
            //monkeyStates.forEach { println(it.inspectedItems) }
            monkeyStates
                .sortedByDescending { it.inspectedItems }
                .take(2)
                .map { it.inspectedItems.toLong() }
                .reduce { acc, i -> acc * i  }
                .let { println("Monkey business level = $it") }
        }
        //Part One
        solveInternal(20) { floor(it.toDouble() / 3).toLong() }
        //Part Two
        val commonDivider = monkeyNotes.map { it.testDivider }.reduce { acc, i -> acc * i }
        solveInternal(10000) { it % commonDivider }
    }
}