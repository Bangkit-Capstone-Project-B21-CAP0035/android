package com.example.sehatmentalku.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Psychologist {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    @SerializedName("gmap")
    @Expose
    var gmap: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: Any? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: Any? = null
}