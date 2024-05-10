package ood.villive_management.MGMT_main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ood.villive_management.R;

public class Tanent_mgmt extends AppCompatActivity {
    private Button depositButton; // 변수명 수정
    private TextView depositTextView; // 다이얼로그에서 값을 설정할 TextView 추가

    private Button monthlypayButton;
    private TextView monthlypayTextView;

    private Button mgmtcostButton;
    private TextView mgmtcostTextView;

    private Button carButton;
    private TextView carTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tanent_mgmt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        depositTextView = findViewById(R.id.tv_deposit);
        depositButton = findViewById(R.id.btn_deposit);
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그를 표시
                showDepositDialog();
            }
        });


        monthlypayTextView = findViewById(R.id.tv_monthly_pay);
        monthlypayButton = findViewById(R.id.btn_monthly_pay);
        monthlypayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그를 표시
                showMonthlyDialog();
            }
        });


        mgmtcostTextView = findViewById(R.id.tv_mgmt_cost);
        mgmtcostButton = findViewById(R.id.btn_mgmt_cost);
        mgmtcostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그를 표시
                showMgmtCostDialog();
            }
        });


        carTextView = findViewById(R.id.tv_car);
        carButton = findViewById(R.id.btn_car);
        carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그를 표시
                showCarDialog();
            }
        });

    }


    // 보증금 조정 다이얼로그
    private void showDepositDialog() {
        // 다이얼로그 창
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("보증금 수정");

        // 다이얼로그에 EditText 추가
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);

        // 다이얼로그에 확인 버튼 추가
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // EditText에서 입력된 값 가져와서 TextView에 설정
                String newValue = editText.getText().toString();
                depositTextView.setText(newValue); // depositTextView에 설정

                //설정 하고 DB에 값 update 시키는 코드

            }
        });

        // 다이얼로그 표시
        builder.show();
    }



    //월세 조정 다이얼로그
    private void showMonthlyDialog() {
        // 다이얼로그 창
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("월세 수정");

        // 다이얼로그에 EditText 추가
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);

        // 다이얼로그에 확인 버튼 추가
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // EditText에서 입력된 값 가져와서 TextView에 설정
                String newValue = editText.getText().toString();
                monthlypayTextView.setText(newValue); // depositTextView에 설정

                //설정 하고 DB에 값 update 시키는 코드

            }
        });

        // 다이얼로그 표시
        builder.show();
    }


    // 관리비 조정
    private void showMgmtCostDialog() {
        // 다이얼로그 창
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("관리비 수정");

        // 다이얼로그에 EditText 추가
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);

        // 다이얼로그에 확인 버튼 추가
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // EditText에서 입력된 값 가져와서 TextView에 설정
                String newValue = editText.getText().toString();
                mgmtcostTextView.setText(newValue); // depositTextView에 설정

                //설정 하고 DB에 값 update 시키는 코드

            }
        });

        // 다이얼로그 표시
        builder.show();
    }


    // 차량
    private void showCarDialog() {
        // 다이얼로그 창
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("차량 번호 수정");

        // 다이얼로그에 EditText 추가
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);

        // 다이얼로그에 확인 버튼 추가
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // EditText에서 입력된 값 가져와서 TextView에 설정
                String newValue = editText.getText().toString();
                carTextView.setText(newValue); // depositTextView에 설정

                //설정 하고 DB에 값 update 시키는 코드

            }
        });

        // 다이얼로그 표시
        builder.show();
    }

}
