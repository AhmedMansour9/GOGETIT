package com.gogit.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Order_Response(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable