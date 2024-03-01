package com.myktapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUp : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        editTextUsername = findViewById(R.id.signup_Username)
        editTextPassword = findViewById(R.id.signup_Password)
        editTextConfirmPassword = findViewById(R.id.signup_ConfirmPassword)
        buttonSave = findViewById(R.id.buttonSave)


        buttonSave.setOnClickListener {

            val userName = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()


            if (validateInput(userName, password, confirmPassword)) {
//                val intent = Intent(this, ActivityLogin::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
                // Save the username and password in SharedPreferences
                saveUserData(userName, password)

                Toast.makeText(this, "Username and Password saved", Toast.LENGTH_SHORT).show()

                // Navigate back to the previous activity (ActivityLogin)
//                val intent = Intent(this, ActivityLogin::class.java)
//                startActivity(intent)
//                finish()
//                Toast.makeText(this, "Username and Password saved", Toast.LENGTH_SHORT).show()
                navigateToLogin()

            }
        }
    }

    //    private fun validateInput(
//        userName: String,
//        password: String
//    ): Boolean {
//
//        //
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle back button clicks
//        if (item.itemId == android.R.id.home) {
//            onBackPressed()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//}
    private fun validateInput(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (userName.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            return false
        }
        //email validation
        if (!isEmailValid(userName)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        // Perform additional validation logic if needed

        return true
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUserData(userName: String, password: String) {
        //getSharedPreferences() is used to retrieve an instance of the sharedPreferences
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        //editor used to edit and Set the values in the SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("username", userName)
        editor.putString("password", password)
        //save the values
        editor.apply()
    }

//    private fun navigateToLogin() {
//        val intent = Intent(this, ActivityLogin::class.java)
//        startActivity(intent)
//        finish()
//    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
    //    override fun onBackPressed() {
//        val intent = Intent(this, ActivityLogin::class.java)
//        startActivity(intent)
//        finish()


