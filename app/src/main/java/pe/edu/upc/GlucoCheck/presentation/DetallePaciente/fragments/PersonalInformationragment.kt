package pe.edu.upc.GlucoCheck.presentation.DetallePaciente.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Single
//import kotlinx.android.synthetic.main.edit_dialog_layout.view.*
import kotlinx.android.synthetic.main.fragment_personal_informationragment.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection

import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.Contact
import pe.edu.upc.GlucoCheck.data.UserManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class PersonalInformationragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //val value = intent.getStringExtra("NombrePaciente")
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

        val valueNombre = activity?.intent?.getStringExtra("NombrePaciente")
        name_data.text = valueNombre
        val valueSexo = activity?.intent?.getStringExtra("Sexo")
        sexo_data.text = valueSexo
        val valueFecha = activity?.intent?.getStringExtra("FechaNac")
        nac_data.text = valueFecha
        val valueDNI = activity?.intent?.getStringExtra("DNI")
        dni_data.text = valueDNI
        val valueDir = activity?.intent?.getStringExtra("Direccion")
        dic_data.text = valueDir
        val valueCorreo = activity?.intent?.getStringExtra("Correo")
        telf_data.text = valueCorreo
        val valueC1T = activity?.intent?.getStringExtra("c1t")
        ctc1Phone_data.text = valueC1T
        val valueC1c = activity?.intent?.getStringExtra("c1c")
        ctc1Cell_data.text = valueC1c
        val valueC1E = activity?.intent?.getStringExtra("c1e")
        ctc1Email_data.text = valueC1E
        val valueC2T = activity?.intent?.getStringExtra("c2t")
        ctc2Phone_data.text = valueC2T
        val valueC2C = activity?.intent?.getStringExtra("c2c")
        ctc2Cell_data.text = valueC2C
        val valueC2E = activity?.intent?.getStringExtra("c2e")
        ctc2Email_data.text = valueC2E


        /*var user = UserManager.user
        name_data.text = "${user!!.nombre} ${user!!.apellido}"
        sexo_data.text = user.sexo
        nac_data.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(user.fecha_nac.toDate())
        dni_data.text = user.dni
        dic_data.text = user.direccion
        telf_data.text = user.correo*/
    }

}