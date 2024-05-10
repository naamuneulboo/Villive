package ood.villive_management.MGMT_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ood.villive_management.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);  //xml 화면 뷰 연결
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button tanent = findViewById(R.id.btn_tanent); // btn_tanent 버튼을 가져옴
        Button notice = findViewById(R.id.btn_notice);
        Button complain = findViewById(R.id.btn_complain);
        Button cost = findViewById(R.id.btn_cost);
        Button settings = findViewById(R.id.btn_settings);
        ImageButton chat = findViewById(R.id.ibtn_chat);

        // 버튼에 클릭 리스너 설정
        tanent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tanent_mgmt.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotice_history.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUsageHistory.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SolveComplain.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Chatting.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });
    }
}