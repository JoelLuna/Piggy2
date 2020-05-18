package luna.barreras.gonzalez.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: EditText
    private lateinit var txtContraseña: EditText
    private lateinit var progressBar1: ProgressBar
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario=findViewById(R.id.txtUsuario)
        txtContraseña=findViewById(R.id.txtContraseña)
        progressBar1=findViewById(R.id.progressBar1)
        auth=FirebaseAuth.getInstance()
    }

    fun forgorPassword(view: View){

    }
    fun register(view: View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }
    fun login(view: View){
        loginUser()
    }

    private fun loginUser(){
        val user:String=txtUsuario.text.toString()
        val password:String=txtContraseña.text.toString()

        if(!TextUtils.isEmpty(user)&& !TextUtils.isEmpty(password)){
            progressBar1.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task ->

                    if(task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this, "Usuario no valido", Toast.LENGTH_LONG).show()
                    }
                }
       }
    }
    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }
}
