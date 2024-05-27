package ood.villive_management.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ood.villive_management.Model.NoticeResponseDto
import ood.villive_management.R

class NoticeAdapter(private val noticeList: MutableList<NoticeResponseDto>) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    // 인터페이스 정의
    interface OnDeleteClickListener {
        fun onDeleteClick(post: NoticeResponseDto)
    }

    // 클릭 리스너 변수 선언
    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        this.onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notice_response, parent, false)
        return NoticeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val currentItem = noticeList[position]
        holder.title.text = currentItem.title
        holder.contents.text = currentItem.contents
        holder.writer.text = currentItem.writer
        holder.createDate.text = currentItem.createDate

        holder.itemView.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(currentItem) // 클릭 시 인터페이스 콜백 호출
        }
    }

    override fun getItemCount() = noticeList.size

    // 뷰홀더 클래스
    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contents: TextView = itemView.findViewById(R.id.contents)
        val writer: TextView = itemView.findViewById(R.id.writer)
        val createDate: TextView = itemView.findViewById(R.id.createDate)
    }

    fun addNotice(notice: NoticeResponseDto) {
        noticeList.add(notice)
        notifyItemInserted(noticeList.size - 1)
    }

}