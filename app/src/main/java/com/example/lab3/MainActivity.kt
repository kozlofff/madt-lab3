package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    private lateinit var textViewDisplay: TextView
    private var currentInput = ""
    private var currentOperator = ""
    private var firstOperand = 0.0
    private var isCalculationCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDisplay = findViewById(R.id.textView)

        setupNumberButtons()
        setupOperatorButtons()
        setupSpecialButtons()
    }
    private fun setupNumberButtons() {
        val numberButtons = listOf<Button>(
            findViewById(R.id.button26), findViewById(R.id.button21),
            findViewById(R.id.button22), findViewById(R.id.button23),
            findViewById(R.id.button16), findViewById(R.id.button17),
            findViewById(R.id.button18), findViewById(R.id.button11),
            findViewById(R.id.button12), findViewById(R.id.button13)
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                appendToCurrentInput(button.text.toString())
                updateDisplay()
            }
        }
    }

    private fun setupOperatorButtons() {
        val operatorButtons = listOf<Button>(
            findViewById(R.id.button28), findViewById(R.id.button24),
            findViewById(R.id.button19), findViewById(R.id.button14)
        )

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                handleOperatorButtonClick(button.text.toString())
                updateDisplay()
            }
        }
    }

    private fun setupSpecialButtons() {
        val specialButtons = listOf<Button>(
            findViewById(R.id.button10), findViewById(R.id.button25),
            findViewById(R.id.button6), findViewById(R.id.button7),
            findViewById(R.id.button8), findViewById(R.id.button9)
        )

        specialButtons.forEach { button ->
            button.setOnClickListener {
                handleSpecialButtonClick(button.text.toString())
                updateDisplay()
            }
        }
    }

    private fun appendToCurrentInput(value: String) {
        if (isCalculationCompleted) {
            currentInput = value
            isCalculationCompleted = false
        } else {
            currentInput += value
        }
    }

    private fun handleOperatorButtonClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator.isNotEmpty()) {
                return
            } else {
                firstOperand = currentInput.toDouble()
                currentInput = ""
            }
            currentOperator = operator
        }
    }

    private fun handleSpecialButtonClick(value: String) {
        when (value) {
            "√" -> performSquareRoot()
            "=" -> performCalculation()
            "←" -> performBackspace()
            "CE" -> clearEntry()
            "C" -> clearAll()
            "±" -> toggleSign()
        }
        updateDisplay()
    }

    private fun performCalculation() {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            try {
                val secondOperand = currentInput.toDouble()
                when (currentOperator) {
                    "+" -> firstOperand += secondOperand
                    "-" -> firstOperand -= secondOperand
                    "*" -> firstOperand *= secondOperand
                    "/" -> {
                        if (secondOperand != 0.0) {
                            firstOperand /= secondOperand
                        } else {
                            handleCalculationError()
                            return
                        }
                    }
                }
                currentInput = ""
                currentOperator = ""
                isCalculationCompleted = true
            } catch (e: NumberFormatException) {
                handleCalculationError()
            }
        }
    }

    private fun handleCalculationError() {
        currentInput = "Error"
        isCalculationCompleted = true
    }

    private fun performSquareRoot() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble()
            if (number >= 0) {
                val result = sqrt(number)
                currentInput = result.toString()
            } else {
                currentInput = "Error"
            }
        }
    }

    private fun performBackspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
        }
    }

    private fun clearEntry() {
        currentInput = ""
    }

    private fun clearAll() {
        currentInput = ""
        currentOperator = ""
        firstOperand = 0.0
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
        }
    }

    private fun updateDisplay() {
        val displayText = when {
            currentInput.isNotEmpty() && currentOperator.isNotEmpty() -> "$currentOperator $currentInput"
            currentInput.isNotEmpty() -> currentInput
            else -> firstOperand.toString()
        }
        textViewDisplay.text = displayText
    }


}
