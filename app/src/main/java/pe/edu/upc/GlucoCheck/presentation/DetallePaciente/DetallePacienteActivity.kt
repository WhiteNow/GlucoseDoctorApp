package pe.edu.upc.GlucoCheck.presentation.DetallePaciente
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_detallepaciente.*
import pe.edu.upc.GlucoCheck.R

class DetallePacienteActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detallepaciente)
        configureTabLayout()
        val valueNombre = intent.getStringExtra("NombrePaciente")
        profile_image.setDefaultImageResId(R.drawable.oldman)
        name_head_lbl.text = valueNombre

    }

    private fun configureTabLayout() {
        treatment_tab.addTab(treatment_tab.newTab().setText("Informacion personal"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Historial Personal"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Anteriores Citas"))

        val adapter = DetallePacienteAdapter(supportFragmentManager, treatment_tab.tabCount)
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