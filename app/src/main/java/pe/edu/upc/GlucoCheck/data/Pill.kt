package pe.edu.upc.GlucoCheck.data

import com.google.firebase.Timestamp

data class Pill(var id: String, val nombre: String) {
    constructor():this ("","")
}