package br.senai.sp.jandira.livraria_api_xml

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var categoriaService: CategoriaService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoriaService = RetrofitHelper.getInstance().create(CategoriaService::class.java)

        val txtCategoria = findViewById<EditText>(R.id.txtCategoria)

        findViewById<Button>(R.id.btnCadastrarCategoria).setOnClickListener{
            val nomeCategoria = txtCategoria.text
            cadastrarCategoria(nomeCategoria.toString())


        }

    }

    private fun cadastrarCategoria(nomeCategoria:String){
        lifecycleScope.launch {
            val body = JsonObject().apply {
                addProperty("nome_categoria", nomeCategoria)
            }

            val result = categoriaService.criarCategoria(body)

            if(result.isSuccessful){
                val msg = result.body()?.get("mensagemStatus")
                Log.e("CREAT-DATA", "${result.body()}")
            }else{
                Log.e("CREAT-DATA", result.message())
            }
        }
    }


}