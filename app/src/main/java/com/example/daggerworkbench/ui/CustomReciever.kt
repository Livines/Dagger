package com.example.daggerworkbench.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CustomReciever {
    val broadcastManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {

            }
        }

    }
}