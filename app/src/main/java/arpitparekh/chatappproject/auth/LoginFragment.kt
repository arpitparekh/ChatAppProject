package arpitparekh.chatappproject.auth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import arpitparekh.chatappproject.dashborad.DashBoardActivity
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.databinding.FragmentLoginBinding
import arpitparekh.chatappproject.databinding.ProgressDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding
    lateinit var dialogBinding : ProgressDialogBinding
    lateinit var auth : FirebaseAuth
    lateinit var dialog : Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogBinding = ProgressDialogBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        activity?.title = "Login"

        if(auth.currentUser?.uid!=null){

            startActivity(Intent(context, DashBoardActivity::class.java))

        }

        binding.btnLogin.setOnClickListener {
            dialogBinding.tvToastMessage.text = "Logging You In..."

            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                dialog.show()
                auth.signInWithEmailAndPassword(binding.edtEmailLogin.text.toString(),binding.edtPasswordLogin.text.toString()).addOnCompleteListener {

                    if(it.isSuccessful){

                        dialog.dismiss()

                        startActivity(Intent(context, DashBoardActivity::class.java))

                    }else{

                        Snackbar.make(binding.btnLogin,it.exception?.message.toString(),Snackbar.LENGTH_SHORT).show()
                    }
                }
            }else{

                Snackbar.make(it,"Fill Empty Field",Snackbar.LENGTH_SHORT).show()

            }

        }

        binding.btnRegister.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnForgetPasword.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }
    }

}