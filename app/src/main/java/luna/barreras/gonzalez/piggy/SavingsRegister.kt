package luna.barreras.gonzalez.piggy

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.trimmedLength
import androidx.navigation.navArgs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_savings_register.*
import kotlinx.android.synthetic.main.activity_savings_register.view.*
import kotlinx.android.synthetic.main.deseo_view.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import luna.barreras.gonzalez.piggy.R
import luna.barreras.gonzalez.piggy.ui.notifications.NotificationsFragment
import java.io.File
import java.io.FileOutputStream

class SavingsRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings_register)

        btndeseo.setOnClickListener {
            guardar_deseo()
        }
    }
        fun guardar_deseo() {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    235
                )
            } else {
                guardar()
            }
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            when (requestCode) {
                235 ->
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                        guardar()
                    else {
                        Toast.makeText(this, "Error:permisos denegados", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        fun guardar() {
            var deseo = textdeseo.text.toString()
            var cantidad = cantidadahorrar.text.toString()
            var contribucionD = contribucion.text.toString()

            if (deseo == "" || cantidad == null ||contribucion == null ) {
                Toast.makeText(this,"Se agrego el Deseo", Toast.LENGTH_LONG).show()
            } else {
                try {
                    val archivo = File(ubicacion(), deseo + ".txt")
                    val fos = FileOutputStream(archivo)

                    fos.write(cantidad.toByteArray())
                    fos.close()

                    fos.write(contribucionD.toByteArray())
                    fos.close()

                    Toast.makeText(this,"Se agrego el archivo en la carpeta", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this,"Error: No se guardo el archivo", Toast.LENGTH_LONG).show()
                }
            }
        }

        private fun ubicacion(): String {
            val carpeta = File(Environment.getExternalStorageDirectory(), "deseos")
            if (!carpeta.exists()) {
                carpeta.mkdir()
            }
            return carpeta.absolutePath
        }



        /*btndeseo.setOnClickListener {

            var deseo = textdeseo.text.toString()
            var cantidad = cantidadahorrar.text.toString().toInt()
            var contribuciondiaria = contribucion.text.toString().toInt()


            var mideseo = Mideseo(
                deseo,
                cantidad,
                contribuciondiaria
            )

            NotificationsFragment.deseoList.add(mideseo)



            Toast.makeText(this, "Deseo Agregado", LENGTH_LONG).show()
        }
    }*/
    }

