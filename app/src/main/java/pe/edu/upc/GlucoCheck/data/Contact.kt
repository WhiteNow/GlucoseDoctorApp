package pe.edu.upc.GlucoCheck.data

data class Contact(val id: String, val telefono: String, val celular: String, val nombre: String) {
    constructor() : this ("","","","")
}