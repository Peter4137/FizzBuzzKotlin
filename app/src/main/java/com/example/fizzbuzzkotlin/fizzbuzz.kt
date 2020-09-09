package com.example.fizzbuzzkotlin

import java.lang.Exception
import java.util.Scanner

data class Rule(
    val word: String,
    val function: (currentOutput: MutableList<String>, ruleWord: String) -> MutableList<String>
)

fun main() {
    val reader = Scanner(System.`in`)
    val ruleDispatcher = mapOf<String, (MutableList<String>, String) -> MutableList<String>>(
        "append" to ::appendRule,
        "clear-and-replace" to ::clearAndReplaceRule,
        "add-before-first-B" to ::addBeforeFirstBRule,
        "reverse" to ::reverseRule,
        "flip-strings" to ::flipAllStringsRule
    )
    println("Welcome to FizzBuzz")
    println("Current rules available are:")
    for (rule in ruleDispatcher.keys) {
        println(rule)
    }
    try {
        print("Enter new rule to add: ")
        val newRule: String = readLine()!!
        print("Enter rule keyword: ")
        val newRuleKeyword: String = readLine()!!
        print("Enter new rule number: ")
        val newRuleNumber: Int = reader.nextInt()
        val userRules = mapOf<Int, Rule>(
            newRuleNumber to Rule(newRuleKeyword, ruleDispatcher[newRule] ?: error("Invalid choice of new rule"))
        )
        print("Enter limit: ")
        val upperLimit:Int = reader.nextInt()
        fizzbuzz(upperLimit, userRules)
    } catch (e: Exception) {
        println(e)
    }
}

private fun fizzbuzz(upperLimit: Int = 100, userRules: Map<Int, Rule>) {
    val rules = mutableMapOf<Int, Rule>(
        3 to Rule("Fizz", ::appendRule),
        5 to Rule("Buzz", ::appendRule),
        7 to Rule("Bang", ::appendRule),
        11 to Rule("Bong", ::clearAndReplaceRule),
        13 to Rule("Fezz", ::addBeforeFirstBRule),
        17 to Rule("", ::reverseRule),
        19 to Rule("", ::flipAllStringsRule)
    )
    rules.putAll(userRules)

    val allResults = mutableListOf<String>()
    for (i in 1..upperLimit) {
        var result = mutableListOf<String>()
        for ((ruleNumber, ruleDefinition) in rules.entries) {
            result = generalRuleCase(i, result, ruleNumber, ruleDefinition)
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
    rule: Rule
): MutableList<String> {
    var result = currentOutput
    if (countNumber % ruleNumber == 0) {
        result = rule.function(currentOutput, rule.word)
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

private fun flipAllStringsRule(currentOutput: MutableList<String>, ruleWord: String): MutableList<String> {
    val newOutput = mutableListOf<String>()
    for (string in currentOutput) {
        newOutput.add(string.reversed())
    }
    return newOutput
}

private fun generateOutputLine(arr: MutableList<String>, countNumber: Int): String {
    var outputLine = arr.joinToString("")
    if (outputLine.isBlank()) {
        outputLine = countNumber.toString()
    }
    return outputLine
}

