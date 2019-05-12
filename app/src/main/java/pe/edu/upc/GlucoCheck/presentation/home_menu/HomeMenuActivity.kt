package pe.edu.upc.GlucoCheck.presentation.home_menu
import android.arch.lifecycle.ReportFragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.UserManager
import pe.edu.upc.GlucoCheck.presentation.appointmets.AppoinmentsActivity
import pe.edu.upc.GlucoCheck.presentation.bluetooth_list.BLEActivity
import pe.edu.upc.GlucoCheck.presentation.bluetooth_list.BluetoothActivity
import pe.edu.upc.GlucoCheck.presentation.glucose_measure.GlucoseMeasureActivity
import pe.edu.upc.GlucoCheck.presentation.my_info.MyInfoActivity
import pe.edu.upc.GlucoCheck.presentation.patient_education.PatientEducActivity
import pe.edu.upc.GlucoCheck.presentation.treatments.TreatmentActivity

class HomeMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txtNombre.text = UserManager.user?.nombre

        treatment_image.setOnClickListener {
            didTapTreatmentsOption()
        }

        glucoseBtn.setOnClickListener {
            Log.d("Bryam","presionando")
            val intent = Intent(this, GlucoseMeasureActivity::class.java)
            startActivity(intent)
        }

        appointment_btn.setOnClickListener {
            val intent = Intent(this, AppoinmentsActivity::class.java)
            startActivity(intent)
        }

        infoBtn.setOnClickListener {
            val intent = Intent(this, MyInfoActivity::class.java)
            startActivity(intent)
        }

        educBtn.setOnClickListener {
            val intent = Intent(this, PatientEducActivity::class.java)
            startActivity(intent)
        }
    }

    private fun didTapTreatmentsOption() {
        val intent = Intent(this, TreatmentActivity::class.java)
        startActivity(intent)
    }


}