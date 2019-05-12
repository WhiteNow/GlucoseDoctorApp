package pe.edu.upc.GlucoCheck.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_patients.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.adapters.PatientsAdapter
import pe.edu.upc.GlucoCheck.data.User

class PatientsActivity : AppCompatActivity() {

    val patientAdapter by lazy {
        PatientsAdapter()
    }

    lateinit var recyclerView: RecyclerView
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients)
    }

    override fun onSupportNavigateUp(): Boolean {

        return super.onSupportNavigateUp()
    }

    fun getPatients() {
        var users = ArrayList<User>()

        var disposable = FirebaseConnection.getUsers().subscribe({
            subie ->
            for (item in subie) {

            }
            patientAdapter.addItems(subie)
        }, {

        })
        compositeDisposable.add(disposable)
    }

    fun setupRecycler() {
        recyclerView = patients_recycler
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = patientAdapter
        }
    }


}
