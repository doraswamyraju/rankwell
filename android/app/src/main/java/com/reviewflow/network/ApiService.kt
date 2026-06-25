package com.reviewflow.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiService {
    private const val BASE_URL = "http://10.0.2.2:5000/api" // Emulator local IP mapping
    private var token: String? = null

    fun setToken(newToken: String?) {
        token = newToken
    }

    suspend fun register(name: String, email: String, password: String): JSONObject = withContext(Dispatchers.IO) {
        val url = URL("$BASE_URL/auth/register")
        val payload = JSONObject().apply {
            put("name", name)
            put("email", email)
            put("password", password)
        }
        val response = makePostRequest(url, payload, useAuth = false)
        if (response.has("token")) {
            token = response.getString("token")
        }
        response
    }

    suspend fun login(email: String, password: String): JSONObject = withContext(Dispatchers.IO) {
        val url = URL("$BASE_URL/auth/login")
        val payload = JSONObject().apply {
            put("email", email)
            put("password", password)
        }
        val response = makePostRequest(url, payload, useAuth = false)
        if (response.has("token")) {
            token = response.getString("token")
        }
        response
    }

    suspend fun previewBusinessLink(mapsUrl: String): JSONObject = withContext(Dispatchers.IO) {
        val url = URL("$BASE_URL/businesses/preview-link")
        val payload = JSONObject().apply {
            put("googleMapsUrl", mapsUrl)
        }
        makePostRequest(url, payload, useAuth = true)
    }

    suspend fun onboardBusiness(name: String, address: String, mapsUrl: String): JSONObject = withContext(Dispatchers.IO) {
        val url = URL("$BASE_URL/businesses")
        val payload = JSONObject().apply {
            put("name", name)
            put("address", address)
            put("googleMapsUrl", mapsUrl)
        }
        makePostRequest(url, payload, useAuth = true)
    }

    private fun makePostRequest(url: URL, payload: JSONObject, useAuth: Boolean): JSONObject {
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true

            if (useAuth && token != null) {
                connection.setRequestProperty("Authorization", "Bearer $token")
            }

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(payload.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val responseString = stream.bufferedReader().use { it.readText() }
            return JSONObject(responseString)
        } finally {
            connection.disconnect()
        }
    }
}
