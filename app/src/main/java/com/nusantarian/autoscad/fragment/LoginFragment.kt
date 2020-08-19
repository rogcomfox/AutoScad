package com.nusantarian.autoscad.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.activity.MainActivity
import com.nusantarian.autoscad.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.tvLupa.setOnClickListener(this)
        ft = activity!!.supportFragmentManager.beginTransaction()
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login ->
                initLogin()
            R.id.tv_lupa ->
                ft.replace(R.id.frame_auth, ForgotFragment())
                        .addToBackStack(null)
                        .commit()
            R.id.btn_signup ->
                ft.replace(R.id.frame_auth, RegisterFragment())
                        .addToBackStack(null)
                        .commit()
        }
    }

    private fun initLogin() {
        binding.progress.visibility = View.VISIBLE
        val email = binding.tilEmail.editText?.text.toString()
        val pass = binding.tilPassword.editText?.text.toString()
        if (!isUserValid(email, pass)) {
            binding.progress.visibility = View.GONE
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                if (it.isSuccessful){
                    startActivity(Intent(activity, MainActivity::class.java))
                    Toast.makeText(activity, "Welcome to AutoScad!", Toast.LENGTH_LONG).show()
                    activity!!.finish()
                } else {
                    Toast.makeText(activity, "Gagal Memasukkan Akun Anda", Toast.LENGTH_LONG).show()
                }
                binding.progress.visibility = View.GONE
            }.addOnFailureListener{
                Log.e("TAG", it.toString())
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                binding.progress.visibility = View.GONE
            }
        }
    }

    private fun isUserValid(email: String, pass: String): Boolean {
        val empty = activity!!.resources.getString(R.string.text_empty_field)
        val invalid = activity!!.resources.getString(R.string.text_invalid_email)

        return if (email.isEmpty()) {
            binding.tilEmail.error = empty
            false
        } else if (pass.isEmpty()) {
            binding.tilPassword.error = empty
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilPassword.error = null
            binding.tilEmail.isErrorEnabled
            binding.tilPassword.isErrorEnabled
            true
        }
    }
}