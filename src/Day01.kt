fun main() {
    val writtenNumbersRegex = Regex("^(zero|one|two|three|four|five|six|seven|eight|nine).*")
    val writtenNumbersRegexReverse = Regex("^(orez|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin).*")
    val writtenNumbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "zero" to 0,
        "eno" to 1,
        "owt" to 2,
        "eerht" to 3,
        "ruof" to 4,
        "evif" to 5,
        "xis" to 6,
        "neves" to 7,
        "thgie" to 8,
        "enin" to 9,
        "orez" to 0
    )

    fun getFiveCharsWithOffset(input: String, offset: Int): String? {
        if (offset + 5 <= input.length) {
            return input.substring(offset..offset + 4)
        } else if (offset + 4 == input.length) {
            return "${input.substring(offset..offset + 3)} "
        } else if (offset + 3 == input.length) {
            return "${input.substring(offset..offset + 2)}  "
        }
        return null
    }

    fun getFirstDigit(input: String, reverse: Boolean = false): Int {
        for (i in input.indices) {
            if (input[i].isDigit()) {
                return input[i].digitToInt()
            }
            val fiveChars = getFiveCharsWithOffset(input, i) ?: continue
            return if (reverse) {
                val result = writtenNumbersRegexReverse.find(fiveChars) ?: continue
                writtenNumbers[result.groups[1]?.value] ?: throw Error("something went wrong")
            } else {
                val result = writtenNumbersRegex.find(fiveChars) ?: continue
                writtenNumbers[result.groups[1]?.value] ?: throw Error("something went wrong")
            }
        }
        throw Error("something went wrong")
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val filteredLine = line.filter { it.code in 48..57 }
            sum += "${filteredLine.first()}${filteredLine.last()}".toInt()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val firstDigit = getFirstDigit(line)
            val lastDigit = getFirstDigit(line.reversed(), reverse = true)
            sum += "${firstDigit.digitToChar()}${lastDigit.digitToChar()}".toInt()
        }
        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testTwoInput = readInput("Day01_test2")
    check(part2(testTwoInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
