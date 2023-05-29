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


        //var currentUser = auth.currentUser

        binding.girisgirisyap.setOnClickListener {
            val girisemail = binding.girisemail.text.toString()
            val girissifre = binding.girissifre.text.toString()
            if(TextUtils.isEmpty(girisemail)){
                binding.girisemail.error = "Email adresinizi giriniz"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(girissifre)){
                binding.girissifre.error = "Şifrenizi giriniz"
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(girisemail,girissifre)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        if(user != null && user.isEmailVerified){
                            intent = Intent(applicationContext, MainProfil::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "E-posta adresiniz doğrulanmamış.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Giriş Hatalı", Toast.LENGTH_LONG).show()
                    }
                }

        }

        binding.giriskayitol.setOnClickListener {
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.girissifremiunuttum.setOnClickListener {
            intent = Intent(applicationContext,MainSifre::class.java)//MainSifreRecyclerViewActivity
            startActivity(intent)
            finish()
        }

    }
}