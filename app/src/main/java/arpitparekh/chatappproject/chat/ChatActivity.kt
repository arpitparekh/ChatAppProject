package arpitparekh.chatappproject.chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    lateinit var binding : ActivityChatBinding
    lateinit var list : ArrayList<Message>
    lateinit var adapter: ChatAdapter
    lateinit var ref : DatabaseReference

    var senderRoom : String? =null
    var receiverRoom : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        ref = FirebaseDatabase.getInstance().reference

        adapter = ChatAdapter(this,list)



        val intent = intent
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid+senderUid
        receiverRoom = senderUid+receiverUid

        title = name

        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)


        binding.recyclerViewChat.adapter = adapter

        ref.child("chat").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    list.clear()
                    for(childSnap in snapshot.children){

                        val msg = childSnap.getValue(Message::class.java)

                        list.add(msg!!)

                    }

                    adapter.notifyDataSetChanged()
                    binding.recyclerViewChat.smoothScrollToPosition(adapter.itemCount);

                }
                override fun onCancelled(error: DatabaseError) {

                    Log.i("error",error.toString())

                }


            })

        binding.btnSendMessage.setOnClickListener {

            val msg = binding.edtMessage.text.toString()

            if(msg.isNotEmpty()){

                val msgObject = Message(msg,senderUid)

                ref.child("chat").child(senderRoom!!).child("messages").push()
                    .setValue(msgObject).addOnSuccessListener {

                        ref.child("chat").child(receiverRoom!!).child("messages").push()
                            .setValue(msgObject).addOnFailureListener {
                                Log.i("error",it.toString())
                            }

                    }
                    .addOnFailureListener {

                        Log.i("error",it.toString())
                    }

                binding.edtMessage.setText("")

            }
        }

    }
}