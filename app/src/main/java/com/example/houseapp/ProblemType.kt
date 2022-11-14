package com.example.houseapp

enum class ProblemType {
    Water, Heat, Electricity, Other;

    fun display(problemType: ProblemType)
    {
        when (problemType){
            Water -> "Водоотведение"
            Heat -> "Теплоснабжение"
            Electricity -> "Электроснабжение"
            else -> "Другое"
        }
    }
}