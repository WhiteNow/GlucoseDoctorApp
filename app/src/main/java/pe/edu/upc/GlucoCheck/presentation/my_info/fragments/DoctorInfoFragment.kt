package pe.edu.upc.GlucoCheck.presentation.my_info.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_doctor_info.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection

import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.Doctor
import pe.edu.upc.GlucoCheck.data.UserManager

class DoctorInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoctor()
    }

    fun getDoctor() {
        var disposable = FirebaseConnection.getDoctor().subscribe({
                subie ->
            UserManager.doctor = subie
            Log.d("Bryam", UserManager.doctor!!.id)
            setupView(subie)
        },{
            Log.e("Bryam", "There is an errooor ! ${it.message}")
        })
    }

    fun setupView(doctor: Doctor) {
        Log.d("Bryam", UserManager.doctor!!.id)
        name_data.text = "${doctor.nombre} ${doctor.apellido}"
        telf_data.text = doctor.celular
        dic_data.text = doctor.edad
        telf_data.text = doctor.email
    }


}
