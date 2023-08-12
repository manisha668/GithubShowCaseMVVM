package com.example.githubshowcaseapp

import android.view.View

fun View.showLoader() {
    this.visibility = View.VISIBLE
}

fun View.hideLoader() {
    this.visibility = View.GONE
}