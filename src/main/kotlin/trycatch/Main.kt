package trycatch

import java.io.File
import java.io.IOException

fun readContentFromFile(filename: String): String {
    return try {
        File(filename).readText()
    } catch (e: IOException) {
        println("Error reading the file: ${e.message}")
        throw e
    }
}

fun transformContent(content: String): CalculationInput {
    val numbers = content.split(",").mapNotNull { it.toIntOrNull() }

    if (numbers.size != 2)
        throw Exception("Invalid input format")

    return CalculationInput(numbers[0], numbers[1])
}

data class CalculationInput(val a: Int, val b: Int)

class Calculator {
    fun divide(a: Int, b: Int): Int {
        if (b == 0)
            throw ArithmeticException("Division by zero is not allowed")

        return a / b
    }
}

fun main() {
    try {
        val content = readContentFromFile("input.txt")
        val numbers = transformContent(content)
        val quotient = Calculator().divide(numbers.a, numbers.b)
        println("The quotient of the division is $quotient")
    } catch (e: IOException) {
        println("Error reading the file: ${e.message}")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}