package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tolgasaglam.mezunapp.databinding.ActivityMainGirisBinding

class MainGiris : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding : ActivityMainGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        /*if(currentUser!=null){
            startActivity(Intent(this@MainGiris,MainProfil::class.java))
            finish()
        }
        */
        binding.girisgirisyap.setOnClickListener {
            var girisemail = binding.girisemail.text.toString()
            var girissifre = binding.girissifre.text.toString()
            if(TextUtils.isEmpty(girisemail)){
                binding.girisemail.error = "Email adresinizi giriniz"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(girissifre)){
                binding.girissifre.error = "Şifrenizi giriniz"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(girisemail,girissifre)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        intent = Intent(applicationContext,MainProfil::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"Giriş Hatalı",Toast.LENGTH_LONG)
                    }
                }

        }

        binding.giriskayitol.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.girissifremiunuttum.setOnClickListener {
            intent = Intent(applicationContext,MainSifre::class.java)
            startActivity(intent)
            finish()
        }






    }
}