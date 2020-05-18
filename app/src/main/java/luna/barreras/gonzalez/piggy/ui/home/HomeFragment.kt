package luna.barreras.gonzalez.piggy.ui.home

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.cell_gastoshoy.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import luna.barreras.gonzalez.piggy.Categoria
import luna.barreras.gonzalez.piggy.R
import java.io.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var adaptador: AdaptadorCategorias?=null
    var categorias = ArrayList<Categoria>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        leerCategorias()

        adaptador=AdaptadorCategorias(root.context, categorias)
            root.gastosdehoy.adapter = adaptador


        return root

    }
    fun leerCategorias(){
        categorias.clear()
        var carpeta = File(ubicacion().absolutePath)

        if(carpeta.exists()){
            var archivos= carpeta.listFiles()
            if(archivos != null){
                for(archivo in archivos){
                    leerArchivo(archivo)
                }
            }
        }

    }

    fun leerArchivo(archivo: File){
        val fis =FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData= ""
        var img = 0

        while(strLine != null){
            myData = myData + strLine
            strLine = br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        var nombre =archivo.name.substring(0,archivo.name.length -4)

        var categoria = Categoria(nombre,myData)

        categorias.add(categoria)
    }

    private fun ubicacion(): File {
        val folder = File(Environment.getExternalStorageDirectory(), "registros")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            leerCategorias()
            adaptador?.notifyDataSetChanged()
        }
    }

    class AdaptadorCategorias: BaseAdapter{
        var categorias=ArrayList<Categoria>()
        var contexto: Context?=null

        constructor(contexto: Context, categorias:ArrayList<Categoria>){
            this.contexto=contexto
            this.categorias=categorias
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var categoria=categorias[p0]
            var inflador=LayoutInflater.from(contexto)
            var vista=inflador.inflate(R.layout.cell_gastoshoy, null)

            //vista.gastos_iconos.setImageLevel(categoria.img)
            vista.gastos_categoria.setText(categoria.nombre)
            vista.gastos_cantidad.setText(categoria.pago)


            vista.btnborrar.setOnClickListener {
                eliminar(categoria.nombre)
                categorias.remove(categoria)
                this.notifyDataSetChanged()
            }

            return vista
        }

        override fun getItem(position: Int): Any{
            return categorias[position]
        }

        override fun getItemId(position: Int): Long{
            return position.toLong()
        }

        override fun getCount():Int{
            return categorias.size
        }

        private fun eliminar(nombre: String) {
            if (nombre == "") {
                Toast.makeText(contexto, "Error: titulo vacio", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val archivo = File(ubicacion(), nombre + ".txt")
                    archivo.delete()

                    Toast.makeText(contexto, "Se eliminó el archivo", Toast.LENGTH_LONG).show()
                }

                catch (e: Exception) {
                    Toast.makeText(contexto, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun ubicacion(): String{
            val album = File(Environment.getExternalStorageDirectory(), "registros")
            if(!album.exists()){
                album.mkdir()
            }
            return album.absolutePath
        }
    }

}




