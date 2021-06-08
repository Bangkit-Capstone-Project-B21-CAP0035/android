package com.example.sehatmentalku.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    // Bagian Auth
    @SerializedName("api_token")
    @Expose
    var apiToken: String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null

    // Bagian getJournal
    @SerializedName("journals")
    @Expose
    var journals: MutableList<Journal?>? = null

    // Bagian getPsychologist
    @SerializedName("psychologists")
    @Expose
    var psychologists: MutableList<Psychologist?>? = null
}