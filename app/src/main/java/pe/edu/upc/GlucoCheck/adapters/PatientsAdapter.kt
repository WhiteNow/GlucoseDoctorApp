package pe.edu.upc.GlucoCheck.adapters

import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidnetworking.widget.ANImageView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_personal_informationragment.*
import kotlinx.android.synthetic.main.patient_item_layout.view.*
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.User
import kotlinx.android.synthetic.main.patient_item_layout.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.data.Contact
import pe.edu.upc.GlucoCheck.data.UserManager
import pe.edu.upc.GlucoCheck.data.UserManager.Companion.user
import pe.edu.upc.GlucoCheck.presentation.DetallePaciente.DetallePacienteActivity
import pe.edu.upc.GlucoCheck.presentation.DetallePaciente.DetallePacienteAdapter
import pe.edu.upc.GlucoCheck.presentation.home_menu.HomeMenuActivity
import pe.edu.upc.GlucoCheck.presentation.reports.ReportsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PatientsAdapter(): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {

    private var items: MutableList<User> = ArrayList()


    //lateinit var mClickListener: ClickListener


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var nameTextView: TextView
        var profileImageView: ANImageView
        lateinit var name_data: String
        lateinit var sexo_data: String
        lateinit var nac_data: String
        lateinit var dni_data: String
        lateinit var dic_data: String
        lateinit var email_data: String
        lateinit var key: String
        lateinit var antFam: String
        lateinit var relato:String
        lateinit var pesoInicial:String
        lateinit var tallaInicial:String
        lateinit var fechaInicio:String
        lateinit var c1t:String
        lateinit var c1c:String
        lateinit var c1e:String
        lateinit var c2t:String
        lateinit var c2c:String
        lateinit var c2e:String
        val db = FirebaseFirestore.getInstance()




        init {
            profileImageView = itemView.user_profile_iv
            nameTextView = itemView.name_lbl
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

            Log.d("RecyclerView","CLICK")

            //val intent = Intent(this, ReportsActivity::class.java)
            //startActivity(intent)

            val context = itemView.context

            val showScreen = Intent(context, DetallePacienteActivity::class.java)
            showScreen.putExtra("NombrePaciente",nameTextView.text)
            showScreen.putExtra("Sexo",sexo_data)
            showScreen.putExtra("FechaNac",nac_data)
            showScreen.putExtra("DNI",dni_data)
            showScreen.putExtra("Direccion",dic_data)
            showScreen.putExtra("Correo",email_data)
            showScreen.putExtra("Antecedentes",antFam)
            showScreen.putExtra("Relato",relato)
            showScreen.putExtra("pesoInicial",pesoInicial)
            showScreen.putExtra("tallaInicial",tallaInicial)
            showScreen.putExtra("fechaInicio",fechaInicio)
            showScreen.putExtra("c1t",c1t)
            showScreen.putExtra("c1c",c1c)
            showScreen.putExtra("c1e",c1e)
            showScreen.putExtra("c2t",c2t)
            showScreen.putExtra("c2c",c2c)
            showScreen.putExtra("c2e",c2e)
            showScreen.putExtra("id",key)



            context.startActivity(showScreen)

        }

        fun getContacsDoctor(id :String): Single<java.util.ArrayList<Contact>> {
            var contactos: java.util.ArrayList<Contact> = java.util.ArrayList()
            var contact = Contact()
            //var user = UserManager.user
            val doc = FirebaseConnection.db.collection("Pacientes").document(id).collection("Contactos")
            return Single.create<java.util.ArrayList<Contact>> { subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    Log.d("Bryam", "consultas...")
                    if (document != null) {
                        Log.d("Bryam","Contactos encontrados")
                        for (documentSnapshot in document.documents) {
                            Log.d("Bryam", "entcontre ${document.size()}")
                            contactos.add(documentSnapshot.toObject(Contact::class.java)!!)
                        }
                        subscriber.onSuccess(contactos)
                    }
                }
            }
        }


        fun bindTo(item: User) {

            var idHistoria = ""

            nameTextView.text = "${item.nombre} ${item.apellido}"
            profileImageView.setDefaultImageResId(R.drawable.oldman)

            //name_data  = "${user!!.nombre} ${user!!.apellido}"
            sexo_data = item.sexo
            nac_data = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(item.fecha_nac.toDate())
            dni_data = item.dni
            dic_data = item.direccion
            email_data= item.correo
            key = item.id


            db.collection("Historias").document(item.historia_id)
                .get()
                .addOnSuccessListener { document ->
                    antFam = document.data?.get("ant_familiares").toString()
                    relato = document.data?.get("relato").toString()
                    pesoInicial = document.data?.get("peso").toString() + " kg"
                    tallaInicial = document.data?.get("talla").toString() + " cm"
                    val fecha = ( document.data?.get("inicio") as Timestamp).seconds.toFloat().toLong()


                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val netDate = Date(fecha*1000)

                    fechaInicio = sdf.format(netDate).toString()

                }

            var hola = 1
            db.collection("Pacientes").document(key).collection("Contactos")
                .get()
                .addOnSuccessListener {documents ->
                    for(document in documents){


                        if(hola == 1){
                            c1t = document.data?.get("telefono").toString()
                            c1c = document.data?.get("celular").toString()
                            c1e = document.data?.get("email").toString()
                        }
                        else if (hola == 2){
                            c2t = document.data?.get("telefono").toString()
                            c2c = document.data?.get("celular").toString()
                            c2e = document.data?.get("email").toString()
                        }



                        hola = hola + 1
                    }

                }

            /*val docRef = db.collection("Pacientes").document(key.toString())

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {

                        idHistoria = document.data?.get("historia_id")?.toString().toString()

                    }
                    }

*/
            /*val docRef2 = db.collection("Historias").document(idHistoria)

            docRef2.get()
                .addOnSuccessListener { document ->
                    if(document != null){

                        antFam = document.data?.get("ant_familiares").toString()
                    }
                }*/




        }



    }

    class setOnClickListener()


    fun addItems(list: ArrayList<User>) {
            items.addAll(list)


        notifyDataSetChanged()

    }

    fun clear(){
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsAdapter.ViewHolder  {
        return PatientsAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.patient_item_layout, parent,false)
        )



    }

    /*override fun filter(String: charText){

    }*/

    override fun getItemCount(): Int {



        return items.size
    }

    override fun onBindViewHolder(holder: PatientsAdapter.ViewHolder, position: Int) {


            holder.bindTo(items[position])



    }





}