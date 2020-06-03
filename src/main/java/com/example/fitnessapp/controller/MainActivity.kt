package com.example.fitnessapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnessapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//        btnStart.setOnClickListener {
//
//            startTimer(pauseOffset)
//        }
//        btnPause.setOnClickListener{
////            pauseTimer()
//        }
//        llStart.setOnClickListener {
//            Toast.makeText(
//                this,
//                "Type text",
//                Toast.LENGTH_SHORT
//            ).show()
        // Um ein neues Event anzufangen! Es wird uns zum naechsten Aktivitaet weiterleiten, wenn wir das Button llStart druecken
        idStarten.setOnClickListener {
            val intent = Intent (this, UebungenActivity::class.java)
            startActivity(intent)       // Das wird den neuen Bildschirm oeffnen
        }
    }
}
