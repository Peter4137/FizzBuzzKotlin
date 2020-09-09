package com.example.fizzbuzzkotlin

import java.lang.Exception
import java.util.Scanner


fun main() {
    try {
        val reader = Scanner(System.`in`)
        print("Enter limit: ")
        val upperLimit:Int = reader.nextInt()
        fizzbuzz(upperLimit)
    } catch (e: Exception) {
        println("Invalid input")
    }

}

private fun fizzbuzz(upperLimit: Int = 100) {
    data class Rule(
        val word: String,
        val function: (currentOutput: MutableList<String>, ruleWord: String) -> MutableList<String>
    )
    val rules = mapOf<Int, Rule>(
        3 to Rule("Fizz", ::appendRule),
        5 to Rule("Buzz", ::appendRule),
        7 to Rule("Bang", ::appendRule),
        11 to Rule("Bong", ::clearAndReplaceRule),
        13 to Rule("Fezz", ::addBeforeFirstBRule),
        17 to Rule("", ::reverseRule)
    )

    val allResults = mutableListOf<String>()
    for (i in 1..upperLimit) {
        var result = mutableListOf<String>()
        for ((ruleNumber, ruleDefinition) in rules.entries) {
            result = generalRuleCase(i, result, ruleNumber, ruleDefinition.word, ruleDefinition.function)
        }
        allResults.add(generateOutputLine(result, i))
    }
    val output = allResults.joinToString("\n")
    println(output)
}

private fun generalRuleCase(
    countNumber: Int,
    currentOutput: MutableList<String>,
    ruleNumber: Int,
    ruleWord: String,
    ruleFunction: (currentOutput: MutableList<String>, ruleWord: String) -> MutableList<String>
): MutableList<String> {
    var result = currentOutput
    if (countNumber % ruleNumber == 0) {
        result = ruleFunction(currentOutput, ruleWord)
    }
    return result
}

private fun appendRule(currentOutput: MutableList<String>, ruleWord: String): MutableList<String> {
    currentOutput.add(ruleWord)
    return currentOutput
}

private fun reverseRule(currentOutput: MutableList<String>, ruleWord: String): MutableList<String> {
    currentOutput.reverse()
    return currentOutput
}

private fun clearAndReplaceRule(currentOutput: MutableList<String>, ruleWord: String): MutableList<String> {
    currentOutput.clear()
    currentOutput.add(ruleWord)
    return currentOutput
}

private fun addBeforeFirstBRule(currentOutput: MutableList<String>, ruleWord: String): MutableList<String> {
    for ((index, string) in currentOutput.withIndex()) {
        if (string.first() == 'B') {
            currentOutput.add(index, "Fezz")
            break
        }
    }
    return currentOutput
}

private fun generateOutputLine(arr: MutableList<String>, countNumber: Int): String {
    var outputLine = arr.joinToString("")
    if (outputLine.isBlank()) {
        outputLine = countNumber.toString()
    }
    return outputLine
}