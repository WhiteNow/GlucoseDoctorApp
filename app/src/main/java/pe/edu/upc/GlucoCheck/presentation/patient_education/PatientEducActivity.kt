package pe.edu.upc.GlucoCheck.presentation.patient_education

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_treatment.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.presentation.treatments.TreatmentPageAdapter

class PatientEducActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_educ)

        configureTabLayout()
    }

    private fun configureTabLayout() {
        treatment_tab.addTab(treatment_tab.newTab().setText("Diabetes"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Medicamentos"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Preguntas frecuentes"))

        val adapter = PatientEducAdapter(supportFragmentManager, treatment_tab.tabCount)
        pager.adapter = adapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(treatment_tab))
        treatment_tab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 != null) {
                    pager.currentItem = p0.position
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })
    }

}
