package com.example.villive.Management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.villive.R

class cost_frag : Fragment() {

    private lateinit var items: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cost_frag, container, false)

        items = resources.getStringArray(R.array.itemList)

        val monthCost = view.findViewById<TextView>(R.id.month_cost_tx)
        val waterCost = view.findViewById<TextView>(R.id.water_cost_tx)
        val powerCost = view.findViewById<TextView>(R.id.power_cost_tx)
        val mgCost = view.findViewById<TextView>(R.id.mg_cost_tx)

        val monthState = view.findViewById<TextView>(R.id.month_state_tx)
        val waterState = view.findViewById<TextView>(R.id.water_state_tx)
        val powerState = view.findViewById<TextView>(R.id.power_state_tx)
        val mgState = view.findViewById<TextView>(R.id.mg_state_tx)
        
        val monthDefaultSign =view.findViewById<View>(R.id.month_default)
        val waterDefaultSign =view.findViewById<View>(R.id.water_default)
        val powerDefaultSign =view.findViewById<View>(R.id.power_default)
        val mgDefaultSign =view.findViewById<View>(R.id.mg_default)


        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        val spinner = view.findViewById<Spinner>(R.id.cost_spinner)

        spinner.adapter = myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                monthDefaultSign.visibility=View.INVISIBLE
                waterDefaultSign.visibility=View.INVISIBLE
                powerDefaultSign.visibility=View.INVISIBLE
                mgDefaultSign.visibility=View.INVISIBLE

                when (position) {
                    0 -> {
                        monthCost.setText("350,000")
                        waterCost.setText("9,600")
                        powerCost.setText("26,590")
                        mgCost.setText("150,000")

                        monthState.setText("납부")
                        waterState.setText("미납")
                        powerState.setText("미납")
                        mgState.setText("납부")

                        waterDefaultSign.visibility=View.VISIBLE
                        powerDefaultSign.visibility=View.VISIBLE
                    }

                    1 -> {
                        monthCost.setText("350,000")
                        waterCost.setText("12,750")
                        powerCost.setText("32,050")
                        mgCost.setText("150,000")

                        monthState.setText("납부")
                        waterState.setText("납부")
                        powerState.setText("미납")
                        mgState.setText("납부")

                        powerDefaultSign.visibility=View.VISIBLE

                    }

                    2 -> {
                        monthCost.setText("350,000")
                        waterCost.setText("7,670")
                        powerCost.setText("12,897")
                        mgCost.setText("150,000")

                        monthState.setText("납부")
                        waterState.setText("납부")
                        powerState.setText("납부")
                        mgState.setText("납부")
                    }

                    3 -> {
                        monthCost.setText("350,000")
                        waterCost.setText("6,790")
                        powerCost.setText("48,090")
                        mgCost.setText("150,000")

                        monthState.setText("납부")
                        waterState.setText("납부")
                        powerState.setText("납부")
                        mgState.setText("납부")
                    }

                    4 -> {
                        monthCost.setText("350,000")
                        waterCost.setText("12,900")
                        powerCost.setText("32,080")
                        mgCost.setText("150,000")

                        monthState.setText("납부")
                        waterState.setText("납부")
                        powerState.setText("납부")
                        mgState.setText("납부")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return view
    }
}
