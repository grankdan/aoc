import kotlin.math.pow

fun main() {

    class Card (val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>) {

        val matchingNumbers: Int
            get() {
                var matchingNumbers = -1
                for (number in numbers) {
                    if (winningNumbers.contains(number)) matchingNumbers += 1
                }
                return matchingNumbers
            }
        val points: Int
            get() {
                return if (matchingNumbers > -1) 2.toDouble().pow(matchingNumbers).toInt()
                else 0
            }


        var scratchCards = -1

        fun scratchCards(allCards: List<Card>): Int {
            if (scratchCards != -1) return scratchCards

            var scratchCardsNumberBuilder = 1
            for (i in id..id + matchingNumbers) {
                scratchCardsNumberBuilder += allCards[i].scratchCards(allCards)
            }

            scratchCards = scratchCardsNumberBuilder
            return scratchCardsNumberBuilder

        }
    }

    fun parseNumbers(input: String): List<Int> {
        return input.trim().split(' ').map { num -> num.trim() }.filter { num -> num.isNotBlank() }
            .map { num -> num.toInt() }
    }

    fun parse(card: String): Card {
        val cardGameSeparatorIndex = card.indexOf(':')
        val id = card.substring(5..<cardGameSeparatorIndex).trim().toInt()

        val cardNumbersSeparatorIndex = card.indexOf('|')

        val winningNumbers = parseNumbers(card.substring(cardGameSeparatorIndex + 1..<cardNumbersSeparatorIndex))
        val numbers = parseNumbers(card.substring(cardNumbersSeparatorIndex + 1))
        return Card(id, winningNumbers, numbers)
    }

    fun part1(input: List<String>): Int {
        val cards = mutableListOf<Card>()
        for (line in input) {
            cards += parse(line)
        }

        var sum = 0
        for (card in cards) {
            sum += card.points
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val cards = mutableListOf<Card>()
        for (line in input) {
            cards += parse(line)
        }

        var sum = 0
        for (card in cards) {
            sum += card.scratchCards(cards)
        }
        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13) { part1(testInput).toString() }
    check(part2(testInput) == 30) { part2(testInput).toString() }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
