package com.example.cricplayers

data class ResponsePlayerInfo(
	val apikey: String? = null,
	val data: Data
)

data class Data(
	val bowlingStyle: String? = null,
	val country: String? = null,
	val placeOfBirth: String? = null,
	val role: String? = null,
	val name: String? = null,
	val battingStyle: String? = null,
	val dateOfBirth: String? = null,
	val id: String? = null,
	val playerImg:String?=null
)

