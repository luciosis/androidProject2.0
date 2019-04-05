package com.example.client

import com.google.gson.annotations.SerializedName



class PrintValue(@SerializedName("value") var value: String, @SerializedName("type") var type: String)