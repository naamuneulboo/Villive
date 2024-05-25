package com.example.villive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.villive.ConfigurationPage.PasswordChangeActivity
import com.example.villive.ConfigurationPage.NicknameChangeActivity
import com.example.villive.ConfigurationPage.UnregisterActivity
import com.example.villive.User_SignPage.user_LogIn

class SettingFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting, container, false)

        val change_nickname = view.findViewById<Button>(R.id.ibtn_change_nickname)
        val change_pw = view.findViewById<Button>(R.id.ibtn_change_pw)
        val logout = view.findViewById<Button>(R.id.ibtn_logout)
        val withdraw = view.findViewById<Button>(R.id.ibtn_withdraw)

        change_nickname.setOnClickListener {
            val intent = Intent(activity, NicknameChangeActivity::class.java)
            startActivity(intent)
        }

        change_pw.setOnClickListener {
            val intent = Intent(activity, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(activity, user_LogIn::class.java)
            startActivity(intent)
            // 로그아웃 시 뒤로가기 버튼 눌러도 안되게 초기화 해야할듯 단순히 view만 교체하는게 아니라

        }

        withdraw.setOnClickListener {
            val intent = Intent(activity, UnregisterActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}