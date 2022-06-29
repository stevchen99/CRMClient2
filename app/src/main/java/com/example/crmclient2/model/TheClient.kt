package com.example.crmclient2.model

data class TheClient(
    val IdClient: Int,
    val Company: String
)
{ override fun toString(): String = Company}
