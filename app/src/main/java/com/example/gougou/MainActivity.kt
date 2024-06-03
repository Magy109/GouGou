package com.example.gougou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    val DURACION:Long=2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val logo=findViewById<ImageView>(R.id.imageView)
        Glide.with(this).load(R.drawable.logo_api).into(logo)
        cambiar()

    }


    private fun cambiar(){
        Handler().postDelayed(Runnable {
            finish()
            val intent= Intent(this,menu::class.java)
            startActivity(intent)
        },DURACION)



    }

}