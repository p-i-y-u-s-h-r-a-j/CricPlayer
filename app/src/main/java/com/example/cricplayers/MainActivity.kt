package com.example.cricplayers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.size
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_player.*
import kotlinx.android.synthetic.main.list_player.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.IDN
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var playerSearchAdapetr: PlayerSearchAdapter
    private val playerSearchUrl = "https://api.cricapi.com/v1/players?apikey=04dbd83d-3942-475a-b467-387e29a7197d&offset=0&search="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isOnline(this)){
            val alert = AlertDialog.Builder(this)
            alert.setTitle("INTERNET CONNECTION NOT FOUND")
            alert.setMessage("Please Check Your internet Connectivity")
            alert.setCancelable(false)
            alert.create()
            alert.show()
        }

        search.setOnClickListener{
            fetchPlayerSearch(playerSearch)
        }
        playerSearchLV.isClickable = true
        playerSearchLV.setOnItemClickListener { parent, view, position, l ->
            val id = view.playerId.text
            val intent = Intent(this, PlayerInfo1Activity::class.java)
                .putExtra("ID", id)
            startActivity(intent)
        }

    }
    private fun fetchPlayerSearch(playerSearch: EditText?){
        val playerName = playerSearch?.text.toString()
        val url = playerSearchUrl   + playerName
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val api = okHttpClient.newCall(request)
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){api.execute()}
            if(response.isSuccessful){
                val dataSearch = Gson().fromJson(response.body?.string(), ResponsePlayerSearch::class.java)
                launch(Dispatchers.Main) {
                    bindPlayerSearch(dataSearch.data.subList(0,dataSearch.data.size))
                }
            }
        }
    }

    private fun bindPlayerSearch(subList: List<DataItem>) {
        playerSearchAdapetr = PlayerSearchAdapter(subList)
        playerSearchLV.adapter =playerSearchAdapetr
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
}