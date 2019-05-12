package pe.edu.upc.GlucoCheck.presentation.my_info

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import pe.edu.upc.GlucoCheck.presentation.my_info.fragments.DoctorInfoFragment
import pe.edu.upc.GlucoCheck.presentation.my_info.fragments.PersonalInformationragment


class MyInfoAdapter(fm: FragmentManager, private var tabCount: Int): FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment? {
        when (p0) {
            0 -> return PersonalInformationragment()
            1 -> return DoctorInfoFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}