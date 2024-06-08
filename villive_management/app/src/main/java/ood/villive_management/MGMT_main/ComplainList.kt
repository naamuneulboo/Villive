package ood.villive_management.MGMT_main


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community.ComplainAdapter
import okhttp3.ResponseBody
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.ComplainResponseDtoAPI
import ood.villive_management.Retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainList : AppCompatActivity(), ComplainAdapter.OnUpdateStatusClickListener {

    private lateinit var complainAdapter: ComplainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_complain)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        complainAdapter = ComplainAdapter(emptyList())
        recyclerView.adapter = complainAdapter

        val retrofit = RetrofitService.getService(this)

        val service = retrofit.create(ComplainResponseDtoAPI::class.java)

        service.getAllComplainResponseDto().enqueue(object : Callback<List<ComplainResponseDto>> {
            override fun onResponse(call: Call<List<ComplainResponseDto>>, response: Response<List<ComplainResponseDto>>) {
                if (response.isSuccessful) {
                    val complainList = response.body()
                    complainList?.let {
                        complainAdapter = ComplainAdapter(it)
                        complainAdapter.setOnUpdateStatusClickListener(this@ComplainList)
                        recyclerView.adapter = complainAdapter
                    }
                } else {
                    Toast.makeText(this@ComplainList, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ComplainResponseDto>>, t: Throwable) {
                Toast.makeText(this@ComplainList, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onUpdateClick(complain: ComplainResponseDto, nextStatus: ComplainResponseDto.Status) {

        val retrofit = RetrofitService.getService(this)

        val service = retrofit.create(ComplainResponseDtoAPI::class.java)

        service.updateComplain(complain.id ?: 0, nextStatus.name, complain).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // 업데이트 성공 !!
                    Toast.makeText(this@ComplainList, "민원 상태 변경이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 업데이트 실패
                    Toast.makeText(this@ComplainList, "민원 상태 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 오류
                Toast.makeText(this@ComplainList, "에러: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
