package com.example.sehatmentalku.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Errors {
    @SerializedName("username")
    @Expose
    var username: List<String>? = null
}