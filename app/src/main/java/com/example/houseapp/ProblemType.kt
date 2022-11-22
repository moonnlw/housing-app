package com.example.houseapp

enum class ProblemType {
    Water, Heat, Electricity, Other;
}

fun display(problemType: ProblemType) : String {
    return when (problemType) {
        ProblemType.Water -> "Водоотведение"
        ProblemType.Heat -> "Теплоснабжение"
        ProblemType.Electricity -> "Электроснабжение"
        else -> "Другое"
    }
}