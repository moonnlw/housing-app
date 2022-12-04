package com.example.houseapp.data

enum class ProblemType {
    Water, Heat, Electricity, Other;
}

fun convert(problemType: ProblemType) : String {
    return when (problemType) {
        ProblemType.Water -> "Водоотведение"
        ProblemType.Heat -> "Теплоснабжение"
        ProblemType.Electricity -> "Электроснабжение"
        else -> "Другое"
    }
}