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
import pe.edu.upc.GlucoCheck.data.AppointmentItem
import pe.edu.upc.GlucoCheck.data.User
import pe.edu.upc.GlucoCheck.data.UserManager
import pe.edu.upc.GlucoCheck.presentation.home_menu.HomeMenuActivity
import pe.edu.upc.GlucoCheck.presentation.sign_up.SignUpActivity
import android.view.View.OnTouchListener





class MainActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    var compositeDisposable = CompositeDisposable()
    var showinPassword = false
    lateinit var progresDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        Log.d("Main", "signInWithEmail:success")

        login_button.setOnClickListener {
            sigInForUser()
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        val intent = Intent(this, SignUpActivity::class.java)
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
                    val user = auth.currentUser
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
        var disposable = FirebaseConnection.getUser(email).subscribe ({
                subie ->
            Log.d("Bryam", "size while observing ${subie}")
            UserManager.user = subie
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
