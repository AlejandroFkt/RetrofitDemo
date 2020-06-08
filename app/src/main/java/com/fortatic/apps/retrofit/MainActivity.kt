package com.fortatic.apps.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetPostById.setOnClickListener {
            getPostById(3)
        }
        btnGetAllPosts.setOnClickListener {
            getAllPosts()
        }
        btnGetAllUsers.setOnClickListener {
            getAllUsers()
        }
        btnPostPost.setOnClickListener {
            postPost()
        }
    }

    private fun getPostById(postId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            //Mostramos un mensaje de espera al usuario.
            tvResult.text = getString(R.string.wait_text)
            val postReceived = try {
                //Llamamos al servicio.
                Api.retrofitService.getPostById(postId)
            } catch (error: Exception) {
                //Retornamos un mensaje de error en caso de que el servicio falle.
                getString(R.string.error_text, error.message)
            }
            //Mostramos el resultado obtenido, ya sea exitoso o erroneo.
            tvResult.text = postReceived.toString()
        }
    }

    private fun getAllPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            //Mostramos un mensaje de espera al usuario.
            tvResult.text = getString(R.string.wait_text)
            val postsReceived = try {
                //Llamamos al servicio.
                Api.retrofitService.getAllPosts()
            } catch (error: Exception) {
                //Retornamos un mensaje de error en caso de que el servicio falle.
                getString(R.string.error_text, error.message)
            }
            //Mostramos el resultado obtenido, ya sea exitoso o erroneo.
            tvResult.text = postsReceived.toString()
        }
    }

    private fun getAllUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            //Mostramos un mensaje de espera al usuario.
            tvResult.text = getString(R.string.wait_text)
            val usersReceived = try {
                //Llamamos al servicio.
                Api.retrofitService.getAllUsers()
            } catch (error: Exception) {
                //Retornamos un mensaje de error en caso de que el servicio falle.
                getString(R.string.error_text, error.message)
            }
            //Mostramos el resultado obtenido, ya sea exitoso o erroneo.
            tvResult.text = usersReceived.toString()
        }
    }

    private fun postPost() {
        //Creamos el objeto a postear.
        val post = Post(userId = 1, id = 1, title = "Posteando ando", body = "Posteando desde AS.")
        CoroutineScope(Dispatchers.IO).launch {
            //Mostramos un mensaje de espera al usuario.
            tvResult.text = getString(R.string.wait_text)
            //Llamamos al servicio en un bloque try/catch por seguridad.
            val postResponse: Response<Post> = try {
                Api.retrofitService.postPost(post)
            } catch (error: Exception) {
                //Si ocurre un error, mostramos el mensaje y salimos
                tvResult.text = getString(R.string.error_text, error.message)
                return@launch
            }
            //Verificamos que la respuesta sea exitosa.
            if (postResponse.isSuccessful) {
                val responseCode = postResponse.code()
                val responseBody = postResponse.body()
                tvResult.text =
                    getString(R.string.successful_text, responseCode, responseBody.toString())
            } else {
                tvResult.text =
                    getString(R.string.error_text, postResponse.message())
            }
        }
    }
}
