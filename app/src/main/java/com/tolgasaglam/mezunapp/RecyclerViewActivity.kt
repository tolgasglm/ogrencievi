package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.tolgasaglam.mezunapp.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etkaydet.setOnClickListener{
            var etadsoyad = binding.etadsoyad.text.toString().trim()
            var etdurum = binding.etdurum.text.toString().trim()
            var etegitimbilgisi = binding.etegitimbilgisi.text.toString().trim()
            var etuzaklik = binding.etuzaklik.text.toString().trim()
            var etsure = binding.etsure.text.toString().trim()
            var etmail = binding.etmail.text.toString().trim()
            var ettelefon = binding.ettelefon.text.toString().trim()
            if(TextUtils.isEmpty(etadsoyad)){
                binding.etadsoyad.error = "Adınızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(etdurum)){
                binding.etdurum.error = "Durum bilgisini boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(etegitimbilgisi.toString())){
                binding.etegitimbilgisi.error = "Eğitim bilgisini boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(etuzaklik.toString())){
                binding.etuzaklik.error = "Uzaklık bilgisini boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(etsure)){
                binding.etsure.error = "Süre bilgisini boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(etmail)){
                binding.etmail.error = "E-posta adresinizi boş bırakamazsınız"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ettelefon)){
                binding.ettelefon.error = "Telefon numaranızı boş bırakamazsınız"
                return@setOnClickListener
            }
            else{
                var database = FirebaseDatabase.getInstance()
                var databaseReference = database.reference.child("Users")
                var id = databaseReference.push()
                id.child("id").setValue(id.key.toString())
                id.child("adi")?.setValue(binding.etadsoyad.text.toString())
                id.child("durum")?.setValue(binding.etdurum.text.toString())
                id.child("egitim")?.setValue(binding.etegitimbilgisi.text.toString())
                id.child("uzaklik")?.setValue(binding.etuzaklik.text.toString())
                id.child("sure")?.setValue(binding.etsure.text.toString())
                id.child("mail")?.setValue(binding.etmail.text.toString())
                id.child("telefon")?.setValue(binding.ettelefon.text.toString())
                Toast.makeText(applicationContext,"Kayıt Başarılı",Toast.LENGTH_LONG).show()
            }
        }
        binding.etkayitlar.setOnClickListener {
            val intent = Intent(this,UserList::class.java)
            startActivity(intent)
            finish()
        }
    }
}