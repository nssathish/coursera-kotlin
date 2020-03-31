package mastermind

import java.util.*

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun String.remove(_charIndex: Int, replaceStr: String) = if (this.isNotEmpty()) {
    var newString = ""
    for (i in this.indices)
        if (i != _charIndex)
            newString += this[i]
        else
            newString += replaceStr

    newString
} else {
    this
}
fun evaluateGuess(secret: String, guess: String): Evaluation {
    var secretStr = secret
    var guessStr = guess
    var rightPos = 0
    var wrongPos = 0

    for (i in secretStr.indices) {
        if (secretStr[i] in guessStr) {
            if (secretStr[i] == guessStr[i]) {
                rightPos++
                secretStr = secretStr.remove(i, "*")
                guessStr = guessStr.remove(i, "|")
            } else {
                for (j in guessStr.indices) {
                    if ((secretStr[i] == guessStr[j] && i == j) || (secretStr[j] == guessStr[j])) {
                        rightPos++
                        if (secretStr[j] == guessStr[j]) {
                            secretStr = secretStr.remove(j, "*")
                            guessStr = guessStr.remove(j, "|")
                        } else {
                            secretStr = secretStr.remove(i, "*")
                            guessStr = guessStr.remove(j, "|")
                            break
                        }
                    } else if ((secretStr[i] == guessStr[j]) && (secretStr[i] != guessStr[i])) {
                        wrongPos++
                        guessStr = guessStr.remove(j, "|")
                        break
                    }
                }
            }
        }
    }
    return Evaluation(rightPosition = rightPos, wrongPosition = wrongPos)
}

fun main(args: Array<String>) {
    while(true) {
        println("Enter the input: ")
        val inputs = readLine()!!.toString().split(" ")
        val secret = inputs[0]
        val guess = inputs[1]
        val result : Evaluation = evaluateGuess(secret, guess)
        println("${result.rightPosition} ${result.wrongPosition}")
        print("Enter 'c' to continue or 'q' to quit: ")
        if (readLine()!!.toString() == "c")
            continue
        else
            break
    }
}