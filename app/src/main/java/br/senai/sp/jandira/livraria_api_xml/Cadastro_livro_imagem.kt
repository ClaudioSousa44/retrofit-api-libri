package br.senai.sp.jandira.livraria_api_xml

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Cadastro_livro_imagem : AppCompatActivity() {

    //Atributos para manipulaçao de imagens (objetos de uri)
    private var imageUriGRD: Uri? = null
    private var imageUriPEQ: Uri? = null

    //Atriubuto para acesso e manipulaçao do storage
    private lateinit var storageRef: StorageReference

    private lateinit var fibaseFirebaseStorage: FirebaseFirestore

    private var btnImgGRD: ImageView? = null
    private var btnImgPEQ: ImageView? = null

    private var btnUpload: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_livro_imagem)
        initVars()

        val body = intent.getStringExtra("body")

        btnImgGRD = findViewById<ImageView>(R.id.imgGRD)
        btnImgPEQ = findViewById<ImageView>(R.id.imgPEQ)

        btnUpload = findViewById<Button>(R.id.btnCadastrarLivro)

        btnImgGRD?.setOnClickListener{
            resultLauncherGRD.launch("image/*")
        }
        btnImgPEQ?.setOnClickListener{
            resultLauncherPEQ.launch("image/*")
        }

        btnUpload?.setOnClickListener{
            upload()
        }
    }

    // inicializacao dos atributos
    private fun initVars(){
        storageRef = FirebaseStorage.getInstance().reference.child("images")
        fibaseFirebaseStorage = FirebaseFirestore.getInstance()
    }

    private val resultLauncherGRD = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUriGRD = it
        btnImgGRD?.setImageURI(it)
    }

    private val resultLauncherPEQ = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUriPEQ = it
        btnImgPEQ?.setImageURI(it)
    }


    private fun upload() {
        imageUriGRD?.let {
            val riversRef =
                storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        fibaseFirebaseStorage.collection("images").add(map)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "UPLOAD IMAGEM GRANDE OK!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        firestoreTask.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                btnImgGRD?.setImageResource(R.drawable.upload)
                            }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgGRD?.setImageResource(R.drawable.upload)
                }
            }
        }


        imageUriPEQ?.let {
            val riversRef =
                storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
            val uploadTask = riversRef.putFile(it)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    riversRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()
                        fibaseFirebaseStorage.collection("images").add(map)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "UPLOAD IMAGEM PEQUENA OK!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        firestoreTask.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                btnImgPEQ?.setImageResource(R.drawable.upload)
                            }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    btnImgPEQ?.setImageResource(R.drawable.upload)
                }
            }
        }
    }
}

