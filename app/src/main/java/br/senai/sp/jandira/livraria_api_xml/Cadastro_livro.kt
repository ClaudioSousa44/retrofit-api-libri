package br.senai.sp.jandira.livraria_api_xml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.gson.JsonObject

class Cadastro_livro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_livro)

        //Recuperando os elementos de view
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtPreco = findViewById<EditText>(R.id.txtPreco)
        val txtDescricao = findViewById<EditText>(R.id.txtLivroDescricao)
        val txtCategoria = findViewById<EditText>(R.id.txtCategoria)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrarLivro)

        //Tratamento de ação de clique no botao
        btnCadastrar.setOnClickListener{

            val titulo = txtTitulo.text.toString()
            val preco = txtPreco.text.toString()
            val categoria = txtCategoria.text.toString()
            val descricao = txtDescricao.text.toString()

            val body = JsonObject().apply {
                addProperty("titulo", titulo)
                addProperty("preco", preco)
                addProperty("categoria", categoria)
                addProperty("descricao", descricao)
            }

            //Navegaçao com dados
            val intent = Intent(this, Cadastro_livro_imagem::class.java).apply {
                putExtra("body", body.toString())
            }

            startActivity(intent)

        }

    }
}