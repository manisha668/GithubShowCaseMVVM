package com.example.githubshowcaseapp.utilities

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun Context.showToast(msg : String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}