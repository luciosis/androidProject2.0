package com.example.client

import com.google.gson.annotations.SerializedName

class KeyboardValue(@SerializedName("value") var value:Char, @SerializedName("type") var type: String)