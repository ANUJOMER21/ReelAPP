package com.Vginfotech.reelapp

import android.content.Context
import android.widget.Toast

class Misc(private  val context: Context) {
    fun showToast(message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}