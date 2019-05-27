package pe.edu.upc.GlucoCheck.presentation.DetallePaciente

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import pe.edu.upc.GlucoCheck.presentation.DetallePaciente.fragments.HistoriaFragment
import pe.edu.upc.GlucoCheck.presentation.DetallePaciente.fragments.PersonalInformationragment
import pe.edu.upc.GlucoCheck.presentation.DetallePaciente.fragments.AnterioresCitasFragment

class DetallePacienteAdapter(fm: FragmentManager, private var tabCount: Int): FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        when (p0) {
            0 -> return PersonalInformationragment()
            1 -> return HistoriaFragment()
            2 -> return AnterioresCitasFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}