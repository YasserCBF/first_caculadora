package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    private lateinit var display: EditText
    private var currentNumber = ""
    private var firstNumber = ""
    private var operation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.etInput)
        updateDisplay("0")
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                if (isNewOperation) {
                    currentNumber = ""
                    isNewOperation = false
                }
                currentNumber += buttonText
                updateDisplay(currentNumber)
            }

            "." -> {
                if (!currentNumber.contains(".")) {
                    currentNumber += if (currentNumber.isEmpty()) "0." else "."
                    updateDisplay(currentNumber)
                }
            }

            "±" -> {
                if (currentNumber.isNotEmpty()) {
                    currentNumber = if (currentNumber[0] == '-') {
                        currentNumber.substring(1)
                    } else {
                        "-$currentNumber"
                    }
                    updateDisplay(currentNumber)
                }
            }

            "AC" -> clearAll()

            "%" -> {
                if (currentNumber.isNotEmpty()) {
                    val percent = currentNumber.toDouble() / 100
                    currentNumber = percent.toString()
                    updateDisplay(currentNumber)
                }
            }

            "+", "-", "×", "÷" -> {
                if (currentNumber.isNotEmpty()) {
                    firstNumber = currentNumber
                    operation = buttonText
                    currentNumber = ""
                    updateDisplay("0")
                }
            }

            "=" -> {
                if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
                    val result = calculate(
                        firstNumber.toDouble(),
                        currentNumber.toDouble(),
                        operation
                    )
                    currentNumber = result.toString()
                    updateDisplay(currentNumber)
                    isNewOperation = true
                    firstNumber = ""
                    operation = ""
                }
            }
        }
    }

    private fun calculate(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            "÷" -> a / b
            else -> 0.0
        }
    }

    private fun updateDisplay(text: String) {
        val displayText = if (text.endsWith(".0")) {
            text.substring(0, text.length - 2)
        } else {
            text
        }
        display.setText(displayText)
    }

    private fun clearAll() {
        currentNumber = ""
        firstNumber = ""
        operation = ""
        updateDisplay("0")
        isNewOperation = true
    }
}
