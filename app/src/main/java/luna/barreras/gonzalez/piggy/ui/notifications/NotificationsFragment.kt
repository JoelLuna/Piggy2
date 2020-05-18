package luna.barreras.gonzalez.piggy.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_calculadora.*
import kotlinx.android.synthetic.main.activity_calculadora.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_savings_register.*
import kotlinx.android.synthetic.main.deseo_view.*

import kotlinx.android.synthetic.main.deseo_view.view.*
import kotlinx.android.synthetic.main.deseo_view.view.cantidad
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import luna.barreras.gonzalez.piggy.*
import luna.barreras.gonzalez.piggy.ui.home.HomeFragment
import java.io.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    companion object {

        var adaptador: DeseoAdapter? = null
        var deseoList = ArrayList<Mideseo>()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        root.añadirmeta.setOnClickListener {
            var intent: Intent = Intent(this.activity, SavingsRegister::class.java)
            startActivity(intent)
        }
        root.agregarahorro.setOnClickListener {
            var intent: Intent = Intent(this.activity, Calculadora::class.java)
            startActivity(intent)
        }
        adaptador = DeseoAdapter(root.context, deseoList)
        root.listmideseo.adapter = adaptador

        leerDeseos()

        return root
        metadinero
        var progress = 0
        var progessfinal = metadinero.toString().toInt()
        var progressInicio = dinerocontribu.toString().toInt()

        root.buttonsumarcantidad.setOnClickListener{
            if(progressInicio < progessfinal){
                var progessInicio = progressInicio
                progessfinal + progessInicio

                progressBar.progress = progessfinal
            }else{
                progessfinal = 0

                progresBar.progress = progessfinal

                progresBar.visibility = View.VISIBLE
            }

            val progessInicio = progressInicio
            if(progessInicio == progessfinal)progressBar.visibility = View.INVISIBLE


        }


    }


    private fun ubicacion(): File {
        val folder = File(Environment.getExternalStorageDirectory(), "deseos")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }

    fun leerDeseos(){
        deseoList.clear()
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
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""
        var contribucionD = ""

        while(strLine != null){
            myData = myData + strLine
            contribucionD = contribucionD + strLine
            strLine = br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        var deseo =archivo.name.substring(0,archivo.name.length -4)

        var deseoLt = Mideseo(deseo, myData,contribucionD)

        deseoList.add(deseoLt)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            leerDeseos()
            adaptador?.notifyDataSetChanged()
        }
    }

    class DeseoAdapter: BaseAdapter{
        var deseoList= ArrayList<Mideseo>()
        var context: Context? = null

        constructor(context: Context, deseoList:ArrayList<Mideseo>){
            this.context = context
            this.deseoList = deseoList
        }

        override fun getCount(): Int {
            return deseoList.size
        }

        override fun getItem(position: Int): Any {
            return deseoList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val deseo = this.deseoList[p0]
            var inflator = LayoutInflater.from(context)
            var vista = inflator.inflate(R.layout.deseo_view, null)

            vista.mideseo.setText(deseo.deseo)
            vista.cantidad.setText(deseo.cantidad)
            vista.dinerocontribu.setText(deseo.contribucionD)
            vista.cantidad2.setText(deseo.cantidad)
            vista.metadinero.setText(deseo.contribucionD)


            return vista
        }

        private fun ubicacion(): String{
            val album = File(Environment.getExternalStorageDirectory(), "deseos")
            if(!album.exists()){
                album.mkdir()
            }
            return album.absolutePath
        }
    }

    }



