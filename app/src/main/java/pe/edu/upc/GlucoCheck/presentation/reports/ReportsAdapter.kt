package pe.edu.upc.GlucoCheck.presentation.reports

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import pe.edu.upc.GlucoCheck.presentation.reports.fragments.Report1Fragment
import pe.edu.upc.GlucoCheck.presentation.reports.fragments.Report2Fragment
import pe.edu.upc.GlucoCheck.presentation.reports.fragments.Report3Fragment
import pe.edu.upc.GlucoCheck.presentation.reports.fragments.Report4Fragment

public class ReportsAdapter(fm: FragmentManager, private var tabCount: Int): FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment? {
        when (p0) {
            0 -> return Report1Fragment()
            1 -> return Report2Fragment()
            2 -> return Report3Fragment()
            3 -> return Report4Fragment()

            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}