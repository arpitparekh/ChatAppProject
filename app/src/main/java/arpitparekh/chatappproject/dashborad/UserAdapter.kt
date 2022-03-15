package arpitparekh.chatappproject.dashborad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arpitparekh.chatappproject.chat.ChatActivity
import arpitparekh.chatappproject.databinding.UserRowItemBinding
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context,val list: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = UserViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.binding.user = user

        holder.itemView.setOnClickListener {

            val i = Intent(context,ChatActivity::class.java)
            i.putExtra("name",user.name)
            i.putExtra("uid",user.uid)
            context.startActivity(i)

        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


}