package pe.edu.upc.GlucoCheck.presentation.home_menu
import android.arch.lifecycle.ReportFragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.UserManager
import pe.edu.upc.GlucoCheck.presentation.PatientsActivity
import pe.edu.upc.GlucoCheck.presentation.appointmets.AppoinmentsActivity
import pe.edu.upc.GlucoCheck.presentation.reports.ReportsActivity

class HomeMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txtNombre.text = UserManager.user?.nombre

        treatment_image.setOnClickListener {
            val intent = Intent(this, PatientsActivity::class.java)
            startActivity(intent)
        }

        appointment_btn.setOnClickListener {
            val intent = Intent(this, AppoinmentsActivity::class.java)
            startActivity(intent)
        }

        infoBtn.setOnClickListener {

        }

        reportsBtn.setOnClickListener{
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun didTapTreatmentsOption() {

    }


}