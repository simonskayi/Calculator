package com.kwekboss.calculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    lateinit var tvInput: TextView
    var lastIsNumeric = false
    var lastIsDot = false
    var lastIsSubtraction = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    // Displaying the content of bottom clicked
    fun buttonPressed(view: View) {
        tvInput.append((view as Button).text)
        lastIsNumeric = true
    }

    // Handling the clear calculation area
    fun clearTvInput(view: View) {
        tvInput.text = ""
        lastIsNumeric = false
        lastIsDot = false
        lastIsSubtraction = false
    }

    //Handling the decimal point usage
    fun decimalPointClicked(view: View) {
        if (lastIsNumeric && !lastIsDot) {
            tvInput.append((view as Button).text)
            lastIsNumeric = false
            lastIsDot = true
        }
    }

    // Handling the substration sign
    fun subtractionSignClicked(view: View) {
        if (!lastIsSubtraction && !isMathOperator(tvInput.text.toString()) || lastIsNumeric) {
            tvInput.append((view as Button).text)
            lastIsNumeric = false
            lastIsDot = false
            lastIsSubtraction = true

        }
    }

    fun mathOperator(view: View) {
        if (lastIsNumeric && !isMathOperator(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastIsNumeric = false
            lastIsDot = false
        }
    }

    fun isMathOperator(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("x") || value.contains("+")
                    || value.contains("-")
        }
    }

    // Handling the Real Calculation
    fun equalSign(view: View) {
        if (lastIsNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)

                }
                // Codes for Subtraction Operator
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput.text =
                        removeZeroAfterDot((firstValue.toDouble() - secondValue.toDouble()).toString())
                }
                // Codes for Addition Operator
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput.text =
                        removeZeroAfterDot((firstValue.toDouble() + secondValue.toDouble()).toString())
                }
                // Codes for Division Operator
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput.text =
                        removeZeroAfterDot((firstValue.toDouble() / secondValue.toDouble()).toString())
                }
                // Codes for Multiplication Operator
                else if (tvValue.contains("x")) {
                    val splitValue = tvValue.split("x")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput.text =
                        removeZeroAfterDot((firstValue.toDouble() * secondValue.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
/// Handling the zero of the calculation because of converting it to of Double type
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}