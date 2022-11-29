package com.example.houseapp

data class FakeRequest(
    val problemType: String = "Insects",
    val text: String = "heeeeeeeeeeeeeeeeeeeeeeeeeelp, I dont know what to do. There is a big cock.roach at my kitchen,f frrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
    val user_id: Int = 1,
    val user_name: String = "Alex Clare",
    val address: String = "Vostochnaya 144",
    val id: Int,
    val isDone: Boolean = true
)