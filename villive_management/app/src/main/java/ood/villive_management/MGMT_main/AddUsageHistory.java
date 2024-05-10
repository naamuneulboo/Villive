package ood.villive_management.MGMT_main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ood.villive_management.R;

public class AddUsageHistory extends AppCompatActivity {

    private Spinner monthSpinner, itemSpinner;
    private EditText costEditText;
    private ListView itemsListView;
    private ArrayAdapter<String> expenseAdapter;
    private Button costAddButton;
    private TextView tv;
    private Button clr_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_usage_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        monthSpinner = findViewById(R.id.sp_month);
        itemSpinner = findViewById(R.id.sp_item);
        costEditText = findViewById(R.id.et_cost);
        itemsListView = findViewById(R.id.lv_items);
        costAddButton = findViewById(R.id.btn_add);
        tv = findViewById(R.id.tv_text);
        clr_all = findViewById(R.id.btn_clr_all);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_array, android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);


        expenseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        itemsListView.setAdapter(expenseAdapter);

        costAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 입력 가져오기
                String month = monthSpinner.getSelectedItem().toString();
                String item = itemSpinner.getSelectedItem().toString();
                String amount = costEditText.getText().toString();

                // 공백이 있는지 확인
                if (amount.isEmpty()) {
                    // 사용자에게 알림
                    Toast.makeText(AddUsageHistory.this, "금액을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return; // 추가 중단
                }

                // 리스트뷰에 추가
                expenseAdapter.add(month + " - " + item + ": ₩ " + amount);
                updateEmptyTextVisibility();
            }
        });


        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 삭제 다이얼로그 표시
                AlertDialog.Builder builder = new AlertDialog.Builder(AddUsageHistory.this);
                builder.setMessage("선택한 항목을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 항목 삭제
                                expenseAdapter.remove(expenseAdapter.getItem(position));
                                updateEmptyTextVisibility();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });


        clr_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모두 삭제 버튼을 클릭했을 때의 동작
                AlertDialog.Builder builder = new AlertDialog.Builder(AddUsageHistory.this);
                builder.setMessage("모든 항목을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 모든 항목 삭제
                                expenseAdapter.clear();
                                updateEmptyTextVisibility();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

    }
    // 리스트에 항목이 추가되거나 삭제될 때
    void updateEmptyTextVisibility() {
        if (expenseAdapter.getCount() == 0) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }
}