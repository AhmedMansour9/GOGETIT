package com.gogit.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class OrderDetails_Response(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("image")
        val image: String,
        @SerializedName("product_id")
        val productId: String,
        @SerializedName("product_name")
        val productName: String,
        @SerializedName("product_price")
        val productPrice: String,
        @SerializedName("product_quantity")
        val productQuantity: String,
        @SerializedName("product_tax")
        val productTax: String
    ) : Parcelable
}