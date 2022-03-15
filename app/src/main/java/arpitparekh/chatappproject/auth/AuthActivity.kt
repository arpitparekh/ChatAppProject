package arpitparekh.chatappproject.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding : ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}