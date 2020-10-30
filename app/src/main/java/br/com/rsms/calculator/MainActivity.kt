package br.com.rsms.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mXparser

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberInput(view : View){
        var viewCopy : MaterialButton = view as MaterialButton
        this.addToDisplay(viewCopy.text.toString())
    }

    fun addToDisplay(str : String){
        var display : EditText = findViewById(R.id.inpExpression)
        display.setText(display.text.toString() + str)
    }

    fun clear(view : View) {
        this.getDisplay().setText("")
    }

    fun eval(view : View){
        val displayExpression: String = this.getDisplay().text.toString()
        val expression: Expression = Expression(displayExpression)
        this.getDisplay().setText(displayExpression + " = " + expression.calculate())
    }

    private fun getDisplay() : EditText {
        val display : EditText = findViewById(R.id.inpExpression)
        return display
    }
}