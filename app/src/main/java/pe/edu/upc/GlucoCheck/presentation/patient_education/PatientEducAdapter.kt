package pe.edu.upc.GlucoCheck.presentation.patient_education

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import pe.edu.upc.GlucoCheck.presentation.patient_education.fragments.DiabetesFragment
import pe.edu.upc.GlucoCheck.presentation.patient_education.fragments.PillsFragment
import pe.edu.upc.GlucoCheck.presentation.patient_education.fragments.QuestionFragment
import pe.edu.upc.GlucoCheck.presentation.treatments.fragments.ExcercisesFragment
import pe.edu.upc.GlucoCheck.presentation.treatments.fragments.FeedingFragment
import pe.edu.upc.GlucoCheck.presentation.treatments.fragments.MedicineFragment

public class PatientEducAdapter(fm: FragmentManager, private var tabCount: Int): FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment? {
        when (p0) {
            0 -> return DiabetesFragment()
            1 -> return PillsFragment()
            2 -> return QuestionFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}