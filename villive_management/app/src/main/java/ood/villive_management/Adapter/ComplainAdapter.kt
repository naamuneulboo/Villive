package com.example.villive.Community

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ood.villive_management.Model.ComplainResponseDto
import ood.villive_management.R

class ComplainAdapter(private val complainList: List<ComplainResponseDto>) :
    RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>() {

    interface OnUpdateStatusClickListener {
        fun onUpdateClick(complain: ComplainResponseDto, nextstatus: ComplainResponseDto.Status)
    }

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

        holder.itemView.setOnClickListener {
            val statusList = arrayOf("접수", "처리중", "완료")

            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("상태 변경")
                .setItems(statusList) { _, which ->
                    val selectedStatus = statusList[which]
                    val nextStatus = when (selectedStatus) {
                        "접수" -> ComplainResponseDto.Status.접수
                        "처리중" -> ComplainResponseDto.Status.처리중
                        "완료" -> ComplainResponseDto.Status.완료
                        else -> ComplainResponseDto.Status.접수
                    }
                    holder.currentStatus = nextStatus
                    holder.status.text = selectedStatus
                    holder.status.setTextColor(getStatusColor(nextStatus))

                    onUpdateStatusClickListener?.onUpdateClick(currentItem, nextStatus)
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }
    }

    private fun getStatusColor(status: ComplainResponseDto.Status?): Int {
        return when (status) {
            ComplainResponseDto.Status.접수 -> Color.BLACK
            ComplainResponseDto.Status.처리중 -> Color.RED
            ComplainResponseDto.Status.완료 -> Color.BLUE
            else -> Color.BLACK
        }
    }

    override fun getItemCount() = complainList.size
}