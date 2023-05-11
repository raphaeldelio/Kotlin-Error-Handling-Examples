package result

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
    readContentFromFile("input.txt").fold(
        onSuccess = {content ->
            transformContent(content).fold(
                onSuccess = {numbers ->
                    divide(numbers.a, numbers.b).fold(
                        onSuccess = {quotient ->
                            println("The quotient of the division is $quotient")
                        },
                        onFailure = {
                            println("Error: ${it.message}")
                        }
                    )
                },
                onFailure = {
                    println("Error: ${it.message}")
                }
            )
        },
        onFailure = {
            println("Error reading the file: ${it.message}")
        }
    )
}*/

/*fun main() {
    readContentFromFile("input.txt").map { content ->
        transformContent(content).map { numbers ->
            divide(numbers.a, numbers.b)
        }
    }.fold(
        onSuccess = { content ->
            content.fold(
                onSuccess = { numbers ->
                    numbers.fold(
                        onSuccess = { quotient ->
                            println("The quotient of the division is $quotient")
                        },
                        onFailure = {
                            println("Error: ${it.message}")
                        }
                    )
                },
                onFailure = {
                    println("Error: ${it.message}")
                }
            )
        },
        onFailure = {
            println("Error: ${it.message}")
        }
    )
}*/

fun main() {
    readContentFromFile("input.txt").onSuccess { content ->
        transformContent(content).onSuccess {
            divide(it.a, it.b).onSuccess { quotient ->
                println("The quotient of the division is $quotient")
            }
        }
    }.onFailure {
        println("Error: ${it.message}")
    }
}
