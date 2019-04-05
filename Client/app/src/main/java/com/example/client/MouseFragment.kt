package com.example.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.mousepad_fragment_portrait.*
import android.view.MotionEvent
import android.view.View.OnTouchListener


class MouseFragment: Fragment() {
    private var initX = 0f
    private var initY = 0f
    private var disX = 0f
    private var disY = 0f
    private var mouseMoved = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.mousepad_fragment_portrait, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mousePad.setOnTouchListener{_: View, m: MotionEvent ->
            if(isConnected){
                when(m.action){
                    MotionEvent.ACTION_DOWN ->{
                        initX =m.x
                        initY =m.y
                        mouseMoved=false
                    }
                    MotionEvent.ACTION_MOVE ->{
                        disX = m.x - initX
                        disY = m.y - initY
                        initX = m.x
                        initY = m.y
                        if((disX > 0 || disX < 0) || (disY > 0 || disY < 0)){
                            Thread{
                                outstream.println(gson.toJson(MouseValue(disX, disY, "mouse")))
                            }.start()
                        }
                        mouseMoved=true
                    }
                }
            }
            true
        }
            btnLClick.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    if(isConnected) {
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            Thread {
                                outstream.println(gson.toJson(MouseClickValue("Left click", true, "mouseclick")))
                            }.start()
                        } else if (event.action == MotionEvent.ACTION_UP) {
                            Thread {
                                outstream.println(gson.toJson(MouseClickValue("Left click", false, "mouseclick")))
                            }.start()
                        }
                    }

                    return true
                }
            })
        btnRClick.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if(isConnected) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        Thread {
                            outstream.println(gson.toJson(MouseClickValue("Right click", true, "mouseclick")))
                        }.start()
                    } else if (event.action == MotionEvent.ACTION_UP) {
                        Thread {
                            outstream.println(gson.toJson(MouseClickValue("Right click", false, "mouseclick")))
                        }.start()
                    }
                }

                return true
            }
        })
    }
}
