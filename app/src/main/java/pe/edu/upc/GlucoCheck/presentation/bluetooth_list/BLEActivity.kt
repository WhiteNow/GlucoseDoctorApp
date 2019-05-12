package pe.edu.upc.GlucoCheck.presentation.bluetooth_list

import android.app.ListActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.adapters.LeDeviceListAdapter




private const val SCAN_PERIOD: Long = 10000

class BLEActivity: ListActivity() {

    private var mScanning: Boolean = false

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled

    private val REQUEST_ENABLE_BT = 1

    private val mLeDeviceListAdapter: LeDeviceListAdapter? = null

    private val handler: Handler = Handler()

    private val leScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        runOnUiThread {
            mLeDeviceListAdapter?.addDevice(device)
            mLeDeviceListAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)
        bluetoothAdapter?.takeIf { it.isDisabled }?.apply {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    private fun scanLeDevice(enable: Boolean) {
        when (enable) {
            true -> {
                handler.postDelayed({
                    mScanning = false
                    bluetoothAdapter!!.stopLeScan(leScanCallback)
                }, SCAN_PERIOD)
                mScanning = true
                bluetoothAdapter!!.startLeScan(leScanCallback)
                Log.i("SCAN", "escaneando")
            }
            else -> {
                mScanning = false
                bluetoothAdapter!!.stopLeScan(leScanCallback)
            }
        }
    }

}
