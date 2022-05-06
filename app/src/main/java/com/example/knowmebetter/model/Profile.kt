package com.example.knowmebetter.model

import java.io.Serializable

class Profile(var fullName: String) : Serializable{
    var shortDescription: String = ""
    val fields = mutableMapOf(Pair("Имя", fullName))

    fun editShortDescription(description: String) {
        shortDescription = description
    }

    fun editField(key: String, value: String) {
        fields[key] = value
    }

    fun deleteField(key: String) = fields.remove(key)

    fun returnData() = fields
}