package com.gogit.Activties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gogit.R
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_log_out.*


class LogOut : AppCompatActivity() {
    private lateinit var dataSaver: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_log_out)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(this);

        Rela_Refuse.setOnClickListener(){
            finish()
        }
        Rela_Accept.setOnClickListener(){
            dataSaver.edit().putString("token", null).apply()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        }


    }
}
