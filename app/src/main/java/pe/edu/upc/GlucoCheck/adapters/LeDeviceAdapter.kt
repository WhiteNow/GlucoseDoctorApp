package pe.edu.upc.GlucoCheck.adapters

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_device.view.*

class LeDeviceAdapter(private var items: List<BluetoothDevice>) :
        RecyclerView.Adapter<LeDeviceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deviceName: TextView
        init {
            deviceName = itemView.device_name
        }
        fun bindTo(device: BluetoothDevice) {
            deviceName.text = device.name
        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LeDeviceAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: LeDeviceAdapter.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}