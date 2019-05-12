package pe.edu.upc.GlucoCheck.presentation.my_info

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_my_info.*
import pe.edu.upc.GlucoCheck.R

class MyInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)
        configureTabLayout()
        profile_image.setDefaultImageResId(R.drawable.oldman)
    }

    private fun configureTabLayout() {
        treatment_tab.addTab(treatment_tab.newTab().setText("Informacion personal"))
        treatment_tab.addTab(treatment_tab.newTab().setText("Mi Doctor"))

        val adapter = MyInfoAdapter(supportFragmentManager, treatment_tab.tabCount)
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
