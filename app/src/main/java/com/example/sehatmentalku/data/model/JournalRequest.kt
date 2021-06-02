package com.example.sehatmentalku.data.model

import android.media.Image
import com.google.gson.annotations.SerializedName

data class JournalRequest(
    @SerializedName("story")
    var story: String

//    @SerializedName("image")
//    var image: Image
)
