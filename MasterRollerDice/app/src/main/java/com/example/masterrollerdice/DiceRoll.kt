package com.example.masterrollerdice

data class DiceRoll(
    val date: String,
    val d4Count: Int = 0,
    val d6Count: Int = 0,
    val d8Count: Int = 0,
    val d10Count: Int = 0,
    val d12Count: Int = 0,
    val d20Count: Int = 0,
    val d100Count: Int = 0,
    val results: List<Int>,
    val total: Int
) {
    fun getDiceDescription(): String {
        val parts = mutableListOf<String>()
        if (d4Count > 0) parts.add("${d4Count}d4")
        if (d6Count > 0) parts.add("${d6Count}d6")
        if (d8Count > 0) parts.add("${d8Count}d8")
        if (d10Count > 0) parts.add("${d10Count}d10")
        if (d12Count > 0) parts.add("${d12Count}d12")
        if (d20Count > 0) parts.add("${d20Count}d20")
        if (d100Count > 0) parts.add("${d100Count}d100")
        return parts.joinToString(" + ")
    }

    fun getFormula(): String {
        return "${results.joinToString(" + ")} = $total"
    }
}