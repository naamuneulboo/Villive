package com.example.villive.Community

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Retrofit.RetrofitService
import okhttp3.ResponseBody
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.R
import ood.villive_management.Retrofit.ComplainResponseDtoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainAdapter(private val context: Context, private val complainList: MutableList<ComplainResponseDto>) :
    RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    interface OnUpdateStatusClickListener {
        fun onUpdateClick(complain: ComplainResponseDto, newStatus: ComplainResponseDto.Status)
    }

    // 클릭 리스너 변수 선언
    private var onUpdateStatusClickListener: OnUpdateStatusClickListener? = null

    fun setOnUpdateStatusClickListener(listener: OnUpdateStatusClickListener) {
        this.onUpdateStatusClickListener = listener
    }

    // 각 항목의 뷰를 나타냄
    class ComplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contents: TextView = itemView.findViewById(R.id.contents)
        val writer: TextView = itemView.findViewById(R.id.writer)
        val createdDate: TextView = itemView.findViewById(R.id.createdDate)
        val status: TextView = itemView.findViewById(R.id.status)
        val type: TextView = itemView.findViewById(R.id.type)

        var currentStatus: ComplainResponseDto.Status? = null
    }

    // 민원 추가를하면 ComplainList에 보여줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complain_response, parent, false)
        return ComplainViewHolder(itemView)
    }

    // 특정 민원을 처리할 때 사용
    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        val currentItem = complainList[position]
        holder.title.text = currentItem.title
        holder.contents.text = currentItem.contents
        holder.writer.text = currentItem.writer
        holder.createdDate.text = currentItem.createdDate
        //holder.status.text = currentItem.status?.name
        holder.type.text = currentItem.type?.name

        holder.currentStatus = currentItem.status
        holder.status.text = holder.currentStatus?.name
        holder.status.setTextColor(getStatusColor(holder.currentStatus!!))

        holder.status.setOnClickListener{
            val nextStatus = getNextStatus(holder.currentStatus)
            holder.currentStatus = nextStatus
            holder.status.text = nextStatus.name
            holder.status.setTextColor(getStatusColor(nextStatus))
            // 민원 상태 업데이트
            onUpdateStatusClickListener?.onUpdateClick(currentItem, nextStatus)
        }
    }

    private fun getNextStatus(currentStatus: ComplainResponseDto.Status?): ComplainResponseDto.Status {
        return when (currentStatus) {
            ComplainResponseDto.Status.접수 -> ComplainResponseDto.Status.처리중
            ComplainResponseDto.Status.처리중 -> ComplainResponseDto.Status.완료
            ComplainResponseDto.Status.완료 -> ComplainResponseDto.Status.접수
            else -> ComplainResponseDto.Status.접수
        }
    }

    private fun getStatusColor(status: ComplainResponseDto.Status): Int {
        return when (status) {
            ComplainResponseDto.Status.접수 -> Color.BLACK
            ComplainResponseDto.Status.처리중 -> Color.RED
            ComplainResponseDto.Status.완료 -> Color.BLUE

        }
    }
/*
    fun updateStatus(complain: String, newStatus: ComplainResponseDto.Status) {
        val position = complainList.indexOfFirst { it.id == complain.id }
        if (position != -1) {
            val currentItem = complainList[position]
            currentItem.status = newStatus
            notifyItemChanged(position)

            // 서버에 상태 업데이트 요청 보내기
            val retrofit = RetrofitService.getService(context)
            val complainResponseDtoAPI = retrofit.create(ComplainResponseDtoAPI::class.java)

            val call = complainResponseDtoAPI.updateStatus(complain.id, currentItem.copy(status = newStatus))
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (!response.isSuccessful) {
                        Log.e("ComplainAdapter", "Failed to update status: ${response.code()}")
                        return
                    }
                    Log.d("ComplainAdapter", "Status updated successfully")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ComplainAdapter", "Error updating status", t)
                }
            })
        } else {
            Log.e("ComplainAdapter", "Complain not found in the list")
        }
    }
*/



    // 민원의 총 개수
    override fun getItemCount() = complainList.size

}