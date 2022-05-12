package com.example.cricplayers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_player.view.*

class PlayerSearchAdapter(val list: List<DataItem>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(position: Int, converterview: View?, parent: ViewGroup?): View {
        val view =converterview?:LayoutInflater.from(parent?.context)
            .inflate( R.layout.list_player,parent,false)

        val item =list[position]
        view.playerName.text = item.name
        view.playerCountry.text = item.country
        view.playerId.text = item.id
        return view
    }
}