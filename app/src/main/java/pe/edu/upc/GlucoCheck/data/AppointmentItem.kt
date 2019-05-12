package pe.edu.upc.GlucoCheck.data

import com.google.firebase.Timestamp
import java.util.*

data class AppointmentItem(val id: String,
                           val descripcion: String,
                           val doctor_id: String,
                           val fecha: Timestamp,
                           val paciente_id: String) {
    constructor() : this (
        "",
        "",
        "",
        Timestamp.now(),
        ""
    )

    companion object {

        fun create() :AppointmentItem = AppointmentItem()
    }
}