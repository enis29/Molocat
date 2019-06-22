package com.enkapp.molocat.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enkapp.molocat.R
import com.enkapp.molocat.constant.Constants

fun <T> AppCompatActivity.customStartActivity(context: Context, clazz: Class<T>, bundle : Bundle) {
    val intent = Intent(context, clazz)
    intent.putExtra(Constants.TAG_BUNDLE, bundle)
    startActivity(intent)
    overridePendingTransition(R.anim.enter, R.anim.exit);
}