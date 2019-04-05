package com.example.client

import com.google.gson.annotations.SerializedName


class MouseClickValue(@SerializedName("value") var value: String, @SerializedName("isPressed") var isPressed: Boolean, @SerializedName("type") var type:String)