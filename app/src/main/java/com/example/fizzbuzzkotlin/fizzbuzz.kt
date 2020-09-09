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
    val ruleWords = mapOf(
        3 to "Fizz",
        5 to "Buzz",
        7 to "Bang",
        11 to "Bong",
        13 to "Fezz",
        17 to ""
    )
    val ruleFunctions = mapOf(
        3 to ::appendRule,
        5 to  ::appendRule,
        7 to ::appendRule,
        11 to ::clearAndReplaceRule,
        13 to ::addBeforeFirstBRule,
        17 to ::reverseRule
    )

    val allResults = mutableListOf<String>()
    for (i in 1..upperLimit) {
        var result = mutableListOf<String>()
        for ((key, value) in ruleWords.entries) {
            result = generalRuleCase(i, result, key, value, ruleFunctions.get(key))
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
    ruleFunction: (currentOutput: MutableList<String>, ruleWord: String) -> MutableList<String> ): MutableList<String> {
    var result = mutableListOf<String>()
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