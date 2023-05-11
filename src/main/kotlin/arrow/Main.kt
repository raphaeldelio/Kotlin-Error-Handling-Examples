package arrow

import arrow.core.flatMap
import arrow.core.raise.result
import java.io.File
import java.io.IOException

fun readContentFromFile(filename: String): Result<String> {
    return try {
        Result.success(
            File(filename).readText()
        )
    } catch (e: IOException) {
        Result.failure(e)
    }
}

fun transformContent(content: String): Result<CalculationInput> {
    val numbers = content.split(",").mapNotNull { it.toIntOrNull() }
    if (numbers.size != 2) {
        return Result.failure(Exception("Invalid input format"))
    }

    return Result.success(CalculationInput(numbers[0], numbers[1]))
}

data class CalculationInput(val a: Int, val b: Int)

    fun divide(a: Int, b: Int): Result<Int> {
        if (b == 0)
            return Result.failure(Exception("Division by zero"))

        return Result.success(a / b)
    }


/*fun main() {
    val result = readContentFromFile("input.txt").flatMap { content ->
        transformContent(content).flatMap { numbers ->
            divide(numbers.a, numbers.b)
        }
    }

    result.fold(
        onSuccess = {quotient ->
            println("The quotient of the division is $quotient")
        },
        onFailure = {
            println("Error: ${it.message}")
        }
    )
}*/

/*
fun main() {
    val result = result {
        val content = readContentFromFile("input.txt").bind()
        val numbers = transformContent(content).bind()
        divide(numbers.a, numbers.b).bind()
    }

    result.fold(
        onSuccess = {quotient ->
            println("The quotient of the division is $quotient")
        },
        onFailure = {
            println("Error: ${it.message}")
        }
    )
}
*/


fun main() {
    result {
        val content = readContentFromFile("input.txt").bind()
        val numbers = transformContent(content).bind()
        val quotient = divide(numbers.a, numbers.b).bind()
        println("The quotient of the division is $quotient")
    }.onFailure {
        println("Error: ${it.message}")
    }
}

/*
fun main() {
    val quotient = result {
        val content = readContentFromFile("input.txt").bind()
        val numbers = transformContent(content).bind()
        divide(numbers.a, numbers.b).bind()
    }.getOrThrow()

    println("The quotient of the division is $quotient")
}
*/
