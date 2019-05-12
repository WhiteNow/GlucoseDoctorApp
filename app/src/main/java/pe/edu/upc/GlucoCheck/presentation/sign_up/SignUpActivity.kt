package pe.edu.upc.GlucoCheck.presentation.sign_up

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import pe.edu.upc.GlucoCheck.R

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val aniDrawable = recover_pass_layout.background as AnimationDrawable
        aniDrawable.setEnterFadeDuration(10)
        aniDrawable.setExitFadeDuration(5000)
        aniDrawable.start()

        cancel_btn.setOnClickListener {
            this.finish()
        }


    }
}
