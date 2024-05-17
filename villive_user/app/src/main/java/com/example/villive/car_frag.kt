package com.example.villive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.villive.Community.Community

class car_frag : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_car_frag, container, false)

        val car_regi = view.findViewById<Button>(R.id.btn_go_registration)

        car_regi.setOnClickListener {
            val intent = Intent(activity, Car_Registration::class.java)
            startActivity(intent)
        }

        return view
    }
}