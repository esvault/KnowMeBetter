package com.example.knowmebetter.model

val p1 = Profile("Maxim Novikov")
val p2 = Profile("Mikhail Vintsukevich")

val pool = ProfilePool().apply {
    addProfile(p1)
    addProfile(p2)
    editProfile(0, mapOf(Pair("Age", "20"), Pair("Weight", "70")))
    editProfile(1, mapOf(Pair("Age", "19"), Pair("Dormitory", "14")))
}