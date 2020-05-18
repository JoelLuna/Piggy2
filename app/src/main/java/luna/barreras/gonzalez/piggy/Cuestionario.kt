package luna.barreras.gonzalez.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cuestionario.*

class Cuestionario : AppCompatActivity() {
    private lateinit var fondosEfectivo: EditText
    private lateinit var fondosDebito: EditText
    private lateinit var fondoscredito: EditText
    private lateinit var btngdcuestionario: Button
    private lateinit var progressBar4: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)

        fondosEfectivo=findViewById(R.id.fondosEfectivo)
        fondosDebito=findViewById(R.id.fondosDebito)
        fondoscredito=findViewById(R.id.fondoscredito)
        progressBar4=findViewById(R.id.progressBar4)
        btngdcuestionario= findViewById(R.id.btngdcuestionario)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

        btngdcuestionario.setOnClickListener{
            crearNuevosFondos()
        }

        btnfinalizarcuestionario.setOnClickListener{
            val intent= (Intent(this,LoginActivity::class.java))
            startActivity(intent)
        }
    }

    private fun ahorros(view: View){
        crearNuevosFondos()

    }

    private fun crearNuevosFondos() {
        val efectivo: Int = fondosEfectivo.text.toString().toInt()
        val debito: Int = fondosDebito.text.toString().toInt()
        val credito: Int = fondoscredito.text.toString().toInt()

        if (efectivo == 0 || debito == 0 || credito == 0) {
            Toast.makeText(this,"Inserte sus fondos para continuar", Toast.LENGTH_LONG).show()
        }else {
            try{
                val fref = FirebaseDatabase.getInstance().getReference("User")


                var fondos = Fondos(efectivo,debito,credito)

                fref.child(auth?.uid.toString()).setValue(fondos).addOnCompleteListener{
                    Toast.makeText(applicationContext, "Fondos Guardados", Toast.LENGTH_LONG).show()
                }

        }catch (e: Exception){
                Toast.makeText(this,"Error al conectarse a la base de datos", Toast.LENGTH_LONG).show()            }

        }
    }
    
}

