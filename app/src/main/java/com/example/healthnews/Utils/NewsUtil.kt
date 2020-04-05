package com.example.healthnews.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.healthnews.R

object NewsUtil {
    var activeNetwork: NetworkInfo? = null
    const val API_KEY = "7e4199b9c96f43fda7a6569f41552690"
    fun showAlertDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(context.getString(R.string.alert))
        alertDialog.setMessage(context.getString(R.string.alertmessage))
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val cm =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetwork = cm.activeNetworkInfo
        return if (activeNetwork != null && activeNetwork!!.isConnectedOrConnecting) true else false
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}