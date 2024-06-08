package com.example.villive.Community

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.R

class ComplainAdapter(private val complainList: List<ComplainResponseDto>) :
    RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    interface OnUpdateStatusClickListener {
        fun onUpdateClick(complain: ComplainResponseDto, nextstatus: ComplainResponseDto.Status)
    }

    // 클릭 리스너 변수 선언
    private var onUpdateStatusClickListener: OnUpdateStatusClickListener? = null

    fun setOnUpdateStatusClickListener(listener: OnUpdateStatusClickListener) {
        this.onUpdateStatusClickListener = listener
    }

    class ComplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contents: TextView = itemView.findViewById(R.id.contents)
        val writer: TextView = itemView.findViewById(R.id.writer)
        val createdDate: TextView = itemView.findViewById(R.id.createdDate)
        val status: TextView = itemView.findViewById(R.id.status)
        val type: TextView = itemView.findViewById(R.id.type)

        var currentStatus: ComplainResponseDto.Status? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complain_response, parent, false)
        return ComplainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        val currentItem = complainList[position]
        holder.title.text = currentItem.title
        holder.contents.text = currentItem.contents
        holder.writer.text = currentItem.writer
        holder.createdDate.text = currentItem.createdDate
        holder.type.text = currentItem.type?.name
        holder.status.text = currentItem.status?.name

        holder.status.setOnClickListener {
            val nextStatus = getNextStatus(holder.currentStatus)
            holder.currentStatus = nextStatus
            holder.status.text = nextStatus.name
            holder.status.setTextColor(getStatusColor(nextStatus))

            onUpdateStatusClickListener?.onUpdateClick(currentItem, nextStatus)
        }

        /*
        val nextStatus = getNextStatus(holder.currentStatus)
        holder.currentStatus = nextStatus
        holder.status.text = nextStatus.name
        holder.status.setTextColor(getStatusColor(nextStatus))

        holder.status.setOnClickListener {
            val nextStatus = getNextStatus(holder.currentStatus)
            holder.currentStatus = nextStatus
            holder.status.text = nextStatus.name
            holder.status.setTextColor(getStatusColor(nextStatus))
            // 민원 상태 업데이트

            onUpdateStatusClickListener?.onUpdateClick(currentItem)
        }

 */
    }

    private fun getNextStatus(currentStatus: ComplainResponseDto.Status?): ComplainResponseDto.Status {
        return when (currentStatus) {
            ComplainResponseDto.Status.접수 -> ComplainResponseDto.Status.처리중
            ComplainResponseDto.Status.처리중 -> ComplainResponseDto.Status.완료
            ComplainResponseDto.Status.완료 -> ComplainResponseDto.Status.접수
            else -> ComplainResponseDto.Status.접수
        }
    }

    private fun getStatusColor(status: ComplainResponseDto.Status?): Int {
        return when (status) {
            ComplainResponseDto.Status.접수 -> Color.BLACK
            ComplainResponseDto.Status.처리중 -> Color.RED
            ComplainResponseDto.Status.완료 -> Color.BLUE
            else -> Color.BLACK // 기본 색상을 검정으로
        }
    }

    override fun getItemCount() = complainList.size
}