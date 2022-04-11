package com.example.knowmebetter.model

//import java.io.File
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//import com.fasterxml.jackson.module.kotlin.readValue
//
//class ProfileIO() {
//
//    fun writeProfilePoolToFile(path: String, profilePool: ProfilePool) {
//        val writer = File(path)
//        val mapper = jacksonObjectMapper()
//        writer.writeText(mapper.writeValueAsString(profilePool))
//    }
//
//    fun readProfilePoolFromFile(path: String): ProfilePool {
//        val totalStr = StringBuilder()
//        for (line in File(path).readLines()) {
//            totalStr.append(line)
//        }
//
//        val mapper = jacksonObjectMapper()
//
//        return mapper.readValue(totalStr.toString())
//    }
//}