package com.gogit.Model


import android.annotation.SuppressLint
import android.os.Parcel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Cart_Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("list")
        val list: List<X>,
        @SerializedName("price")
        val price: Double,
        @SerializedName("total_delevery_fees")
        val totalDeleveryFees: String,
        @SerializedName("total_tax")
        val totalTax: Double
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class X(
            @SerializedName("cart_id")
            val cartId: Int,
            @SerializedName("hash_color")
            val hashColor: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("product_id")
            val productId: String,
            @SerializedName("product_name")
            val productName: String,
            @SerializedName("quantity")
            val quantity: String,
            @SerializedName("size")
            val size: String,
            @SerializedName("total_unit_price")
            val totalUnitPrice: Int,
            @SerializedName("unit_price")
            val unitPrice: String
        ) : Parcelable
    }
}