package org.example.a2023_11_cdan_server.model

import jakarta.persistence.Entity




data class StudentBean(var name:String = "", var note : Int = 0)
data class MessageBean(var pseudo: String, var message : String)

data class PlaneBean(var name: String ="", var id:String ="") {

    fun isCorrectFill() = name.isNotBlank() && id.isNotBlank()

}
