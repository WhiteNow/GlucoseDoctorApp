package pe.edu.upc.GlucoCheck.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.firebase.Timestamp

@JsonIgnoreProperties
data class User(val contactos: ArrayList<Contact>,
                var id: String, val telefono: String,
                val fotourl: String, val nombre: String, val historia_id: String,
                val doctor_id: String, val correo: String,
                val dni: String, val sexo: String, val direccion: String,
                val apellido: String, val fecha_nac: Timestamp
                ) {

    constructor() : this(
        ArrayList<Contact>(),
        "","","",
        "","","","","", "",
        "","", Timestamp.now()
    )
}