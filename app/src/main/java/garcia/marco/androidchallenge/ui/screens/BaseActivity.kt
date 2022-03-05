package garcia.marco.androidchallenge.ui.screens

import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import garcia.marco.androidchallenge.R
import garcia.marco.androidchallenge.domain.exceptions.DataException
import garcia.marco.androidchallenge.ui.custom.ErrorManager

abstract class BaseActivity : AppCompatActivity() {

    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createView()
        collectFlows()
    }

    open fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    abstract fun createView()
    abstract fun collectFlows()

}