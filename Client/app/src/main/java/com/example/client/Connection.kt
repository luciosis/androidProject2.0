package com.example.client

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket

class ConnectPhoneTask: AsyncTask<String, Void, Boolean>() {

    override fun doInBackground(vararg params: String?): Boolean {
        var result = true
        try {
            val serverAddress = InetAddress.getByName(params[0])
            socket = Socket(serverAddress, 8998)
        } catch (e: IOException) {
            Log.e("remotedroid", "Error while connecting", e)
            result = false
        }
        return result
    }
    override fun onPostExecute(result: Boolean)
    {
        isConnected = result
        Toast.makeText(context, if(isConnected) context.getString(R.string.connected) else context.getString(R.string.connection_failed), Toast.LENGTH_LONG).show()
        try {
            if(isConnected) {
                outstream = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
            }
        }catch (e: IOException){
            Log.e("remotedroid", "Error while creating OutWriter", e)
            Toast.makeText(context,"Error while connecting", Toast.LENGTH_LONG).show()
        }
    }
}
