package luna.barreras.gonzalez.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.PhantomReference

class RegisterActivity : AppCompatActivity() {

    private lateinit var textnombre: EditText
    private lateinit var textapellido: EditText
    private lateinit var txtemail: EditText
    private lateinit var txtpassword: EditText
    private lateinit var progresBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textnombre=findViewById(R.id.textnombre)
        textapellido=findViewById(R.id.textapellido)
        txtemail=findViewById(R.id.txtemail)
        txtpassword=findViewById(R.id.txtpassword)

        progresBar=findViewById(R.id.progresBar)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")
    }

    fun register(view: View){
        createNewAccount()
    }

    private fun createNewAccount(){
        val name:String=textnombre.text.toString()
        val lastName:String=textapellido.text.toString()
        val email:String=txtemail.text.toString()
        val password:String=txtpassword.text.toString()

        if(!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(lastName) &&!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)){
            progresBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task->

                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD=dbReference.child(user?.uid.toString())

                        userBD.child("Nombre").setValue(name)
                        userBD.child("Apellido").setValue(lastName)
                        userBD.child("ID").setValue(auth.uid)
                        action()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this,Bienvenida::class.java))
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->

                if(task.isComplete){
                    Toast.makeText(this, "Email Enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Email No Enviado", Toast.LENGTH_LONG).show()
                }
            }
    }
}
