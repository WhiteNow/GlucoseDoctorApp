package pe.edu.upc.GlucoCheck.data

import com.google.firebase.Timestamp

data class Sample(val id: String,  val estado: String, val nivel: Int) {
    constructor() : this ("","",1)
}