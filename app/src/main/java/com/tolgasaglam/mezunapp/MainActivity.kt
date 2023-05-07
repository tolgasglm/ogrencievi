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

            auth.createUserWithEmailAndPassword(binding.kayiteposta.text.toString(),binding.kayitsifre.text.toString())
                .addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        var currentUser = auth.currentUser
                        var currentUserDb = currentUser?.let { it1->databaseReference?.child(it1.uid) }
                        currentUserDb?.child("adı")?.setValue(binding.kayitad.text.toString())
                        currentUserDb?.child("soyadı")?.setValue(binding.kayitsoyad.text.toString())
                        currentUserDb?.child("girisyili")?.setValue(binding.kayitgirisyil.text.toString())
                        currentUserDb?.child("mezunyili")?.setValue(binding.kayitmezunyil.text.toString())
                        Toast.makeText(this@MainActivity, "Kayıt Başarılı",Toast.LENGTH_LONG).show()
                        intent = Intent(applicationContext,MainGiris::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Toast.makeText(this@MainActivity, "Kayıt Başarısız",Toast.LENGTH_LONG).show()

                    }


                }
            //database.setValue(Kullanici(kulad,kulsoyad,kulgirisyil, kulmezunyil, kuleposta, kulsifre))
            //database.child(kuleposta).setValue(Kullanici(kulad,kulsoyad,kulgirisyil, kulmezunyil, kulsifre))
        }

        binding.kayitgirisyap.setOnClickListener {
            intent = Intent(applicationContext,MainGiris::class.java)
            startActivity(intent)
            finish()
        }

/*

        var getData = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children) {
                    var kulad = i.child("kulad").getValue()
                    var kulsoyad = i.child("kulsoyad").getValue()
                    var kulgirisyil = i.child("kulgirisyil").getValue()
                    var kulmezunyil = i.child("kulmezunyil").getValue()
                    var kulsifre = i.child("kulsifre").getValue()
                    sb.append("${i.key} $kulad \n $kulsoyad \n $kulgirisyil \n $kulmezunyil \n $kulsifre")

                }
                binding.kullanicibilgiler.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }

        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)
*/

    }
}



