package com.example.fizzbuzzkotlin

fun main() {
    fizzbuzz(200)
}

private fun fizzbuzz(x: Int) {
    val allResults = mutableListOf<String>()
    for (i in 1..x) {
        val result = mutableListOf<String>()
        if (i % 3 == 0) result.add("Fizz")
        if (i % 5 == 0) result.add("Buzz")
        if (i % 7 == 0) result.add("Bang")
        if (i % 11 == 0) {
            result.clear()
            result.add("Bong")
        }
        if (i % 13 == 0) {
            for ((index, string) in result.withIndex()) {
                if (hasLeadingB(string)) {
                    result.add(index, "Fezz")
                    break
                }
            }
        }
        if (result.isNullOrEmpty()){
            allResults.add(i.toString())
        } else {
            allResults.add(result.joinToString(""))
        }
    }
    val output = allResults.joinToString("\n")
    println(output)
}

private fun hasLeadingB(arg: String): Boolean {
    if (arg[0] == 'B') {
        return true
    }
    return false
}