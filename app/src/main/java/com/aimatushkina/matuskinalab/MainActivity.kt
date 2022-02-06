package com.aimatushkina.matuskinalab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.aimatushkina.matuskinalab.models.ImgModel
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.aimatushkina.matuskinalab.databinding.ActivityMainBinding
import com.aimatushkina.matuskinalab.room.DataBasePreferences
import okhttp3.internal.wait


class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://developerslife.ru/random?json=true"
    private lateinit var binding: ActivityMainBinding
    lateinit var db: DataBasePreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DataBasePreferences(this)
        var counter = 1
        fetchJson(BASE_URL)
        runOnUiThread{
        donlowder(counter)}


        binding.nextButton.setOnClickListener {
            binding.backButton.isVisible=true
            binding.backButton.isClickable=true
            counter++
            fetchJson(BASE_URL)
            donlowder(counter)

        }
        binding.backButton.setOnClickListener {
            if(counter>1) {
                counter--
                donlowder(counter)
            }
            if(counter==1){
                binding.backButton.isVisible=false
                binding.backButton.isClickable=false
            }

        }
    }

    fun donlowder(counter:Int){
        val imgP = binding.imageView1
        println(counter)
        Glide.with(this)
//                .asGif()
            .load(db.getURL(counter))
            .into(imgP)
        binding.picText.setText(db.getDescr(counter))
    }

    fun fetchJson(url:String) {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, ImgModel::class.java)
                db.addJSONObject(homeFeed)
            }
        })
    }
}

