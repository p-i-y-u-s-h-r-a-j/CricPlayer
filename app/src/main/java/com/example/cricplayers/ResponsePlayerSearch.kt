package com.example.cricplayers

data class ResponsePlayerSearch(
	val apikey: String? = null,
	val data: List<DataItem>
)


data class DataItem(
	val country: String? = null,
	val name: String? = null,
	val id: String? = null
)

