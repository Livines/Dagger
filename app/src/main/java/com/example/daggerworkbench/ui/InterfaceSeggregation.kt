package com.example.daggerworkbench.ui
/*
interface OnClickListener {
    fun onClick()
    fun onLongClick()
}
class CustomUIComponent : OnClickListener {
    override fun onClick() {
        // handles onClick event.
    }

    // left empty as I don't want the [CustomUIComponent] to have long-click behavior.
    override fun onLongClick() {

    }
}
 */


interface OnClickListener {
    fun onClick()
}

interface OnLongClickListener {
    fun onLongClick()
}

class CustomUIComponent : OnClickListener {
    override fun onClick() {
        // handle single-click event
    }
}