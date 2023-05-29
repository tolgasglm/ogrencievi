package com.tolgasaglam.mezunapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class CustomAdapter(private val userList: ArrayList<Kullanici>, val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var originalList: ArrayList<Kullanici> = ArrayList(userList)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvadsoyad:TextView = view.findViewById(R.id.cvadsoyad)
        val cvdurum:TextView = view.findViewById(R.id.cvdurum)
        val cvegitim:TextView = view.findViewById(R.id.cvegitim)
        val cvuzaklik:TextView = view.findViewById(R.id.cvuzaklik)
        val cvsure:TextView = view.findViewById(R.id.cvsure)
        val cvmail:TextView = view.findViewById(R.id.cvmail)
        val cvtelefon:TextView = view.findViewById(R.id.cvtelefon)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.useritemlist, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cvadsoyad.text = userList[position].adi
        viewHolder.cvdurum.text = userList[position].durum
        viewHolder.cvegitim.text = userList[position].egitim
        viewHolder.cvuzaklik.text = userList[position].uzaklik
        viewHolder.cvsure.text = userList[position].sure
        viewHolder.cvmail.text = userList[position].mail
        viewHolder.cvtelefon.text = userList[position].telefon

        viewHolder.itemView.setOnClickListener {
            var user = userList[position]
            var adisoyadi:String? = user.adi
            var durum:String? = user.durum
            var egitim:String? = user.egitim
            var uzaklik:String? = user.uzaklik
            var sure:String? = user.sure
            var mail:String? = user.mail
            var telefon:String? = user.telefon

            var intent = Intent(context,DetayActivity::class.java)
            intent.putExtra("putadisoyadi",adisoyadi)
            intent.putExtra("putdurum",durum)
            intent.putExtra("putegitim",egitim)
            intent.putExtra("putuzaklik",uzaklik)
            intent.putExtra("putsure",sure)
            intent.putExtra("putmail",mail)
            intent.putExtra("puttelefon",telefon)

            context.startActivity(intent)
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList.size

    fun submitList(filteredList: List<Kullanici>) {
        userList.clear()
        userList.addAll(filteredList)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val searchQuery = query.toLowerCase(Locale.getDefault())
        val filteredList = if (searchQuery.isEmpty()) {
            originalList
        } else {
            originalList.filter { user ->
                user.adi.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                        user.durum.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                        user.egitim.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                        user.uzaklik.toLowerCase(Locale.getDefault()).contains(searchQuery) ||
                        user.sure.toLowerCase(Locale.getDefault()).contains(searchQuery)
            }
        }
        userList.clear()
        userList.addAll(filteredList)
        notifyDataSetChanged()
    }
}





