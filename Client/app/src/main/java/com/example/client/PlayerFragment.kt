package com.example.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.player_fragment_portrait.*


class PlayerFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.player_fragment_portrait, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            btnPlay.setOnClickListener { onAction("player", "play") }
            btnNext.setOnClickListener { onAction("player", "next") }
            btnPrev.setOnClickListener { onAction("player", "previous") }
            btnVolUp.setOnClickListener { onAction("player", "volume up") }
            btnVolDn.setOnClickListener { onAction("player", "volume down") }
            btnMute.setOnClickListener{ onAction("player", "mute")}

    }

    private fun onAction(type: String, value: String){
        if(isConnected){
            Thread {
                val printValue = PrintValue(value, type)
                outstream.println(gson.toJson(printValue))
            }.start()
        }
    }
}
