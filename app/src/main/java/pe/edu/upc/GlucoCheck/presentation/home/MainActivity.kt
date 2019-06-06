package pe.edu.upc.GlucoCheck.presentation.home

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import pe.edu.upc.GlucoCheck.Networking.FirebaseConnection
import pe.edu.upc.GlucoCheck.Networking.GlucosAppApi
import pe.edu.upc.GlucoCheck.R
import pe.edu.upc.GlucoCheck.data.*
import pe.edu.upc.GlucoCheck.data.AppointmentItem
import pe.edu.upc.GlucoCheck.data.User
import pe.edu.upc.GlucoCheck.data.UserManager
import pe.edu.upc.GlucoCheck.presentation.home_menu.HomeMenuActivity
import pe.edu.upc.GlucoCheck.presentation.sign_up.SignUpActivity
import android.view.View.OnTouchListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    var compositeDisposable = CompositeDisposable()
    var showinPassword = false
    lateinit var progresDialog: ProgressDialog
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //AndroidInjection.inject(this)


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                //val msg = getString(R.string.msg_token_fmt, token)
                Log.d("TAG", token)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })

        configureGoogleSignIn()
        firebaseAuth = FirebaseAuth.getInstance()


        //Log.e("new token",keey)

        //auth = FirebaseAuth.getInstance()
        //Log.d("Main", "signInWithEmail:success")

        login_button.setOnClickListener {
            sigInForUser()

        }

        sign_in_button.setOnClickListener{
            signIn()
        }


        showPassBtn.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    password_edit_text.setInputType(InputType.TYPE_CLASS_TEXT)
                }
                MotionEvent.ACTION_UP -> password_edit_text.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
            true
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("524359549487-1rhgnqfn29b16d44qk7eqlv6mhgannj5.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        showDialog()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Main", "signInWithEmail:success")
                //val user = auth.currentUser
                //updateUI(user)
                getUser(acct.email.toString())
                //startActivity(Intent(this, HomeMenuActivity::class.java))

                // val intent = Intent(this, PatientsActivity::class.java)
                // startActivity(intent

            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //val intent = Intent(this, SignUpActivity::class.java)

        /*val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(this, HomeMenuActivity::class.java))
            finish()
        }*/

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun sigInForUser() {
        showDialog()
        val username = username_edit_text.text.toString()
        val password = password_edit_text.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete los campos vacios", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d("Main", "signInWithEmail:success")
                    //val user = auth.currentUser
                    getUser(username)
                }
            }
            .addOnFailureListener {
                progresDialog.dismiss()
                Log.w("Main", "signInWithEmail:failure ${it.message}")
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun getUser(email: String) {
        var disposable = FirebaseConnection.getUserDoctor(email).subscribe ({
                subie ->
            Log.d("Bryam", "size while observing ${subie}")
            UserManager.doctor = subie
            progresDialog.dismiss()
            val intent = Intent(this, HomeMenuActivity::class.java)
            startActivity(intent)
        }, {
            Log.e("Bryam", "There is an errooor ! ${it.message}")
            progresDialog.dismiss()
        })
        compositeDisposable.add(disposable)
    }

    private fun showDialog() {
        progresDialog = ProgressDialog(this)
        progresDialog.setMessage("Iniciando sesion.....")
        progresDialog.setCancelable(false)
        progresDialog.show()
    }

}
