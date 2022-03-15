package arpitparekh.chatappproject.dashborad

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.auth.AuthActivity
import arpitparekh.chatappproject.databinding.ActivityDashBoardBinding
import arpitparekh.chatappproject.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DashBoardActivity : AppCompatActivity() {

    lateinit var binding : ActivityDashBoardBinding
    lateinit var dialogBinding : ProgressDialogBinding
    lateinit var dialog : Dialog
    lateinit var list : ArrayList<User>
    lateinit var adapter : UserAdapter
    lateinit var auth : FirebaseAuth
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashBoardBinding.inflate(layoutInflater)
        dialogBinding = ProgressDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.tvToastMessage.text = "Fetching Users";
        dialog.setCancelable(false)
        dialog.show()

        auth = FirebaseAuth.getInstance()
        list = ArrayList()
        binding.recyclerViewUser.layoutManager=LinearLayoutManager(this)
        ref = FirebaseDatabase.getInstance().reference

        adapter = UserAdapter(this,list)

        binding.recyclerViewUser.adapter = adapter

        ref.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(userSnap in snapshot.children){

                    val currentUser = userSnap.getValue(User::class.java)

                    if(auth.currentUser?.uid != currentUser?.uid){
                        list.add(currentUser!!)
                    }
                }
                dialog.dismiss()
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("error",error.toString())
            }


        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_logout){

            auth.signOut()
            startActivity(Intent(this,AuthActivity::class.java))
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(FirebaseAuth.getInstance().currentUser?.uid!=null){

        }else{
            super.onBackPressed()
        }

    }
}