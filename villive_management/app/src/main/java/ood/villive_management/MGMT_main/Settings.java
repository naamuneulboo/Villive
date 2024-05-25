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

import ood.villive_management.MGMT_Settings.MGMT_change_pw;
import ood.villive_management.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton change_pw = findViewById(R.id.ibtn_change_pw);
        ImageButton add_vill = findViewById(R.id.ibtn_add_vill);
        ImageButton change_vill = findViewById(R.id.ibtn_change_vill);
        ImageButton logout = findViewById(R.id.ibtn_logout);
        ImageButton rm_member = findViewById(R.id.ibtn_rm_member);
        Button pipp = findViewById(R.id.btn_PIPP);

        change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MGMT_change_pw.class); // 다음 화면으로 넘어가기 위한 intent 객체 생성
                startActivity(intent);
            }
        });

    }
}