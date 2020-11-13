package br.com.rsms.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import br.com.rsms.calculator.model.enums.NumbersEnum
import br.com.rsms.calculator.model.enums.OperationsEnum
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.*

class MainActivity : AppCompatActivity() {

    private var calculatorDisplay: TextView? = null
    private var calculatorHistory: TextView? = null
    private var calculatorExpression: TextView? = null
    private var evaluated: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_main)
        this.calculatorDisplay = findViewById(R.id.tvDisplay)
        this.calculatorHistory = findViewById(R.id.tvHistory)
        this.calculatorHistory!!.movementMethod = ScrollingMovementMethod()
        this.calculatorExpression = findViewById(R.id.tvExpression)
        initBackspaceClickPressListener();
        initClearLongClickListener();

        mXparser.setDegreesMode()
    }

    fun numberInput(view : View){
        this.clearDisplayAfterEvaluation()
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
        this.clearDisplayAfterEvaluation()
        val input: OperationsEnum = OperationsEnum.valueOf(view.tag.toString())
        val display: String = this.calculatorDisplay!!.text.toString()
        var operationValue: String = ""

        this.calculatorDisplay!!.text.toString()
        operationValue = input.operation

        this.addToExpression("$display $operationValue")
        this.calculatorDisplay!!.text = ""
    }

    private fun addToDisplay(str : String){
        this.calculatorDisplay!!.append(str)
    }

    private fun addToExpression(str : String){
        this.calculatorExpression!!.append(str)
    }

    fun clear(view : View) {
        this.calculatorDisplay!!.text = ""
    }

    fun backspace(view : View) {
        clearDisplayAfterEvaluation()
        var exp = this.calculatorDisplay!!.text
        if (exp.isNotEmpty()){
            exp = exp.subSequence(0, exp.lastIndex)
        }
        this.calculatorDisplay!!.text = exp
    }

    fun invertSign(view: View) {
        if(this.calculatorDisplay!!.text.isNotEmpty()) {
            if(this.calculatorDisplay!!.text.contains("-")) {
                this.calculatorDisplay!!.text = this.calculatorDisplay!!.text.toString().replace("-", "")
            } else {
                this.calculatorDisplay!!.text = "-" + this.calculatorDisplay!!.text.toString()
            }
        }
    }

    fun eval(view : View){
        if(this.evaluated) {
            this.clearDisplayAfterEvaluation()
        } else {
            // Get display value
            val displayExpression: String = this.calculatorDisplay!!.text.toString()
            // Append display value to expression
            this.calculatorExpression!!.append(displayExpression);
            // Sanitize expression (operators, enclosing parenthesis e etc...)
            this.calculatorExpression!!.text = this.sanitizeExpression(this.calculatorExpression!!.text.toString())
            // Eval expression
            val expression: Expression = Expression(this.calculatorExpression!!.text.toString())
            val result = expression.calculate()
            var resultStr: String = ""
            if(result.isNaN()){
                resultStr = "ERROR"
            } else {
                resultStr = "$result"
            }
            // Display result
            this.calculatorExpression!!.append(" = $resultStr")
            this.calculatorDisplay!!.text = "$resultStr"
            // Update calculator history
            this.evaluated = true;
            this.calculatorHistory!!.append(this.calculatorExpression!!.text.toString() + "\n")
            // Clean expression
            this.calculatorExpression!!.text = ""
        }
    }

    private fun clearDisplayAfterEvaluation(){
        if(this.evaluated) {
            this.calculatorDisplay!!.text = ""
            this.evaluated = false
        }
    }

    private fun sanitizeExpression(expression: String): String{
        var sanitizedExpression = expression

        val openingParenthesis = expression.count{"(".contains(it)}
        val closingParenthesis = expression.count{")".contains(it)}

        for (i in 1..openingParenthesis - closingParenthesis step 1) {
            sanitizedExpression = "$sanitizedExpression)"
        }

        return sanitizedExpression
    }

    private fun initBackspaceClickPressListener() {
        
    }

    private fun initClearLongClickListener() {

    }

}