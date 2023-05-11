package bubbleup

import java.io.File

fun readContentFromFile(filename: String): String {
    return File(filename).readText()
}

fun transformContent(content: String): CalculationInput {
    val numbers = content.split(",").mapNotNull { it.toIntOrNull() }
    if (numbers.size != 2)
        throw Exception("Invalid input format")


    return CalculationInput(numbers[0], numbers[1])
}

data class CalculationInput(val a: Int, val b: Int)

class Calculator {
    fun divide(a: Int, b: Int) = a / b
}

fun main() {
    val content = readContentFromFile("input.txt")
    val numbers = transformContent(content)
    val quotient = Calculator().divide(numbers.a, numbers.b)

    println("The quotient of the division is $quotient")
}


















/*fun main() {
    startFramework {
        startBusinessLogic()
    }
}*/

fun startFramework(action: () -> Unit) {
    runCatching {
        action()
    }.onFailure {
        println("Something went wrong. Contact the administrator")
        throw it
    }
}

fun startBusinessLogic() {
    val content = readContentFromFile("input.txt")
    val numbers = transformContent(content)
    val quotient = Calculator().divide(numbers.a, numbers.b)
    println("The quotient of the division is $quotient")
}
