package com.example.ecomm.core.navigation

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val EDIT_PROFILE = "editProfile"

    const val PRODUCT_DETAIL = "productDetail"
    const val PRODUCT_ID_ARG = "id"
    const val PRODUCT_DETAIL_ROUTE = "$PRODUCT_DETAIL/{$PRODUCT_ID_ARG}"

    fun productDetail(id: String): String = "$PRODUCT_DETAIL/$id"
}
