package com.example.client


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.connection_fragment_portrait.*
import java.io.File
import java.nio.charset.Charset

class ConnectFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.connection_fragment_portrait, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val file : File = context!!.getFileStreamPath("IP_Address")
        if(file.exists()){
            Connect_IP.setText(file.readBytes().toString(Charset.defaultCharset()))
        }
        ConnectButton.setOnClickListener {
            context!!.openFileOutput("IP_Address", Context.MODE_PRIVATE).use{
                it.write(Connect_IP.text.toString().toByteArray())
            }
            if(!isConnected) {
                connectPhoneTask = ConnectPhoneTask()
                connectPhoneTask.execute(Connect_IP.text.toString())
            }
        }

    }
}