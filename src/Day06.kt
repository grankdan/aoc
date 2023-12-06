
fun main() {

    class Race(val time: Long, val recordDistance: Long)


    fun parse(input: List<String>): List<Race> {

        val timeList = input.first()
            .split(':')
            .last()
            .split(' ')
            .map { number -> number.trim() }
            .filter { num -> num.isNotBlank() }
            .map { num -> num.toLong() }

        val recordDistanceList = input.last()
            .split(':')
            .last()
            .split(' ')
            .map { number -> number.trim() }
            .filter { num -> num.isNotBlank() }
            .map { num -> num.toLong() }

        val racesList = mutableListOf<Race>()

        for (i in timeList.indices) {
            racesList.add(Race(timeList[i], recordDistanceList[i]))
        }
        return racesList
    }

    fun parseAsOne(input: List<String>): Race {

        val time = input.first()
            .split(':')
            .last()
            .filter { char -> char != ' ' }
            .toLong()

        val recordDistance = input.last()
            .split(':')
            .last()
            .filter { char -> char != ' ' }
            .toLong()

        return Race(time, recordDistance)
    }

    fun countWaysToWin(race: Race): Long {
        val middle = race.time / 2

        for (i in 0..middle) {
            if (i * (race.time - i) > race.recordDistance) {
                return race.time - i * 2 + 1
            }
        }
        return -1
    }

    fun part1(input: List<String>): Long {
        val racesList = parse(input)

        var product = 1L

        for (race in racesList) {
            product *= countWaysToWin(race)

        }
        return product
    }

    fun part2(input: List<String>): Long {
        val race = parseAsOne(input)
        return countWaysToWin(race)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L) { part1(testInput).toString() }
    check(part2(testInput) == 71503L) { part2(testInput).toString() }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
