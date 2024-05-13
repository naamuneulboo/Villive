package com.example.villive

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.villive.ConfigurationPage.AlarmActivity
import com.example.villive.ConfigurationPage.PasswordChangeActivity
import com.example.villive.ConfigurationPage.ProfileSetupActivity
import com.example.villive.User_SignPage.user_LogIn
import com.example.villive.User_SignPage.user_SignUp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting, container, false)

        val set_profile = view.findViewById<ImageButton>(R.id.ibtn_set_profile)
        val change_pw = view.findViewById<ImageButton>(R.id.ibtn_change_pw)
        val set_notice = view.findViewById<ImageButton>(R.id.ibtn_set_noti)
        val logout = view.findViewById<ImageButton>(R.id.ibtn_logout)
        val withdraw = view.findViewById<ImageButton>(R.id.ibtn_withdraw)

        set_profile.setOnClickListener {
            val intent = Intent(activity, ProfileSetupActivity::class.java)
            startActivity(intent)
        }

        change_pw.setOnClickListener {
            val intent = Intent(activity, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

        set_notice.setOnClickListener {
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(activity, user_LogIn::class.java)
            startActivity(intent)
        }

        withdraw.setOnClickListener {
            val intent = Intent(activity, user_SignUp::class.java)
            startActivity(intent)
        }

        return view
    }


}