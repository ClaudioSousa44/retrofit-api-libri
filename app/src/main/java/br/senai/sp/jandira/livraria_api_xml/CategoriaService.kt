package br.senai.sp.jandira.livraria_api_xml

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoriaService {

    @POST("/categoria/cadastrarCategoria")
    suspend fun criarCategoria(@Body body: JsonObject): Response<JsonObject>
}