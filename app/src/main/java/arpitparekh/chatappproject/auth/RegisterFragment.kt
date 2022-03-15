package arpitparekh.chatappproject.auth

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.dashborad.User
import arpitparekh.chatappproject.databinding.FragmentRegisterBinding
import arpitparekh.chatappproject.databinding.ProgressDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {

    lateinit var binding : FragmentRegisterBinding
    lateinit var auth : FirebaseAuth
    lateinit var ref : DatabaseReference
    lateinit var dialog : Dialog
    lateinit var dialogBinding: ProgressDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogBinding = ProgressDialogBinding.inflate(layoutInflater)
        dialogBinding.tvToastMessage.text = "Creating User..."

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().reference

        dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        activity?.title = "Register"

        binding.btnRegister.setOnClickListener {

            val name = binding.edtNameRegister.text.toString()
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()
            val repeatPassword = binding.edtRepeatPassword.text.toString()

            if(name.isNotEmpty() && password.isNotEmpty() && password == repeatPassword && password.length>=6 && password.length<=8){

                dialog.show()
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

                    if(it.isSuccessful){
                        addUserIntoADatabase(name,email,auth.currentUser?.uid!!)
                        Toast.makeText(context,"User Created",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                    }else{

                        Snackbar.make(binding.btnRegister,it.exception?.message.toString(),Snackbar.LENGTH_SHORT).show()
                    }

                }
            }else{

                Snackbar.make(binding.btnRegister,"Empty Filed or Password Does Not match or Password length is Wrong",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserIntoADatabase(name: String, email: String, uid: String) {

        ref.child("user").child(uid).setValue(User(name,email,uid))
    }
}