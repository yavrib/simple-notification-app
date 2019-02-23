package com.example.notifyme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("http://www.ebookfrenzy.com"))

        startActivity(intent)
    }
}