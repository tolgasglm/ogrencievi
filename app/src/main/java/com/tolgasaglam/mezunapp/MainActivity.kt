package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tolgasaglam.mezunapp.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference =database?.reference!!.child("profil")



        binding.kayitkaydol.setOnClickListener{
            var kulad = binding.kayitad.text.toString()
            var kulsoyad = binding.kayitsoyad.text.toString()
            var kulgirisyil = binding.kayitgirisyil.text.toString().toInt()
            var kulmezunyil = binding.kayitmezunyil.text.toString().toInt()
            var kuleposta = binding.kayiteposta.text.toString()
            var kulsifre = binding.kayitsifre.text.toString()
            if(TextUtils.isEmpty(kulad)){
                binding.kayitad.error = "Adınızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(kulsoyad)){
                binding.kayitsoyad.error = "Soyadınızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(kulgirisyil.toString())){
                binding.kayitgirisyil.error = "Giriş yılınızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(kulmezunyil.toString())){
                binding.kayitmezunyil.error = "Mezun yılınızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(kuleposta)){
                binding.kayiteposta.error = "E-posta adresinizi boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(kulsifre)){
                binding.kayitsifre.error = "Şifrenizi boş bırakamazsınız"
                return@setOnClickListener
            }

            auth.setLanguageCode("tr")
            auth.useAppLanguage()

            val emailPattern = Regex("[a-zA-Z0-9._-]+@std\\.yildiz\\.edu\\.tr")

            if (!emailPattern.matches(binding.kayiteposta.text.toString())) {
                // E-posta adresi istenen formata uymuyor
                Toast.makeText(this@MainActivity, "Geçerli bir @std.yildiz.edu.tr e-posta adresi girin.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(binding.kayiteposta.text.toString(),binding.kayitsifre.text.toString())
                .addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        var currentUser = auth.currentUser
                        var currentUserDb = currentUser?.let { it1->databaseReference?.child(it1.uid) }
                        currentUserDb?.child("adı")?.setValue(binding.kayitad.text.toString())
                        currentUserDb?.child("soyadı")?.setValue(binding.kayitsoyad.text.toString())
                        currentUserDb?.child("girisyili")?.setValue(binding.kayitgirisyil.text.toString())
                        currentUserDb?.child("mezunyili")?.setValue(binding.kayitmezunyil.text.toString())


                        currentUser?.sendEmailVerification()?.addOnCompleteListener(this) { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Toast.makeText(this@MainActivity, "Doğrulama e-postası gönderildi. Lütfen e-postanızı kontrol edin.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@MainActivity, "Doğrulama e-postası gönderilemedi.", Toast.LENGTH_LONG).show()
                            }

                        }

                        Toast.makeText(this@MainActivity, "Kayıt Başarılı",Toast.LENGTH_LONG).show()
                        intent = Intent(applicationContext,MainGiris::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Toast.makeText(this@MainActivity, "Kayıt Başarısız",Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.kayitgirisyap.setOnClickListener {
            intent = Intent(applicationContext,MainGiris::class.java)
            startActivity(intent)
            finish()
        }
    }
}



