package com.example.client

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.GsonBuilder
import java.io.PrintWriter
import java.net.Socket

var isConnected = false
lateinit var context: Context
lateinit var outstream: PrintWriter
lateinit var socket: Socket
val gson = GsonBuilder().create()!!
lateinit var connectPhoneTask: ConnectPhoneTask

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        supportFragmentManager.beginTransaction().replace(R.id.fragment_content, ConnectFragment()).commit()

        val navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(navListener)
        navigation.menu.getItem(0)
    }

    private val navListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            lateinit var selectedFragment: Fragment
            when (item.itemId) {
                R.id.mouseTab -> {
                    selectedFragment = MouseFragment()
                }
                R.id.playerTab -> {
                    selectedFragment = PlayerFragment()
                }
                R.id.keyboardTab -> {
                    selectedFragment = KeyboardFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_content, selectedFragment).commit()
            return true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_connect, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_connect) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_content, ConnectFragment()).commit()
        }

        return super.onOptionsItemSelected(item)
    }
}