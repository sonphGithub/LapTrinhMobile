package com.example.food01android.activity.LogIn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.food01android.databinding.ActivityLoginBinding
import com.example.food01android.model.LogIn.LogIn
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import com.google.android.gms.tasks.Task
import android.telephony.TelephonyManager
import com.example.food01android.FoodApi
import java.security.AccessController.getContext


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var callbackManager: CallbackManager

    val RC_SIGN_IN: Int = 0
    lateinit var loginmanager: LoginManager
    lateinit var sharedPreferences: SharedPreferences
    var uuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE)
        supportActionBar?.hide()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnSignInGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        //get_uuid
//        val tManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//         this.uuid = tManager.deviceId

        uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
        Log.d("uuid", "" + uuid)

        actionSkipLogIn()
        //hashkey
        printHashKey(this)
//        FacebookSdk.setClientToken("Client token")
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(application)
//        callBackFacebookLogin()
        actionSignIn()
    }

    fun actionSignIn() {
        actionSignInFacebook()
        actionSignInGoogle()
    }

    fun callBackFacebookLogin() {
//        loginmanager = LoginManager.getInstance()
//        callbackManager = CallbackManager.Factory.create()
//        loginmanager.registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult?> {
//                override fun onSuccess(loginResult: LoginResult?) {
//                    Log.d("statusLogin", "success")
//                    val graphRequest =
//                        GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { obj, response ->
//                            try {
//                                val facebookid = obj?.getString("id")
//                                val email = obj?.getString("email")
//                                if (obj?.has("email")!!) {
//                                    val email = obj?.getString("email")
//                                    requestLoginFacebook(facebookid.toString(), email.toString())
//                                }
//
//                                Log.d("facebookdata", obj.getString("email"))
//                                Log.d("facebookdata", obj.getString("id"))
//
//
//                            } catch (e: Exception) {
//                                Log.d("erorr", "" + e)
//                            }
//                        }
//                    val bundle = Bundle()
//                    bundle.putString("fields", "name,id,email")
//                    graphRequest.parameters = bundle
//                    graphRequest.executeAsync()
//                }
//
//                override fun onCancel() {
//                    Log.d("statusLogin", "cancel")
//                }
//
//                override fun onError(exception: FacebookException) {
//                    Log.d("statusLogin", "error log in$exception")
//                }
//            })
    }

    fun actionSignInFacebook() {
        binding.btnSignInFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
//        else{
//            callbackManager.onActivityResult(requestCode, resultCode, data)
//        }
    }

    fun requestLoginFacebook(id: String, email: String) {
        val foodApi: FoodApi = FoodApi.invoke()

        foodApi.logInFacebook(id, email).enqueue(object : Callback<LogIn> {
            override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("token", response.body()?.data?.token)
                editor.commit()

                val intent = Intent(this@LogInActivity, VegetarianActivity::class.java)
                startActivity(intent)
                Log.d("shareP", "" + sharedPreferences.getString("token", ""))
            }

            override fun onFailure(call: Call<LogIn>, t: Throwable) {
                Log.d("post", "post fail")
            }
        })
    }

    fun requestLogInGoogle(name: String, email: String) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.logInGoogle(name, email).enqueue(object : Callback<LogIn> {
            override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {
                Log.d("post", "" + response.body()?.data?.token)
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("token", response.body()?.data?.token)

                editor.commit()

                val intent = Intent(this@LogInActivity, VegetarianActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<LogIn>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    fun actionSignInGoogle() {

    }
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//
//        } catch (e: ApiException) {
//            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//        }
//    }


    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult()
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personName = acct.displayName
                val personEmail = acct.email
                Log.d("googledata", "" + personEmail)
                Log.d("googledata", "" + personName)
                requestLogInGoogle(personName.toString(), personEmail.toString())
            } else {
                Log.d("sidna", "slnjdasdkas")
            }
        } catch (e: ApiException) {
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
        }
    }

    fun requestSkipLogin() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.skipLogIn(uuid.toString()).enqueue(object : Callback<LogIn> {
            override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("token", response.body()?.data?.token)
                editor.commit()
                val intent = Intent(this@LogInActivity, VegetarianActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<LogIn>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    fun actionSkipLogIn() {
        binding.btnAccessNow.setOnClickListener {
//            Log.d("sskip", "aaaa")
            requestSkipLogin()
        }

    }

    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i("TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("TAG", "printHashKey()", e)
        } catch (e: java.lang.Exception) {
            Log.e("TAG", "printHashKey()", e)
        }
    }


}



