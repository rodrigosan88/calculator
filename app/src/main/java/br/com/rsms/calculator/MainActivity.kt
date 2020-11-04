package br.com.rsms.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import br.com.rsms.calculator.model.enums.NumbersEnum
import br.com.rsms.calculator.model.enums.OperationsEnum
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private var calculatorDisplay: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_main)
        this.calculatorDisplay = findViewById(R.id.tvDisplay)
    }

    fun numberInput(view : View){
        val input: NumbersEnum = NumbersEnum.valueOf(view.tag.toString())
        var value: String = ""
        when(input){
            NumbersEnum.DOT -> {
                if(!this.calculatorDisplay!!.text.toString().contains(".")){
                    value = NumbersEnum.DOT.number
                }
            }
            else -> {
                value = input.number
            }
        }

        if(this.calculatorDisplay!!.text.toString() == "0"){
            this.calculatorDisplay!!.text = value
        } else {
            this.addToDisplay(value)
        }

    }

    fun operationInput(view : View){
        val input: OperationsEnum = OperationsEnum.valueOf(view.tag.toString())
        var operationValue: String = ""

        operationValue = input.operation

        this.addToDisplay(operationValue)
    }

    private fun addToDisplay(str : String){
        this.calculatorDisplay!!.append(str)
    }

    fun clear(view : View) {
        this.calculatorDisplay!!.text = ""
    }

    fun backspace(view : View) {
        var exp = this.calculatorDisplay!!.text
        exp = exp.subSequence(0, exp.lastIndex)
        this.calculatorDisplay!!.text = exp
    }

    fun eval(view : View){
        val displayExpression: String = this.calculatorDisplay!!.text.toString()
        val expression: Expression = Expression(displayExpression)
        this.calculatorDisplay!!.append(" = ${expression.calculate()}")
    }

}