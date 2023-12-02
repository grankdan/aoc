fun main() {
    class GameSet constructor(val red: Int, val green: Int, val blue: Int)
    class Game constructor(val id: Int, val gameSets: List<GameSet>) {
        val valid: Boolean
            get() = !gameSets.any { it.red > 12 || it.green > 13 || it.blue > 14 }
        val minCubes: Int
            get() {
                var redMax = 0
                var greenMax = 0
                var blueMax = 0

                for (gameSet in gameSets) {
                    if (gameSet.red > redMax)
                        redMax = gameSet.red
                    if (gameSet.green > greenMax)
                        greenMax = gameSet.green
                    if (gameSet.blue > blueMax)
                        blueMax = gameSet.blue
                }
                return redMax * greenMax * blueMax
            }
    }


    fun parse(game: String): Game {
        val gameSetsSeparatorIndex = game.indexOf(':')
        val id = game.substring(5..<gameSetsSeparatorIndex).toInt()

        val gameSetsText = game.substring(gameSetsSeparatorIndex).split(';')

        val redRegex = Regex("(\\d+) red")
        val greenRegex = Regex("(\\d+) green")
        val blueRegex = Regex("(\\d+) blue")


        val gameSets = mutableListOf<GameSet>()
        for (gameSetText in gameSetsText) {
            val red = redRegex.find(gameSetText)?.groups?.get(1)?.value?.toInt() ?: 0
            val green = greenRegex.find(gameSetText)?.groups?.get(1)?.value?.toInt() ?: 0
            val blue = blueRegex.find(gameSetText)?.groups?.get(1)?.value?.toInt() ?: 0
            gameSets += GameSet(red, green, blue)
        }

        return Game(id, gameSets)
    }

    fun part1(input: List<String>): Int {
        val games = mutableListOf<Game>()
        for (line in input) {
            games += parse(line)
        }

        var sumOfIds = 0
        for (game in games) {
            if (game.valid)
                sumOfIds += game.id
        }
        return sumOfIds

    }

    fun part2(input: List<String>): Int {
        val games = mutableListOf<Game>()
        for (line in input) {
            games += parse(line)
        }
        var sumMax = 0
        for (game in games) {
            sumMax += game.minCubes
        }
        return sumMax
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
