package luna.barreras.gonzalez.piggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_calculadora.*
import net.objecthunter.exp4j.ExpressionBuilder


class Calculadora : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        val button: Button = findViewById(R.id.button) as Button
        val button2: Button = findViewById(R.id.button2) as Button
        val button3: Button = findViewById(R.id.button3) as Button
        val button4: Button = findViewById(R.id.button4) as Button
        val button5: Button = findViewById(R.id.button5) as Button
        val button6: Button = findViewById(R.id.button6) as Button
        val button7: Button = findViewById(R.id.button7) as Button
        val button8: Button = findViewById(R.id.button8) as Button
        val button9: Button = findViewById(R.id.button9) as Button
        val button0: Button = findViewById(R.id.button0) as Button
        val btnplus: Button = findViewById(R.id.idplus) as Button

        val buttonsumarcantidad: Button = findViewById(R.id.buttonsumarcantidad) as Button
        val clearoperacion: Button = findViewById(R.id.clearoperacion) as Button

        val txOperacion: TextView = findViewById(R.id.idOperacion) as TextView
        val txResultado: TextView = findViewById(R.id.idResultado) as TextView





        fun agregarExpresion(string: String, valor: Boolean) {
            if (idResultado.text.isNotEmpty()) {
                idOperacion.text = ""
            }

            if (valor) {
                idResultado.text = ""
                idOperacion.append(string)
            } else {
                idOperacion.append(idResultado.text)
                idOperacion.append(string)
                idResultado.text = ""
            }

        }




        //numeros
        button.setOnClickListener{agregarExpresion("1",true)

        }

        button2.setOnClickListener { agregarExpresion("2",true)
        }

        button3.setOnClickListener { agregarExpresion("3",true)
        }

        button4.setOnClickListener { agregarExpresion("4",true)
        }
        button5.setOnClickListener { agregarExpresion("5",true)
        }
        button6.setOnClickListener { agregarExpresion("6",true)
        }
        button7.setOnClickListener { agregarExpresion("7",true)
        }
        button8.setOnClickListener { agregarExpresion("8",true)
        }
        button9.setOnClickListener { agregarExpresion("9",true)
        }
        button0.setOnClickListener { agregarExpresion("0",true)
        }

        //operaciones


        btnplus.setOnClickListener{ agregarExpresion("+",false)

        }


        //acciones
        buttonsumarcantidad.setOnClickListener {
            try {

                val operacion = ExpressionBuilder(idOperacion.text.toString()).build()
                val resultado = operacion.evaluate()
                val longResult = resultado.toLong()
                if(resultado == longResult.toDouble())
                    idResultado.text = longResult.toString()
                else
                    idResultado.text = resultado.toString()

            }catch (e:Exception){
                Log.d("Exception"," Mensaje : " + e.message )
            }
        }


        clearoperacion.setOnClickListener {
            txOperacion.setText("")
            txResultado.setText("")
        }



    }

}

