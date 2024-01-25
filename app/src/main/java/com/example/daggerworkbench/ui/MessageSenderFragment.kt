package com.example.daggerworkbench.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daggerworkbench.R

class MessageSenderFragment : Fragment() {
    lateinit var model: SharedViewModel
    lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        view.findViewById<AppCompatButton>(R.id.button).setOnClickListener {
            val msg = view.findViewById<EditText>(R.id.ed1).text.toString()
            model.sendMessage(msg)
            communicator.sendMessage(msg)
        }
    }

    fun initializeListener(communicator: Communicator) {
        this.communicator = communicator
    }
}

interface Communicator {
    fun sendMessage(text: String)
}