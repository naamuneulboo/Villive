package ood.villive_management.MGMT_main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ood.villive_management.R;

public class AddNotice extends AppCompatActivity {

    private Button addnotiButton;
    private EditText noticeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_notice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noticeInput = findViewById(R.id.et_notice_input);
        addnotiButton = findViewById(R.id.btn_add_noti);
        addnotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = noticeInput.getText().toString();
                // 공백이 있는지 확인
                if (str.isEmpty()) {
                    // 사용자에게 알림
                    Toast.makeText(AddNotice.this, "게시글 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return; // 추가 중단
                }
                // 다이얼로그를 표시
                showSuccessDialog();

                /*
                String userInput = editText.getText().toString();
                // DB에 입력한 내용 등록
                boolean isRegistered = registerToDatabase(userInput);
                // 등록 결과에 따라 다이얼로그 표시
                if (isRegistered) {
                    showSuccessDialog();
                } else {
                    showFailureDialog();
                }

                 */
            }
        });



    }

/*
 // DB에 등록하는 메서드
    private boolean registerToDatabase(String userInput) {
        // DB에 등록하는 로직
        // 성공하면 true
        // 실패하면 false
        return true; // 또는 false
    }
*/

    // 등록 성공
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("게시글이 성공적으로 등록되었습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // 등록 실패
    private void showFailureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("게시글 등록에 실패했습니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}