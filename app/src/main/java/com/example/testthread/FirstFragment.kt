package com.example.testthread

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), CoroutineScope {

    var start = 0L
    var end = 0L
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start = System.currentTimeMillis()
        Log.i("MAIN-IO-MAIN", "start " + start)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    Log.i("MAIN-IO-MAIN", "get  " + Thread.currentThread())
                    //simulate network with sleep
                    Thread.sleep(5000)
                    "aaa"
                }
                Log.i("MAIN-IO-MAIN", "set ${Thread.currentThread()} + $result")
                end = System.currentTimeMillis()
                Log.i("MAIN-IO-MAIN", "end $end")
                Log.i("MAIN-IO-MAIN", "duration " + ((end - start) / 1000) + "seconds")
            } catch (e: Exception) {

            }
        }
        // simulate prepare view with sleep
        Thread.sleep(4000)
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
}