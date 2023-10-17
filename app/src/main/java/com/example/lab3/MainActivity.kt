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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDisplay = findViewById(R.id.textView)

        val button0 : Button = findViewById(R.id.button26)
        val button1 : Button = findViewById(R.id.button21)
        val button2 : Button = findViewById(R.id.button22)
        val button3 : Button = findViewById(R.id.button23)
        val button4 : Button = findViewById(R.id.button16)
        val button5 : Button = findViewById(R.id.button17)
        val button6 : Button = findViewById(R.id.button18)
        val button7 : Button = findViewById(R.id.button11)
        val button8 : Button = findViewById(R.id.button12)
        val button9 : Button = findViewById(R.id.button13)

        setNumberButtonClickListeners(button0)
        setNumberButtonClickListeners(button1)
        setNumberButtonClickListeners(button2)
        setNumberButtonClickListeners(button3)
        setNumberButtonClickListeners(button4)
        setNumberButtonClickListeners(button5)
        setNumberButtonClickListeners(button6)
        setNumberButtonClickListeners(button7)
        setNumberButtonClickListeners(button8)
        setNumberButtonClickListeners(button9)

        val buttonEquals = findViewById<Button>(R.id.button25)
        buttonEquals.setOnClickListener {
            performCalculation()
        }

        val buttonPlus : Button = findViewById(R.id.button28)
        val buttonMinus : Button = findViewById(R.id.button24)
        val buttonMultiply : Button = findViewById(R.id.button19)
        val buttonDivision : Button = findViewById(R.id.button14)

        setOperatorClickListeners(buttonPlus, "+")
        setOperatorClickListeners(buttonMinus, "-")
        setOperatorClickListeners(buttonMultiply, "*")
        setOperatorClickListeners(buttonDivision, "/")

        val buttonSquareRoot = findViewById<Button>(R.id.button10)
        buttonSquareRoot.setOnClickListener {
            performSquareRoot()
        }
    }

    private fun setNumberButtonClickListeners(button: Button) {
        button.setOnClickListener {
            val buttonText = button.text.toString()
            currentInput += buttonText
            updateDisplay(currentInput)
        }
    }

    private fun setOperatorClickListeners(button: Button, operator: String) {
        button.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                if (currentOperator.isNotEmpty()) {
                    performCalculation()
                } else {
                    firstOperand = currentInput.toDouble()
                    currentInput = ""
                }
                currentOperator = operator
            }
        }
    }

    private fun performCalculation() {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            when (currentOperator) {
                "+" -> firstOperand += secondOperand
                "-" -> firstOperand -= secondOperand
                "*" -> firstOperand *= secondOperand
                "/" -> {
                    if (secondOperand != 0.0) {
                        firstOperand /= secondOperand
                    } else {
                        updateDisplay("Unknown error")
                        return
                    }
                }
            }
            updateDisplay(firstOperand.toString())
            currentInput = ""
            currentOperator = ""
        }
    }

    private fun performSquareRoot() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble()
            if (number >= 0) {
                val result = sqrt(number)
                updateDisplay(result.toString())
                currentInput = result.toString()
            } else {
                updateDisplay("Unknown error")
            }
        }
    }

    private fun updateDisplay(text: String) {
        textViewDisplay.text = text
    }
}