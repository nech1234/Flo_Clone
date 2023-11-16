package com.example.myapplication

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String)
}