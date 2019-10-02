package com.gogit.Model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AllProducts_Response(
    @SerializedName("data")
    val `data`: List<AllProducts_Model>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) {
    @Keep
    data class AllProducts_Model(
        @SerializedName("brand_name")
        val brandName: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("image_guide")
        val imageGuide: String,
        @SerializedName("price_general")
        val priceGeneral: String,
        @SerializedName("rates")
        val rates: Int,
        @SerializedName("sale_price")
        val salePrice: String,
        @SerializedName("short_description")
        val shortDescription: String,
        @SerializedName("title")
        val title: String
    )
}