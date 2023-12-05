fun main() {
    class AlmanacMapEntry(val firstRangeStart: Long, val secondRangeStart: Long, val rangeLength: Long) {
        fun getMappedValue(input: Long): Long {
            return input - firstRangeStart + secondRangeStart
        }
    }

    class AlmanacMap(val entries: List<AlmanacMapEntry>) {

        val rangeToMapping: Map<LongRange, AlmanacMapEntry>
            get() {
                val mapBuilder = mutableMapOf<LongRange, AlmanacMapEntry>()
                for (entry in entries) {
                    mapBuilder[entry.firstRangeStart..<entry.firstRangeStart + entry.rangeLength] = entry
                }
                return mapBuilder.toMap()
            }

        fun getMappedValue(input: Long): Long {
            for (entry in rangeToMapping.entries) {
                if (entry.key.contains(input)) {
                    return entry.value.getMappedValue(input)
                }
            }
            return input
        }
    }

    class Input(
        val seeds: List<Long>,
        val maps: List<AlmanacMap>
    )


    fun parse(input: List<String>): Input {
        val seeds = input[0].split(':')[1]
            .split(' ')
            .map { entry -> entry.trim() }
            .filter { num -> num.isNotBlank() }
            .map { num -> num.toLong() }

        val lists = mutableListOf<List<AlmanacMapEntry>>()

        var currentList = mutableListOf<AlmanacMapEntry>()
        lists.add(currentList)

        for (i in 3..<input.size) {
            if (input[i].contains(':')) {
                currentList = mutableListOf()
                lists.add(currentList)
                continue
            } else if (input[i].isEmpty()) continue

            val mapping = input[i]
                .split(' ')
                .map { entry -> entry.trim() }
                .filter { num -> num.isNotBlank() }
                .map { num -> num.toLong() }
            currentList.add(AlmanacMapEntry(mapping[1], mapping[0], mapping[2]))
        }

        return Input(
            seeds,
            lists.map { list -> AlmanacMap(list) }
        )
    }

    fun part1(input: List<String>): Long {
        val inputStructure = parse(input)

        var lowestLocationNumber = Long.MAX_VALUE

        for (seed in inputStructure.seeds) {
            var currentVal = seed
            for (map in inputStructure.maps) {
                currentVal = map.getMappedValue(currentVal)
            }

            //Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
            //Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
            //Seed 55, soil 57, fertilizer 57, water 53, light 46, temperature 82, humidity 82, location 86.
            //Seed 13, soil 13, fertilizer 52, water 41, light 34, temperature 34, humidity 35, location 35.

            if (currentVal < lowestLocationNumber) {
                lowestLocationNumber = currentVal
            }
        }
        return lowestLocationNumber
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L) { part1(testInput).toString() }
    //check(part2(testInput) == 30) { part2(testInput).toString() }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
