package arpitparekh.chatappproject.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arpitparekh.chatappproject.databinding.ReceiveBinding
import arpitparekh.chatappproject.databinding.SentBinding
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(val context: Context,val list : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2

    class SentViewHolder(val binding: SentBinding) : RecyclerView.ViewHolder(binding.root)

    class ReceiveViewHolder(val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){

            val binding : SentBinding = SentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            val viewHolder = SentViewHolder(binding)
            return viewHolder

        }else{

            val binding : ReceiveBinding = ReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            val viewHolder = ReceiveViewHolder(binding)
            return viewHolder

        }
    }

    override fun getItemViewType(position: Int): Int {

        val msg = list[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(msg.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val msg = list[position]

        if(holder.javaClass==SentViewHolder::class.java){


            val viewHolder = holder as SentViewHolder
            viewHolder.binding.tvSend.text = msg.message


        }else{

            val viewHolder = holder as ReceiveViewHolder
            viewHolder.binding.tvReceive.text = msg.message

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}