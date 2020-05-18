package luna.barreras.gonzalez.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bienvenida.*
import kotlinx.android.synthetic.main.activity_savings_register.*

class Bienvenida : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        btncomenzarahora.setOnClickListener {
                val i = Intent(this, Cuestionario::class.java)
                startActivity(i)
            }
        }
    }

