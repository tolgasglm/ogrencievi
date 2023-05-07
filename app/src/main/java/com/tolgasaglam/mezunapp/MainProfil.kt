package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tolgasaglam.mezunapp.databinding.ActivityMainProfilBinding

class MainProfil : AppCompatActivity() {
    lateinit var binding: ActivityMainProfilBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference =database?.reference!!.child("profil")

        var currentUser = auth.currentUser
        binding.profileposta.text = currentUser?.email

        var userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilad.text = "İsim:"+ snapshot.child("adı").value.toString()
                binding.profilgirisyil.text = "Giriş Yılı:"+ snapshot.child("girisyili").value.toString()
                binding.profilmezunyil.text = "Mezun Yılı:"+ snapshot.child("mezunyili").value.toString()
                binding.profilsoyad.text = "Soyisim:"+ snapshot.child("soyadı").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.profilcikisyap.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@MainProfil,MainGiris::class.java))
            finish()
        }

        binding.profilhesabisil.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız Silindi",Toast.LENGTH_LONG)
                    auth.signOut()
                    startActivity(Intent(this@MainProfil,MainGiris::class.java))
                    finish()
                }
            }
        }
    }
}