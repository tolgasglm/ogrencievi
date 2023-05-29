package com.tolgasaglam.mezunapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.tolgasaglam.mezunapp.databinding.ActivityUserListBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.appcompat.widget.SearchView
import java.util.Locale

class UserList : AppCompatActivity() {
    lateinit var binding: ActivityUserListBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Kullanici>
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userRecyclerView = binding.userlist

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Kullanici>()
        customAdapter = CustomAdapter(userArrayList, this)
        userRecyclerView.adapter = customAdapter

        getUserData()

        binding.buttonkullaniciekle.setOnClickListener {
            val intent = Intent(this, RecyclerViewActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.profilegeridon.setOnClickListener {
            intent = Intent(applicationContext,MainProfil::class.java)
            startActivity(intent)
            finish()
        }

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList<Kullanici>()

                if (newText.isNullOrEmpty()) {
                    filteredList.addAll(getAllUsers()) // Tüm kullanıcıları tekrar listeye ekle
                } else {
                    val searchQuery = newText.toLowerCase(Locale.getDefault())
                    filteredList.addAll(getAllUsers().filter { user ->
                        user.adi.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                                user.durum.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                                user.egitim.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                                user.uzaklik.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                                user.sure.toLowerCase(Locale.getDefault()).contains(searchQuery)
                    })
                }

                customAdapter.submitList(filteredList)
                return true
            }
        })
    }

    private fun getAllUsers(): List<Kullanici> {
        return userArrayList.toList()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Users")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Kullanici::class.java)
                        userArrayList.add(user!!)
                    }
                    customAdapter.submitList(ArrayList(userArrayList))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }
}
