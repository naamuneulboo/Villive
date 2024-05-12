package com.example.villive.ConfigurationPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.example.villive.R

class AlarmActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)
        initializeComponents()
        val tb = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(tb) // 액티비티의 앱바로 지정
        val actionBar = supportActionBar // 앱바 제어를 위한 툴바 액세스
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바에 뒤로가기 버튼 만들기
        actionBar.setDisplayShowTitleEnabled(false) // 기존 title 숨김
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면 액티비티의 onOptionsItemSelected() 메서드가 호출
    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeComponents() {
        val TextAllAlarm = findViewById<TextView>(R.id.text_allA)
        val TextManagerAlarm = findViewById<TextView>(R.id.text_managerA)
        val TextChattingAlarm = findViewById<TextView>(R.id.text_chattingA)
        val TextNoticeAlarm = findViewById<TextView>(R.id.text_noticeA)
        val TextComplainAlarm = findViewById<TextView>(R.id.text_complainA)
        val TextArticleAlarm = findViewById<TextView>(R.id.text_articleA)
        val buttonAll = findViewById<SwitchCompat>(R.id.switchPushBtn)
        val buttonManager = findViewById<SwitchCompat>(R.id.switchManagerBtn)
        val buttonChatting = findViewById<SwitchCompat>(R.id.switchChattingBtn)
        val buttonNotice = findViewById<SwitchCompat>(R.id.switchNoticeBtn)
        val buttonComplain = findViewById<SwitchCompat>(R.id.switchComplainBtn)
        val buttonArticle = findViewById<SwitchCompat>(R.id.switchArticleBtn)

        /* 게시판 알림이 선택되면 alarm_article.xml 화면이 보여져야함
        buttonArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        */
    }
}
