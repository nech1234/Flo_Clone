package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginSignInBtn.setOnClickListener {
            login()
        }
        setContentView(binding.root)
    }

    private fun login() {
        if (binding.loginIdEt.text.toString()
                .isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String =
            binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd: String = binding.loginPasswordEt.text.toString()

        val userDB = SongDatabase.getInstance(this)!!
        val user = userDB.userDao().getUser(email, pwd)

        if (user != null) {
            Log.d("LoginActivity", user.id.toString())
            saveJwt(user.id)
            startMainActivity()
        } else {
            Toast.makeText(this, "회원정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveJwt(jwt: Int) {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}