package pe.edu.upc.GlucoCheck.presentation.bluetooth_list

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.adapters.LeDeviceListAdapter
import java.util.*
import kotlin.collections.ArrayList


class ScanBLEActivity : AppCompatActivity() {

    val GLUCOSE_RATE_SERVICE_UUID = convertFromInteger(0x1808)
    val GLUCOSE_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A18)
    val GLUCOSE_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A52)

    var GLUCOSE_DEVICES_UUID = arrayOf<UUID>()

    val REQUEST_ENABLE_BT = 1

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled
    private val bleScanner: BluetoothLeScanner? = null
    private val bleGatt: BluetoothGatt? = null

    private var mScanning: Boolean = false

    private lateinit var handler: Handler

    lateinit var leDeviceListAdapter: LeDeviceListAdapter

    private val leScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        runOnUiThread {
            leDeviceListAdapter.addDevice(device)
            Log.i("SCAN",device.name)
            Log.i("SCAN",device.address)
            Log.i("SCAN","////////////////////////////")
            leDeviceListAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_ble)
        bluetoothAdapter?.takeIf { it.isDisabled }?.apply {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        GLUCOSE_DEVICES_UUID += GLUCOSE_RATE_SERVICE_UUID

    }

    private fun convertFromInteger(i: Int): UUID {
        val MSB = 0x0000000000001000L
        val LSB = -0x7fffff7fa064cb05L
        val value = (i and -0x1).toLong()
        return UUID(MSB or (value shl 32), LSB)
    }

    private fun scanLeDevice(enable: Boolean) {
        when(enable) {
            true -> {
                handler.postDelayed({
                    mScanning = false
                    bluetoothAdapter!!.stopLeScan(leScanCallback)
                }, 10000)
                mScanning = true
                bluetoothAdapter!!.startLeScan(GLUCOSE_DEVICES_UUID, leScanCallback)
            }

        }
    }



}
