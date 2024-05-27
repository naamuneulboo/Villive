import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.villive.Calendar.C_event
import com.example.villive.Calendar.EventAdapter
import com.example.villive.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private val allEvents = mutableListOf<C_event>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.calendar, container, false)

        calendarView = rootView.findViewById(R.id.calendarView)
        recyclerView = rootView.findViewById(R.id.cl_recycler)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        allEvents.add(C_event("2024-05-25", "정기 가스 점검"))
        allEvents.add(C_event("2024-05-25", "일반 쓰레기 배출일"))
        allEvents.add(C_event("2024-05-26", "건물 대청소"))
        allEvents.add(C_event("2024-05-29", "관리인 휴무"))
        allEvents.add(C_event("2024-05-31", "수도 점검/단수"))
        allEvents.add(C_event("2024-06-01", "일반 쓰레기 배출일"))
        allEvents.add(C_event("2024-06-03", "음식물 쓰레기 배출일"))
        allEvents.add(C_event("2024-06-06", "현충일"))
        allEvents.add(C_event("2024-06-06", "관리인 휴무"))
        allEvents.add(C_event("2024-06-8", "엘리베이터 점검"))

        eventAdapter = EventAdapter(emptyList())
        recyclerView.adapter = eventAdapter

        val currentDate = getCurrentDate()
        updateEventsForDate(currentDate)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
            updateEventsForDate(selectedDate)
        }

        return rootView
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun updateEventsForDate(date: String) {
        val eventsForDate = allEvents.filter { it.date == date }
        eventAdapter.updateEvents(eventsForDate)
    }
}