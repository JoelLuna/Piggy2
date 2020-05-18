package luna.barreras.gonzalez.piggy.ui.dashboard

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.cell_gastoshoy.*
import kotlinx.android.synthetic.main.cell_gastoshoy.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import luna.barreras.gonzalez.piggy.Categoria
import luna.barreras.gonzalez.piggy.R
import luna.barreras.gonzalez.piggy.R.*

import luna.barreras.gonzalez.piggy.ui.home.HomeFragment
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Manifest

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(layout.fragment_dashboard, container, false)

        root.btnsavecambios.setOnClickListener {
            guardar_categoria()
        }
        return root
    }

    fun guardar_categoria() {
        if (ContextCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this.requireActivity(),arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
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
                    Toast.makeText(requireContext(), "Error:permisos denegados", Toast.LENGTH_SHORT).show()
                }
        }
    }


    fun guardar() {
        var nombre = añadircategoria.text.toString()
        var pago = pagodecategoria.text.toString()
        var img = ArrayList<Int>()

        if (comidacheckbox.isChecked)
            img.add(R.drawable.comida)

        if (transportecheckbox.isChecked)
            img.add(R.drawable.transporte)

        if (regalocheckbox.isChecked)
            img.add(R.drawable.regalos)

        if (escuelacheckbox.isChecked)
            img.add(R.drawable.escuela)

        if (hogarcheckbox.isChecked)
            img.add(R.drawable.hogar)

        if (ejerciciocheckbox.isChecked)
            img.add(R.drawable.ejercicio)

        if (comprascheckbox.isChecked)
            img.add(R.drawable.compras)

        if (ropacheckbox.isChecked)
            img.add(R.drawable.ropa)

        if (entretenimientocheckbox.isChecked)
            img.add(R.drawable.entretenimiento)

        if (saludcheck.isChecked)
            img.add(R.drawable.salud)

        if (nombre == "" || pago == "") {
            Toast.makeText(requireContext(),"Se agrego la categoria", Toast.LENGTH_LONG).show()
        } else {
            try {
                val archivo = File(ubicacion(), nombre + ".txt")
                val fos = FileOutputStream(archivo)

                fos.write(pago.toByteArray())
                fos.close()
                Toast.makeText(requireContext(),"Se agrego el archivo en la carpeta", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(),"Error: No se guardo el archivo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun ubicacion(): String {
        val carpeta = File(Environment.getExternalStorageDirectory(), "registros")
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }


}
