package com.example.villive.Alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.villive.R

/**
 * A simple [Fragment] subclass.
 * Use the [Board_Alarm.newInstance] factory method to
 * create an instance of this fragment.
 */
class Board_Alarm : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board_alarm, container, false)
    }
}