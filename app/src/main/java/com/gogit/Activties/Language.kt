package com.gogit.Activties

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.gogit.R
import kotlinx.android.synthetic.main.activity_language.*

class Language : AppCompatActivity() {
    internal lateinit var share: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_language)
    Lan_Arabic()
    Lan_English()
    Lan_Herb()


    }


    private fun Lan_Herb() {
        Rela_Herb.setOnClickListener(View.OnClickListener {
            share = getSharedPreferences("Language", MODE_PRIVATE).edit()
            share.putString("Lann", "he")
            share.commit()
            startActivity(Intent(this@Language, Navigation::class.java))
            finish()
        })

    }

    fun Lan_Arabic() {
        Rela_Arabic.setOnClickListener(View.OnClickListener {
            share = getSharedPreferences("Language", MODE_PRIVATE).edit()
            share.putString("Lann", "ar")
            share.commit()
            startActivity(Intent(this@Language, Navigation::class.java))
            finish()
        })
    }

    fun Lan_English() {
        Rela_English.setOnClickListener(View.OnClickListener {
            share = getSharedPreferences("Language", MODE_PRIVATE).edit()
            share.putString("Lann", "en")
            share.commit()
            startActivity(Intent(this@Language, Navigation::class.java))
            finish()
        })

    }

}
