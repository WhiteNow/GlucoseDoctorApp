package pe.edu.upc.GlucoCheck.adapters

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import pe.edu.upc.GlucoCheck.R


class LeDeviceListAdapter: BaseAdapter() {

    private lateinit var mLeDevices: ArrayList<BluetoothDevice>
    private lateinit var mInflator: LayoutInflater

    fun addDevice(device: BluetoothDevice) {
        if(!mLeDevices.contains(device)) {
            mLeDevices.add(device)
        }
    }

    fun getDevice(position: Int): BluetoothDevice {
        return mLeDevices.get(position)
    }

    fun clear() {
        mLeDevices.clear()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var viewh = view
        val viewHolder: ViewHolder
        // General ListView optimization code.
        if (view == null) {
            viewh = mInflator.inflate(R.layout.item_device, null)
            viewHolder = ViewHolder()
//            viewHolder.deviceAddress = view!!.findViewById(R.id.device_address) as TextView
            viewHolder.deviceName = viewh.findViewById(R.id.device_name) as TextView
            viewh.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val device = mLeDevices[i]
        val deviceName = device.name
        if (deviceName != null && deviceName.length > 0)
            viewHolder.deviceName.text = deviceName
        else
            viewHolder.deviceName.setText(R.string.unknown_device)
        viewHolder.deviceAddress.setText(device.address)

        return viewh!!
    }

    override fun getItem(position: Int): Any {
        return mLeDevices.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mLeDevices.count()
    }

}

class ViewHolder {
        lateinit var deviceName: TextView
        lateinit var deviceAddress: TextView
}