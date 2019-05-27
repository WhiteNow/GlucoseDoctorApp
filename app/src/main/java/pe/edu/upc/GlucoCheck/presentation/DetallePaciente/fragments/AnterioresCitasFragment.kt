package pe.edu.upc.GlucoCheck.presentation.DetallePaciente.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_ant_citas.*
import kotlinx.android.synthetic.main.fragment_ant_citas.view.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.adapters.AppointmentAdapter
import pe.edu.upc.GlucoCheck.data.AppointmentItem

class AnterioresCitasFragment: Fragment() {

    var TAG = "Appointment"

    val appointmentAdapter by lazy {
        AppointmentAdapter()
    }

    lateinit var recyclerView: RecyclerView
    var compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ant_citas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpView()
        setupRecycler()
        getAppointments()

    }

    /*fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }*/

    fun getAppointments() {

        val valueID = activity?.intent?.getStringExtra("id").toString()

        var appointments = ArrayList<AppointmentItem>()

        var disposable =  FirebaseConnection.getAppointmentsID(valueID).subscribe ({

                subie ->
            Log.d("Bryam", "size while observing ${subie.size}")
            for (item in subie){
                Log.d("Bryam", "these are the items ${item.fecha}")
            }
            appointmentAdapter.addItems(subie)

        }, {
            Log.e("Bryam", "There is an errooor ! ${it.message}")
        })
        compositeDisposable.add(disposable)
    }

    fun setupRecycler() {
        val view: View = appointment_view2
        recyclerView = view.appointments2
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = appointmentAdapter
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()

    }

    fun setUpView() {
        //val valueID = activity?.intent?.getStringExtra("key")
        //answer2_lbl.text = valueAntecedentes

    }
}