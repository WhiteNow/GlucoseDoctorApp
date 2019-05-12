package pe.edu.upc.GlucoCheck.adapters

import android.os.Build
import android.os.LocaleList
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.appoint_item_layout.view.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.AppointmentItem
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AppointmentAdapter():
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    private var items: MutableList<AppointmentItem> = ArrayList()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var dateTextView: TextView
        var hourTextView: TextView
        var description: TextView

        init {
            dateTextView = itemView.date_label
            hourTextView = itemView.hour_label
            description = itemView.description_label
        }

        fun bindTo(item: AppointmentItem) {
            dateTextView.text = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(item.fecha.toDate())
            hourTextView.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(item.fecha.toDate())
            description.text = item.descripcion
        }
    }

    fun addItems(list: ArrayList<AppointmentItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.appoint_item_layout, parent, false ))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AppointmentAdapter.ViewHolder, position: Int) {
        holder.bindTo(items[position])
    }
}