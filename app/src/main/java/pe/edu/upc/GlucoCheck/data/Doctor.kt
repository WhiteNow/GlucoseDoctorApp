package pe.edu.upc.GlucoCheck.data

data class Doctor(
    var id: String, val nombre: String, val apellido: String,
    val celular: String, val dni: String, val edad: String,
    val email: String) {
    constructor():this ("","", "", "", "", "", "")
}