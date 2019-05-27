package pe.edu.upc.GlucoCheck.presentation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_patients.*
import kotlinx.android.synthetic.main.patient_item_layout.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.adapters.PatientsAdapter
import pe.edu.upc.GlucoCheck.data.User
import pe.edu.upc.GlucoCheck.presentation.reports.ReportsActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Filter;
import android.widget.Filterable;

class PatientsActivity : AppCompatActivity() {

    val patientAdapter by lazy {
        PatientsAdapter()
    }

    lateinit var recyclerView: RecyclerView
    var compositeDisposable = CompositeDisposable()
    lateinit var etseartch: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients)




        editText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun afterTextChanged(s: Editable?) {

                if(s?.isBlank()!!){

                    patientAdapter.clear()
                    var valueText = editText.text.toString()
                    setupRecycler()
                    getPatients(valueText)
                }

            }

        })

        var texto = editText.text.toString()

        setupRecycler()
        getPatients(texto)
        //etseartch =  findViewById(R.id.editText) as EditText

        //etseartch.addTextChangedListener()

        button.setOnClickListener {

            patientAdapter.clear()
            var valueText = editText.text.toString()
            setupRecycler()
            getPatients(valueText)

        }

    }

    override fun onSupportNavigateUp(): Boolean {

        return super.onSupportNavigateUp()
    }



    fun getPatients(filtro :String) {

        if(filtro.length == 0){

            var disposable = FirebaseConnection.getUsersTotal("").subscribe({
                    subie ->
                for (item in subie) {



                }
                patientAdapter.addItems(subie)





            }, {

            })
            compositeDisposable.add(disposable)
        }else{

            var disposable = FirebaseConnection.getUsersTotal(filtro).subscribe({
                    subie ->
                for (item in subie) {



                }
                patientAdapter.addItems(subie)





            }, {

            })
            compositeDisposable.add(disposable)
        }







    }





    fun setupRecycler() {
        recyclerView = patients_recycler
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = patientAdapter

        }

    }


}
