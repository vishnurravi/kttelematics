package com.myktapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.myktapp.databinding.ActivityMainBinding
import com.myktapp.dataclsforinfo.Constants
import com.myktapp.dataclsforinfo.UserDetail
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    private lateinit var buttonSignIn: Button
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonSignIn.setOnClickListener {
            val username = binding.editTextUserName.text.toString()
            val password =binding.editTextUserPassword.text.toString()
            if (validateInput(username, password)) {
                val savedUsername = getSharedPreferencesData("username")
                val savedPassword = getSharedPreferencesData("password")

                if (username == savedUsername && password == savedPassword) {
                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.gsignin.setOnClickListener {
            val signin = mGoogleSignInClient.signInIntent
            startActivityForResult(signin, Req_Code)

        }
        binding.buttonSignUP.setOnClickListener {

            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
//            }

        }

    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == Req_Code) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleResult(task)
            }
        }

        private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
            try {
                val account: GoogleSignInAccount? =
                    completedTask.getResult(ApiException::class.java)
                if (account != null) {
                    updateUI(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }



        supportActionBar?.setDisplayHomeAsUpEnabled(true)





    }
    private fun updateUI(account: GoogleSignInAccount){
        val credenial = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credenial).addOnCompleteListener {
            if (it.isSuccessful){
                val intent:Intent =Intent(this,MapActivity::class.java)
                intent.putExtra("dname",account.displayName)
                intent.putExtra("email",account.email)
                startActivity(intent)}
            else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun getSharedPreferencesData(key: String): String {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    }
