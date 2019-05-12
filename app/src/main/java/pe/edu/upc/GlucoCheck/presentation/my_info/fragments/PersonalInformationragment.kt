package pe.edu.upc.GlucoCheck.presentation.my_info.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_personal_informationragment.*

import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.UserManager
import java.text.SimpleDateFormat
import java.util.*


class PersonalInformationragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_informationragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    fun setUpView() {
        var user = UserManager.user
        name_data.text = "${user!!.nombre} ${user!!.apellido}"
        sexo_data.text = user.sexo
        nac_data.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(user.fecha_nac.toDate())
        dni_data.text = user.dni
        dic_data.text = user.direccion
        telf_data.text = user.correo
    }

}
