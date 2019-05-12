package pe.edu.upc.GlucoCheck.presentation.appointmets

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.firebase.Timestamp
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_appoinments.view.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.adapters.AppointmentAdapter
import pe.edu.upc.GlucoCheck.data.AppointmentItem

class AppoinmentsActivity : AppCompatActivity() {

    var TAG = "Appointment"

    val appointmentAdapter by lazy {
        AppointmentAdapter()
    }

    lateinit var recyclerView: RecyclerView
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoinments)
        setupRecycler()
        getAppointments()
     }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getAppointments() {
        var appointments = ArrayList<AppointmentItem>()

       var disposable =  FirebaseConnection.getAppointments().subscribe ({

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
        val view: View = findViewById(R.id.appointment_view)
        recyclerView = view.appointments
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = appointmentAdapter
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()

    }
}
