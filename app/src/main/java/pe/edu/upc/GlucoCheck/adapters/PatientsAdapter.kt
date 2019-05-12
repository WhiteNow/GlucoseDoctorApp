package pe.edu.upc.GlucoCheck.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidnetworking.widget.ANImageView
import kotlinx.android.synthetic.main.patient_item_layout.view.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.User

class PatientsAdapter(): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {

    private var items: MutableList<User> = ArrayList()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView
        var profileImageView: ANImageView
        init {
            profileImageView = itemView.user_profile_iv
            nameTextView = itemView.name_lbl
        }

        fun bindTo(item: User) {
            nameTextView.text = "${item.nombre} ${item.apellido}"
            profileImageView.setDefaultImageResId(R.drawable.oldman)
        }

    }

    fun addItems(list: ArrayList<User>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsAdapter.ViewHolder  {
        return PatientsAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.patient_item_layout, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PatientsAdapter.ViewHolder, position: Int) {
        holder.bindTo(items[position])
    }
}