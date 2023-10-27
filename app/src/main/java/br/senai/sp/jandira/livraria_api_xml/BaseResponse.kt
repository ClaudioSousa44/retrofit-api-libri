package br.senai.sp.jandira.livraria_api_xml

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    var data: T? = null

)
