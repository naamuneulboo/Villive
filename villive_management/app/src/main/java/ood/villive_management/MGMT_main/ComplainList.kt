package ood.villive_management.MGMT_main

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Community.ComplainAdapter
import com.example.villive.Retrofit.RetrofitService
import okhttp3.ResponseBody
import ood.villive_management.Adapter.NoticeAdapter
import ood.villive_management.Model.ComplainRequestDto
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.Model.NoticeResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.ComplainRequestDtoAPI
import ood.villive_management.Retrofit.ComplainResponseDtoAPI
import ood.villive_management.getStatusFromString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainList : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var complainAdapter: ComplainAdapter
    private lateinit var complainResponseDtoAPI: ComplainResponseDtoAPI

    //private lateinit var complainId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_complain)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //complainId = intent.getStringExtra("COMPLAIN_ID").toString()

        // Retrofit 객체 가져오기
        val retrofit = RetrofitService.getService(this)
        complainResponseDtoAPI = retrofit.create(ComplainResponseDtoAPI::class.java)

        val call = complainResponseDtoAPI.getAllComplainResponseDto()
        call.enqueue(object : Callback<List<ComplainResponseDto>> {
            override fun onResponse(call: Call<List<ComplainResponseDto>>, response: Response<List<ComplainResponseDto>>) {
                if (!response.isSuccessful) {
                    // Handle error
                    //Toast.makeText(this@ComplainList, "업데이트 실패11", Toast.LENGTH_SHORT) .show()

                    Log.e("ComplainList", "Error: ${response.errorBody()?.string()}")
                    return
                }

                val complainResponseDtos = response.body() ?: return
                complainAdapter = ComplainAdapter(complainResponseDtos)
                recyclerView.adapter = complainAdapter

                complainAdapter.setOnUpdateStatusClickListener(object : ComplainAdapter.OnUpdateStatusClickListener {
                    override fun onUpdateClick(complain: ComplainResponseDto, nextstatus: ComplainResponseDto.Status) {
                        update(complain.id.toString(), nextstatus.toString())
                        //Toast.makeText(this@ComplainList, "업데이트 성공?!?!?!", Toast.LENGTH_SHORT) .show()
                    }
                })
            }

            override fun onFailure(call: Call<List<ComplainResponseDto>>, t: Throwable) {
                // Handle failure
                Log.e("ComplainList", "Request Failed11", t)
                //Toast.makeText(this@ComplainList, "네트워크 오류? 업데이트 실패22", Toast.LENGTH_SHORT) .show()
            }
        })
    }

    private fun update(complainId: String, status: String) {

        val titleView = findViewById<TextView>(R.id.title)
        val contentsView = findViewById<TextView>(R.id.contents)
        val typeView = findViewById<TextView>(R.id.type)
        //val statusView = findViewById<TextView>(R.id.status)

        val title = titleView.text.toString().trim()
        val contents = contentsView.text.toString().trim()
        val type = typeView.text.toString().trim()
        // status = statusView.text.toString().trim()

        val nextStatus = ComplainResponseDto.Status.valueOf(status)
        val currentStatus = getStatusFromString(nextStatus.toString())
        val ComplainPostDto = ComplainRequestDto(contents, currentStatus.toString(), title, type)

        val retrofit = RetrofitService.getService(this)
        val complainRequestDtoAPI = retrofit.create(ComplainRequestDtoAPI::class.java)

        complainRequestDtoAPI.updateComplain2(complainId.toLong(), ComplainPostDto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //Toast.makeText(this@ComplainList, "업데이트 성공", Toast.LENGTH_SHORT) .show()
                    Log.e("ComplainList", "Error22: ${response.errorBody()?.string()}")

                } else {
                    //Toast.makeText(this@ComplainList, "업데이트 실패33..", Toast.LENGTH_SHORT) .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 네트워크 오류 등의 실패 처리
                Log.e("ComplainList", "Request Failed22", t)
                //Toast.makeText(this@ComplainList, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


/*
      statusTextView.setOnClickListener {
                    val nextStatus2 = statusTextView.text.toString()
                    val ComplainPostDto = ComplainRequestDto(status = nextStatus2)

                    val complainRequestDtoAPI = retrofit.create(ComplainRequestDtoAPI::class.java)
                    complainRequestDtoAPI.updateComplain2(complainId.toLong(), ComplainPostDto)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                if (response.isSuccessful) {
                                    //Toast.makeText(this@ComplainList, "업데이트 성공", Toast.LENGTH_SHORT) .show()
                                    Log.e("ComplainList", "Error22: ${response.errorBody()?.string()}")
                                } else {

                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                // 네트워크 오류 등의 실패 처리
                               // Toast.makeText(this@ComplainList, "업데이트 실패", Toast.LENGTH_SHORT).show()
                                Log.e("ComplainList", "Error333: ${response.errorBody()?.string()}")
                            }
                        })


                }
                */


    /*override fun onUpdateClick(complain: ComplainResponseDto, newStatus: ComplainResponseDto.Status) {
        //val updatedComplain = complain.copy(status = newStatus)
        updateComplainStatus(complain.id ?: return, newStatus)
    }

    override fun onUpdateClick(complain: ComplainResponseDto, newStatusString: String) {
        val newStatus = getStatusFromString(newStatusString)
        newStatus?.let {
            val updatedComplain = complain.copy(status = it)
            updateComplainStatus(complain.id ?: return, updatedComplain)
        }
    }

    // 해당 민원의 id를
    private fun updateComplainStatus(id: Long, status: ComplainResponseDto) {
        Log.d("ComplainList", "Updating complain status: $status")

        val call = status.status?.let { complainResponseDtoAPI.updateComplain(id, it) }
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) {
                    // Handle error
                    Log.e("ComplainList", "Server Error: ${response.errorBody()?.string()}")
                    return
                }
                Log.i("ComplainList", "Update Successful: ${response.body()?.string()}")
                // Update UI or handle success
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
                Log.e("ComplainList", "Request Failed22", t)
            }
        })
    }

     */

