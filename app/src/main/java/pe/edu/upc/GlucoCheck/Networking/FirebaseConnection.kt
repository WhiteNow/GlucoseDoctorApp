package pe.edu.upc.GlucoCheck.Networking

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import pe.edu.upc.GlucoCheck.data.*

class FirebaseConnection {

    companion object {

        val db = FirebaseFirestore.getInstance()
        fun getAppointments(): Single<ArrayList<AppointmentItem>> {
            var appointments: ArrayList<AppointmentItem> = ArrayList<AppointmentItem>()
            var appointment: AppointmentItem = AppointmentItem()
            val doc = db.collection("Citas").whereEqualTo("paciente_id","JwvffihQuAeHwvBmLf2c")

            return Single.create<ArrayList<AppointmentItem>> { subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    Log.d("Bryam", "Networkeando :v")
                    if (document != null) {
                        Log.d("Bryam", "Networkeando  dentro:v")
                        for (documentSnapshot in document.documents) {
                            Log.d("Bryam", "size is ${document.documents.size}  dentro:v")
                            appointments.add(documentSnapshot.toObject(AppointmentItem::class.java)!!)

                        }
                        subscriber.onSuccess(appointments)

                    }
                }
            }
        }

        fun getUser(email: String): Single<User> {
            var user = User()
            val doc = db.collection("Pacientes").whereEqualTo("correo",email)
            return Single.create<User> { subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    Log.d("Bryam", "buscando...")
                    if (document != null) {
                        Log.d("Bryam", "Encontre 1...")
                        for (documentSnapshot in document.documents) {
                            Log.d("Bryam", "size is ${document.documents.size}")

                            user = documentSnapshot.toObject(User::class.java)!!
                            user.id = documentSnapshot.id
                            Log.d("Bryam", "the id is ${documentSnapshot.id}")
                            Log.d("Bryam", "the id is ${user.id}")
                        }
                    }
                    subscriber.onSuccess(user)
                }
            }
        }

        fun getContacs(): Single<ArrayList<Contact>> {
            var contactos: ArrayList<Contact> = ArrayList()
            var contact = Contact()
            var user = UserManager.user
            val doc = db.collection("Pacientes").document(user!!.id).collection("Contactos")
            return Single.create<ArrayList<Contact>> { subscriber ->
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

        fun getDoctor(): Single<Doctor> {
            var doctor = Doctor()
            var user = UserManager.user
            val doc = db.collection("Doctores").document(user!!.doctor_id)
            return Single.create<Doctor> {subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    if (document != null) {
                        Log.d("Bryam","Doctor encontrado")
                        doctor = document.toObject(Doctor::class.java)!!
                        doctor.id = document.id
                        subscriber.onSuccess(doctor)
                    }
                }
            }
        }

        fun getConsults(): Single<Consult> {
            var consult = Consult()
            var user = UserManager.user
            val doc = db.collection("Consultas").whereEqualTo("paciente_id",user!!.id)
            return Single.create<Consult> {subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    if (document != null) {
                        consult = document.first().toObject(Consult::class.java)
                        consult.id = document.first().id
                        subscriber.onSuccess(consult)
                    }
                }
            }
        }

        fun getFood(): Single<ArrayList<Food>> {
            var foods: ArrayList<Food> = ArrayList()
            var food: Food
            val doc = db.collection("Consultas").document(UserManager.lastConsult!!.id).collection("Alimentacion")
            return Single.create<ArrayList<Food>> {subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    if (document != null) {
                        for(documentSnapshot in document.documents) {
                            food = documentSnapshot.toObject(Food::class.java)!!
                            food.id = documentSnapshot.id
                            foods.add(food)
                        }
                        subscriber.onSuccess(foods)
                    }
                }
            }
        }

        fun getPills(): Single<ArrayList<Pill>> {
            var pills: ArrayList<Pill> = ArrayList()
            var pill = Pill()
            val doc = db.collection("Pacientes").document(UserManager.user!!.id).collection("Medicamentos")
            return Single.create<ArrayList<Pill>> {subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    if (document != null) {
                        for(documentSnapshot in document.documents) {
                            pill = documentSnapshot.toObject(Pill::class.java)!!
                            pill.id = documentSnapshot.id
                            pills.add(pill)
                        }
                        subscriber.onSuccess(pills)
                    }
                }
            }
        }

        fun getUsers(): Single<ArrayList<User>> {
            var users: ArrayList<User> = ArrayList()
            var user = User()
            val doc = db.collection("Pacientes")
            return Single.create<ArrayList<User>> { subscriber ->
                doc.get().addOnCompleteListener {
                        task ->
                    val document = task.result
                    Log.d("Bryam", "buscando...")
                    if (document != null) {
                        Log.d("Bryam", "Encontre 1...")
                        for (documentSnapshot in document.documents) {
                            Log.d("Bryam", "size is ${document.documents.size}")

                            user = documentSnapshot.toObject(User::class.java)!!
                            user.id = documentSnapshot.id
                            users.add(user)
                            Log.d("Bryam", "the id is ${documentSnapshot.id}")
                            Log.d("Bryam", "the id is ${user.id}")
                        }
                    }
                    subscriber.onSuccess(users)
                }
            }
        }

    }
}