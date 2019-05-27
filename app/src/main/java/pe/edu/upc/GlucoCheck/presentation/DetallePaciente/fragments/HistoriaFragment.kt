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
//import kotlinx.android.synthetic.main.edit_dialog_layout.view.*
//import kotlinx.android.synthetic.main.fragment_personal_informationragment.*
import kotlinx.android.synthetic.main.fragment_antecedentes.*;
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection

import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.UserManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class HistoriaFragment:Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //val value = intent.getStringExtra("NombrePaciente")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_antecedentes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()


    }

    fun setUpView() {

        val valueAntecedentes = activity?.intent?.getStringExtra("Antecedentes")
        answer2_lbl.text = valueAntecedentes
        val valueRelato = activity?.intent?.getStringExtra("Relato")
        answer3_lbl.text = valueRelato
        val valuePeso = activity?.intent?.getStringExtra("pesoInicial")
        answer4_lbl.text = valuePeso
        val valueTalla = activity?.intent?.getStringExtra("tallaInicial")
        answer5_lbl.text = valueTalla
        val valueFecha = activity?.intent?.getStringExtra("fechaInicio")
        answer6_lbl.text = valueFecha

        /*val valueNombre = activity?.intent?.getStringExtra("NombrePaciente")
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
        telf_data.text = valueCorreo*/


        /*var user = UserManager.user
        name_data.text = "${user!!.nombre} ${user!!.apellido}"
        sexo_data.text = user.sexo
        nac_data.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(user.fecha_nac.toDate())
        dni_data.text = user.dni
        dic_data.text = user.direccion
        telf_data.text = user.correo*/
    }

}