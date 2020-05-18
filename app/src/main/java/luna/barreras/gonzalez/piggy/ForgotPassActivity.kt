package luna.barreras.gonzalez.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var txtemail: EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var progressBar2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        txtemail=findViewById(R.id.txtemail)
        auth=FirebaseAuth.getInstance()
        progressBar2=findViewById(R.id.progressBar2)

    }
    fun send(view: View){
        val email=txtemail.text.toString()

        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                    task ->

                    if(task.isSuccessful){
                        progressBar2.visibility=View.VISIBLE
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Toast.makeText(this,"Error al mandar el Email",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
