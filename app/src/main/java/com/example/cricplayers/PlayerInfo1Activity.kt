package com.example.cricplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.player_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class PlayerInfo1Activity : AppCompatActivity() {
    private val playerUrl = "https://api.cricapi.com/v1/players_info?apikey=04dbd83d-3942-475a-b467-387e29a7197d&offset=0&id="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_info)
        val playerID = intent.getStringExtra("ID")
        val playerInfoUrl = playerUrl+playerID
//        Toast.makeText(this, playerInfoUrl,Toast.LENGTH_LONG).show()

        fetchPlayerInfo(playerInfoUrl)
    }

    private fun fetchPlayerInfo(url: String) {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val api = okHttpClient.newCall(request)

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){ api.execute()}
            if(response.isSuccessful) {
                val infoData = Gson().fromJson(response.body?.string(), ResponsePlayerInfo::class.java)
                launch (Dispatchers.Main) {
                    bindData(infoData.data)
                }
            }
        }

    }

    private fun bindData(data: Data) {

        playerNameInfo.text = "Name - "+data.name
        val DOB = data.dateOfBirth.toString()
        playerDOBInfo.text = "DOB - " + DOB.slice(0..9)
        playerBattingStyleInfo.text = "Batting Style - " + data.battingStyle
        if(data.bowlingStyle.isNullOrEmpty()){
            playerBowlingStyleInfo.text = "Bowling Style - --"
        }else {
            playerBowlingStyleInfo.text = "Bowling Style - " + data.bowlingStyle
        }
        playerBirthInfo.text = "Birth Place - " +data.placeOfBirth
        playerCountryInfo.text = "Country - "+ data.country
        playerRoleInfo.text = "Role - "+data.role
        val imageUrl = data.playerImg
        Picasso.get().load(imageUrl).into(playerInfoPic)
    }


}