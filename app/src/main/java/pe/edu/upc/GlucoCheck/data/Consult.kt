package pe.edu.upc.GlucoCheck.data

data class Consult(var id: String, val ejercicio: String, val peso: Int, val sintomas: String){
    constructor() : this ("","",1,"")
}