package com.example.client

import com.google.gson.annotations.SerializedName

class MouseValue(@SerializedName("x") var x:Float, @SerializedName("y") var y:Float, @SerializedName("type") var type:String)