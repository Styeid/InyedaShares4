package com.jufaja.inyedashares

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_aaalogin.*
import java.text.SimpleDateFormat
import java.util.*

private  const val TAG = "AAALoginActivity"
class AAAloginActivity : AppCompatActivity() {

    lateinit var tvdate: TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aaalogin)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            baaScreen()
        }
        // Showing date in Loginscreen
        tvdate = findViewById(R.id.tvdateaaa)
        val currentDay: TextView = this.findViewById(R.id.tvdateaaa)
        val dateConventional = SimpleDateFormat("dd.MMM.yyyy")
        val currentDate: String = dateConventional.format(Date())
        currentDay.text = currentDate
        // Login BTN
        btnlogin.setOnClickListener {
            btnlogin.isEnabled = false
            val email = etemail.text.toString()
            val password = etpassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Enter valid Email and Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            // Firebase authentication check
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnlogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show()
                    baaScreen()
                } else {
                    Log.i(TAG, "Sign-in with Email/Password is failed", task.exception )
                    Toast.makeText(this, "Invalid Email or Password",
                    Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun baaScreen() {
        Log.i(TAG, "BAHomepage")
        val intent = Intent(this, BAAHomepageActivity::class.java )
        startActivity(intent)
        finish()
    }
}