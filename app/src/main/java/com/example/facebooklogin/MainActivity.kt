package com.example.facebooklogin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.gson.Gson
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        Log.i("TAG", "onCreate KEYHASH000  :")
        val key = generateKeyHash()
        Log.i("TAG", "onCreate KEYHASH111: $key")*/

        setTextView()
        logooutBtn()
        checkFb()
        setupFb1()
    }


    lateinit var txv: TextView
    private fun setTextView() {

        txv = findViewById<TextView>(R.id.txv_data)

    }

    private fun logooutBtn() {
        val btnlo = findViewById<View>(R.id.btn_logout)
        btnlo.setOnClickListener {
            LoginManager.getInstance().logOut()
            txv.text = "CLEAR"
        }
    }

    private fun checkFb() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        Log.i("TAG", "checkFb: $isLoggedIn")
        txv.text = "ISLOGGED : $isLoggedIn"
    }

    private fun setupFb2() {

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    Log.i("registerCallback", "onSuccess: ")

                    txv.text = "DATA: ${Gson().toJson(loginResult)}"

                }

                override fun onCancel() {
                    // App code
                    Log.i("registerCallback", "onCancel: ")
                    txv.text = "on Cancel"
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.i("registerCallback", "onError: ")
                    txv.text = "OnError ${exception.message}"
                }
            })
    }

    private fun setupFb1() {

        callbackManager = CallbackManager.Factory.create();

        Log.i("#Brandon", "setupFb1: ${callbackManager}")

        val loginButton = findViewById<View>(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        // If using in a fragment
        // If using in a fragment
//        loginButton.setFragment(this)

        // Callback registration

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                Log.i("registerCallback", "onSuccess: ")

                txv.text = "DATA: ${Gson().toJson(loginResult)}"
            }

            override fun onCancel() {
                // App code
                Log.i("registerCallback", "onCancel: ")
                txv.text = "on Cancel"
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.i("registerCallback", "onError: ")
                txv.text = "OnError ${exception.message}"
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val mock = false

        Log.i("TAG", "onActivityResult: $mock")

        if (callbackManager == null) {
            Log.i("TAG", "onActivityResult: IS NULL $mock")
        } else {
            Log.i("TAG", "onActivityResult: NOT NULL $mock")
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }

        //callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun generateKeyHash(): String? {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return String(Base64.encode(md.digest(), 0))
            }
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }
        return "key hash not found"
    }
}