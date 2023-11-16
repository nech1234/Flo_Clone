package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class SongResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result") val result: FloChartResult
)

data class FloChartResult(
    @SerializedName(value = "songs") val songs : ArrayList<FloChartSongs>
)

data class FloChartSongs(
    @SerializedName("songIdx") val songIdx: Int,
    @SerializedName("albumIdx") val albumIdx: Int,
    @SerializedName("singer") val singer: String,
    @SerializedName("title") val title:String,
    @SerializedName("coverImgUrl") val coverImgUrl : String
)
