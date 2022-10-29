package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class ConvertResponse(
    @SerializedName("base_code")
    val base_code: String,
    @SerializedName("conversion_rate")
    val conversion_rate: Double,
    @SerializedName("conversion_result")
    val conversion_result: Double,
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("target_code")
    val target_code: String,
    @SerializedName("terms_of_use")
    val terms_of_use: String,
    @SerializedName("time_last_update_unix")
    val time_last_update_unix: Int,
    @SerializedName("time_last_update_utc")
    val time_last_update_utc: String,
    @SerializedName("time_next_update_unix")
    val time_next_update_unix: Int,
    @SerializedName("time_next_update_utc")
    val time_next_update_utc: String
)