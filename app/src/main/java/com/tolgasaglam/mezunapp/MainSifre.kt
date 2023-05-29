package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tolgasaglam.mezunapp.databinding.ActivitySifreBinding

class MainSifre : AppCompatActivity() {
    lateinit var binding: ActivitySifreBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySifreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.sifresifirlabuton.setOnClickListener {
            var sifreemail = binding.sifremail.text.toString().trim()
            if(TextUtils.isEmpty(sifreemail)){
                binding.sifremail.error = "Lütfen e-postanızı yazınız"
            }
            else{
                auth.sendPasswordResetEmail(sifreemail)
                    .addOnCompleteListener(this){sifirlama->
                        if(sifirlama.isSuccessful){
                            binding.sifresifremiunuttum.text = "E-postanıza sıfırlama bağlantısı gönderildi"
                        }
                        else{
                            binding.sifresifremiunuttum.text = "Şifre sıfırlama başarısız"
                        }

                    }
            }
        }
        binding.sifregirisyap.setOnClickListener {
            intent = Intent(applicationContext,MainGiris::class.java)
            startActivity(intent)
            finish()
        }
    }
}