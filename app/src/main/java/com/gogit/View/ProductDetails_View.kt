package com.gogit.View

import com.gogit.Model.AllProducts_Response
import com.gogit.Model.Categories_Response

interface ProductDetails_View {

    fun Details(detailsProduct: AllProducts_Response.AllProducts_Model)
}