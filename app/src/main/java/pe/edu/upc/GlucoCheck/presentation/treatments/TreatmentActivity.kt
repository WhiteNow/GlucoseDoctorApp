package pe.edu.upc.GlucoCheck.presentation.treatments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TableLayout
import pe.edu.upc.GlucoCheck.R

import kotlinx.android.synthetic.main.activity_treatment.*
import kotlinx.android.synthetic.main.fragment_feeding.*
import kotlinx.android.synthetic.main.fragment_medicine.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.data.UserManager

class TreatmentActivity : AppCompatActivity() {


    var listOfPills: ArrayList<String> = ArrayList()
    var listItems = arrayOf("Losartan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)


        setSupportActionBar(toolbar)
        getLastConsult()
        configureTabLayout()
    }

    private fun configureTabLayout() {
        treatment_tab.addTab(treatment_tab.newTab().setText("Medicamentos"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Alimentación"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Ejercicio"))

        val adapter = TreatmentPageAdapter(supportFragmentManager, treatment_tab.tabCount)
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

    fun getLastConsult() {
        var disposable = FirebaseConnection.getConsults().subscribe({
                subie ->
            UserManager.lastConsult = subie
            getLastFoods()
        },{
            Log.e("Bryam", "There is an errooor ! ${it.message}")
        })
    }

    fun getLastFoods() {
        var disposable = FirebaseConnection.getFood().subscribe({
                subie ->
            for (item in subie) {
                when(item.id) {
                    "Almuerzo" -> lunch_tv_id.text = item.descripcion
                    "Cena" -> dinner_tv_id.text = item.descripcion
                    "Desayuno" -> description_tv_id.text = item.descripcion
                }
            }
        },{
            Log.e("Bryam", "There is an errooor ! ${it.message}")
        })
    }

    fun getPills() {

    }

}
