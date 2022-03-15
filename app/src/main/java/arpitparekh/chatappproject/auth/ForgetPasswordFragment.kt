package arpitparekh.chatappproject.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import arpitparekh.chatappproject.R
import arpitparekh.chatappproject.databinding.FragmentForgetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class ForgetPasswordFragment : Fragment() {

    lateinit var binding : FragmentForgetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Forget Password"

        binding.btnRecoverPassword.setOnClickListener {

            val email = binding.edtemailForget.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {

                if(it.isSuccessful){

                    Snackbar.make(binding.btnRecoverPassword,"Reset Password",Snackbar.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment)

                }

            }
        }
    }

}