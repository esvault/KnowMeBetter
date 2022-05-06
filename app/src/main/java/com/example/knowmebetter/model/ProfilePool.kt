package com.example.knowmebetter.model

import java.io.Serializable

class ProfilePool(val profiles: MutableList<Profile> = mutableListOf(), var size: Int = 0): Serializable {
    fun addProfile(profile: Profile) {
        profiles.add(profile)
        size++
    }

    fun deleteProfile(index: Int) {
        profiles.removeAt(index)
        size--
    }

    fun editProfileName(index: Int, newName: String) {
        profiles[index].fullName = newName
    }

    fun editProfileDesc(index: Int, desc: String) {
        profiles[index].shortDescription = desc
    }

    fun editProfile(index: Int, editions: Map<String, String>) {
        val currentProfile = profiles[index]
        for (note in editions) {
            currentProfile.editField(note.key, note.value)
        }
    }

    fun deleteProfileField(index: Int, key: String) {
        val currentProfile = profiles[index]
        currentProfile.deleteField(key)
    }

    fun getProfile(index: Int) = profiles[index]
}