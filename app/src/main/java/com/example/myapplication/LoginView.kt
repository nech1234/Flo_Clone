package com.example.myapplication

interface LoginView {
    fun onLoginSuccess(code : Int, result: Result)
    fun onLoginFailure(message: String)
}