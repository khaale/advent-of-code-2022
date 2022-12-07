import org.junit.jupiter.api.Test

class Day07 {

    data class File(val name: String, val size: Int)
    data class Dir(
        val name: String,
        //val parent: Dir? = null,
        val dirs: ArrayList<Dir> = ArrayList(),
        val files: ArrayList<File> = ArrayList()
    ) {
        val size: Int
            get() = dirs.sumOf { d -> d.size } + files.sumOf { f -> f.size }
    }

    @Test
    fun solve() {
        // Load input
        val inputText = this.javaClass.getResource("Day07.txt")?.readText()!!
        val terminalOutput = inputText
            .split(System.lineSeparator())
        // Building dir tree
        val root = Dir("/")
        val stack = ArrayDeque<Dir>()
        stack.addLast(root)
        terminalOutput.drop(1).forEach { line ->
            val currentDir = stack.last()
            if (line.startsWith("$ cd")) {
                if (line != "\$ cd ..") {
                    val nextDirName = line.replace("\$ cd ", "")
                    stack.addLast(
                        currentDir.dirs.find { d -> d.name == nextDirName }!!
                    )
                } else {
                    stack.removeLast()
                }
            } else if (!line.startsWith("$")) {
                if (line.startsWith("dir ")) {
                    val dir = Dir(line.replace("dir ", ""))
                    currentDir.dirs.add(dir)
                } else {
                    val tokens = line.split(" ")
                    val file = File(tokens[1], tokens[0].toInt())
                    currentDir.files.add(file)
                }
            }
        }
        //Part One
        fun solve1() {
            fun sumDirSizes(dir: Dir): Int =
                (if (dir.size <= 100000) dir.size else 0) +
                        dir.dirs.sumOf { d -> sumDirSizes(d) }
            println("Part One: sum of small dir size = ${sumDirSizes(root)}")
        }
        solve1()
        //Part Two
        fun solve2() {
            fun dirSizes(dir: Dir): List<Int> =
                listOf(dir.size) + dir.dirs.flatMap { d -> dirSizes(d) }
            val dirSizes = dirSizes(root)
            val totalSpace = 70000000
            val unusedSpace = totalSpace - root.size
            val spaceToFree = 30000000 - unusedSpace
            val dirToDelete = dirSizes
                .filter { s -> s > spaceToFree }
                .minOf { it }
            println("Part Two: dir size to delete = $dirToDelete")

        }
        solve2()
    }
}