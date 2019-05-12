package pe.edu.upc.GlucoCheck.presentation.treatments.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_medicine.*

import pe.edu.upc.GlucoCheck.R

class MedicineFragment : Fragment(){

    var listItems = arrayOf("Losartan")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicine_spinner_id.adapter = ArrayAdapter<String>( activity,android.R.layout.simple_spinner_item,listItems)

        medicine_spinner_id.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textView4.text = "Medicina no seleccionada"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textView4.text = "${listItems[position]}: Bloquea selectivamente el receptor AT1 , lo que provoca una reducción de los efectos de la angiotensina II."
            }

        }


    }

}
