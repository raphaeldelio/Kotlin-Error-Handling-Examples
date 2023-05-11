package arrowalternative

import arrow.core.Either
import arrow.core.raise.either
import java.io.File

fun readContentFromFile(filename: String): Either<String, String> {
    val file = File(filename)
    if (!file.exists())
        return Either.Left("File does not exist")

    return Either.Right(file.readText())
}

fun transformContent(content: String): Either<String, CalculationInput> {
    val numbers = content.split(",").mapNotNull { it.toIntOrNull() }
    if (numbers.size != 2) {
        return Either.Left("More or less than two numbers in the file")
    }

    return Either.Right(CalculationInput(numbers[0], numbers[1]))
}

data class CalculationInput(val a: Int, val b: Int)

class Calculator {
    fun divide(a: Int, b: Int): Either<String, Int> {
        if (b == 0)
            return Either.Left("Division by zero")

        return Either.Right(a / b)
    }
}

fun main() {
    val result = either {
        val content = readContentFromFile("input.txt").bind()
        val numbers = transformContent(content).bind()
        Calculator().divide(numbers.a, numbers.b).bind()
    }

    result.fold(
        ifLeft = { error ->
            println("Error: $error")
        },
        ifRight = { quotient ->
            println("The quotient of the division is $quotient")
        }
    )
}