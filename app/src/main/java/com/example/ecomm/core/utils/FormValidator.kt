package com.example.ecomm.core.utils

object FormValidator {

    fun validateEmail(value: String?): String? {
        val input = value?.trim() ?: ""
        if (input.isEmpty()) return "Enter your email address"
        val emailPattern = Regex("""^[a-zA-Z0-9]+([._%+\-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9\-]+(\.[a-zA-Z]{2,})+$""")
        if (!emailPattern.matches(input)) return "Please enter a valid email address"
        return null
    }

    fun validatePassword(value: String?): String? {
        val input = value?.trim() ?: ""
        if (input.isEmpty()) return "Enter your password"
        if (input.length < 6) return "Password must be at least 6 characters"
        return null
    }

    fun validateConfirmPassword(value: String?, confirmPw: String?): String? {
        val input = value?.trim() ?: ""
        if (input.isEmpty()) return "Enter your password"
        if (input.length < 6) return "Password must be at least 6 characters"
        if (input != confirmPw?.trim()) return "Passwords do not match"
        return null
    }

    fun validateName(value: String?): String? {
        val input = value?.trim() ?: ""
        if (input.isEmpty()) return "Enter your full name"
        return null
    }
}