package com.example.a7minworkout

import android.content.Intent
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding.flStart.setOnClickListener {
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding.flBmi.setOnClickListener {
            val intent=Intent(this,BmiActivity::class.java)
            startActivity(intent)
        }
        binding.flHistory.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}