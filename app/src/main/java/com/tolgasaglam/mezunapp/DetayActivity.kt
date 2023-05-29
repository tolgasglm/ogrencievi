package com.tolgasaglam.mezunapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tolgasaglam.mezunapp.databinding.ActivityDetayBinding

class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        var adisoyadi = intent.getStringExtra("putadisoyadi")
        var durum = intent.getStringExtra("putdurum")
        var egitim = intent.getStringExtra("putegitim")
        var uzaklik = intent.getStringExtra("putuzaklik")
        var sure = intent.getStringExtra("putsure")
        var mail = intent.getStringExtra("putmail")
        var telefon = intent.getStringExtra("puttelefon")
        binding.detayadsoyad.text = adisoyadi.toString()
        binding.detaydurum.text = durum.toString()
        binding.detayegitim.text = egitim.toString()
        binding.detayuzaklik.text = uzaklik.toString()
        binding.detaysure.text = sure.toString()
        binding.detaymail.text = mail.toString()
        binding.detaytelefon.text = telefon.toString()

    }
}