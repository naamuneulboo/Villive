package com.example.villive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
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
            // AlertDialog를 생성하여 로그아웃 여부를 묻는다
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("네") { dialog, _ ->
                    // "네"를 선택하면 로그아웃 처리를 한다
                    performLogout()
                    dialog.dismiss()
                }
                .setNegativeButton("아니오") { dialog, _ ->
                    // "아니오"를 선택하면 대화상자를 닫는다
                    dialog.dismiss()
                }
                .show()
        }


        withdraw.setOnClickListener {
            val intent = Intent(activity, UnregisterActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun performLogout() {
        val intent = Intent(activity, user_LogIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}