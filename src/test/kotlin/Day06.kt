import org.junit.jupiter.api.Test

class Day06 {

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day06.txt")?.readText()!!
        //val inputText = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        fun indexAt(i:Int) = inputText[i].code - 'a'.code
        fun solve(distinctChars: Int) {
            val counters = Array(26) { 0 }
            fun incrementBuffer(i: Int, inc: Int) { counters[indexAt(i)] = counters[indexAt(i)] + inc }
            for (i in inputText.indices) {
                if (i > distinctChars-1) {
                    incrementBuffer(i-distinctChars, -1)
                }
                incrementBuffer(i, 1)
                if (counters.count { c -> c > 0} == distinctChars) {
                    println("Distinct chars - $distinctChars, marker after ${i+1}")
                    break
                }
            }
        }
        //Part One
        solve(4)
        //Part Two
        solve(14)
    }
}