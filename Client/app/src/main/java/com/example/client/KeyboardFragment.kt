package com.example.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.keyboard_fragment_portrait.*

class KeyboardFragment :Fragment(){
    var lenght = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.keyboard_fragment_portrait, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        keyboardTxt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isConnected)
                Thread{
                        if(lenght < keyboardTxt.length()) {
                            outstream.println(
                                gson.toJson(
                                    KeyboardValue(
                                        keyboardTxt.text.toString().toCharArray()[keyboardTxt.length() - 1],
                                        "keyboard"
                                    )
                                )
                            )
                            lenght = keyboardTxt.length()
                        }
                        else{
                            Thread {
                                outstream.println(gson.toJson(PrintValue("BACKSPACE", "keyboard_action")))
                            }.start()
                            lenght = keyboardTxt.length()
                        }

                }.start()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        keyboardTxt.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(isConnected)
                    Thread {
                        outstream.println(gson.toJson(PrintValue("ENTER", "keyboard_action")))
                    }.start()
                    return@setOnKeyListener true
                }
            else if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL){
                    if(isConnected)
                    Thread {
                        outstream.println(gson.toJson(PrintValue("BACKSPACE", "keyboard_action")))
                    }.start()
                    return@setOnKeyListener true
                }

            return@setOnKeyListener false
        }

        }
    }