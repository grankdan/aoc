fun main() {
    val dot = '.'
    val gear = '*'

    class PartNumber(val value: Int, val x: Int, val length: Int)
    class Symbol(val value: Char, val x: Int) {
        fun isAdjacentTo(partNumber: PartNumber): Boolean {
            return x in partNumber.x - 1..partNumber.x + partNumber.length
        }
    }

    class InputLine(val partNumbers: List<PartNumber>, val symbols: List<Symbol>)


    fun extractNumberFromString(input: String, offset: Int): Int {
        val pattern = "\\d+".toRegex()
        val result = pattern.find(input, offset)
        return result?.value?.toInt() ?: throw Error("Something went wrong")

    }

    fun parseLine(input: String): InputLine {
        val partNumbers = mutableListOf<PartNumber>()
        val symbols = mutableListOf<Symbol>()

        var i = 0
        while (i < input.length) {
            if (input[i] == dot) {
                i++
                continue
            } else if (!input[i].isDigit()) symbols.add(Symbol(input[i], i))
            else {
                val number = extractNumberFromString(input, i)
                val numberLength = number.toString().length
                partNumbers.add(PartNumber(number, i, numberLength))
                i += numberLength - 1
            }
            i++
        }
        return InputLine(partNumbers, symbols)
    }

    fun hasAdjacentSymbol(partNumber: PartNumber, symbols: List<Symbol>): Boolean {
        for (symbol in symbols) {
            if (symbol.isAdjacentTo(partNumber)) return true
        }
        return false
    }

    fun getPartsSum(partNumbers: List<PartNumber>, symbols: List<Symbol>): Int {
        var sum = 0

        for (partNumber in partNumbers) {
            if (hasAdjacentSymbol(partNumber, symbols)) sum += partNumber.value
        }

        return sum
    }


    fun part1(input: List<String>): Int {
        var sum = 0

        val lines = mutableListOf<InputLine>()

        input.forEach { line -> lines.add(parseLine(line)) }

        for (i in 0..<lines.size) {
            sum += if (i == 0) getPartsSum(lines[i].partNumbers, lines[i].symbols + lines[1].symbols)
            else if (i + 1 == lines.size) getPartsSum(
                lines[i].partNumbers,
                lines[i - 1].symbols + lines[i].symbols
            )
            else getPartsSum(
                lines[i].partNumbers,
                lines[i - 1].symbols + lines[i].symbols + lines[i + 1].symbols
            )
        }
        return sum
    }

    fun getSumOfGearRatios(symbols: List<Symbol>, partNumbers: List<PartNumber>): Int {
        var sumOfGearRatios = 0

        for (symbol in symbols) {
            if (symbol.value != gear) continue
            val adjacentParts = partNumbers.filter { partNumber -> symbol.isAdjacentTo(partNumber) }
            if (adjacentParts.size == 2) sumOfGearRatios += adjacentParts.map { partNumber -> partNumber.value }
                .reduce(Int::times)

        }

        return sumOfGearRatios
    }

    fun part2(input: List<String>): Int {
        var sumOfGearRatios = 0

        val lines = mutableListOf<InputLine>()

        input.forEach { line -> lines.add(parseLine(line)) }

        for (i in 0..<lines.size) {
            sumOfGearRatios += if (i == 0) getSumOfGearRatios(
                lines[i].symbols,
                lines[i].partNumbers + lines[1].partNumbers
            )
            else if (i + 1 == lines.size) getSumOfGearRatios(
                lines[i].symbols,
                lines[i - 1].partNumbers + lines[i].partNumbers
            )
            else getSumOfGearRatios(
                lines[i].symbols,
                lines[i - 1].partNumbers + lines[i].partNumbers + lines[i + 1].partNumbers
            )
        }
        return sumOfGearRatios
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361) { part1(testInput).toString() }
    check(part2(testInput) == 467835) { part2(testInput).toString() }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
