package com.example.villive

import com.example.villive.Management.ManagementFragment
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.footbar)

        var bnv_main = findViewById<BottomNavigationView>(R.id.bnv_main)

        bnv_main.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, homeFragment).commit()
                    }

                    R.id.navigation_chatting -> {
                        val chattingFragment = ChattingFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, chattingFragment).commit()
                    }

                    R.id.navigation_management -> {
                        val managementFragment = ManagementFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, managementFragment).commit()
                    }

                    R.id.navigation_Setting -> {
                        val settingFragment = SettingFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, settingFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.navigation_home
        }
    }
}
