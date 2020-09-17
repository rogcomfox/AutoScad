package com.nusantarian.autoscad.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.databinding.FragmentForgotBinding

class ForgotFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        ft = activity!!.supportFragmentManager.beginTransaction()
        auth = FirebaseAuth.getInstance()
        binding.btnReset.setOnClickListener(this)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        return binding.root
    }
    override fun onClick(v: View) {
        if (v.id == R.id.btn_reset){
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()

            if (!isValid(email)){
                binding.progress.visibility = View.GONE
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Silahkan Cek Email Anda", Toast.LENGTH_LONG).show()
                        ft.replace(R.id.frame_auth, LoginFragment())
                                .commit()
                    } else {
                        Toast.makeText(activity, "Gagal Mereset Password", Toast.LENGTH_LONG).show()
                    }
                    binding.progress.visibility = View.GONE
                }.addOnFailureListener {
                    Log.e("TAG", it.toString())
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun isValid(email : String) : Boolean{
        val empty = activity!!.resources.getString(R.string.text_empty_field)
        val invalid = activity!!.resources.getString(R.string.text_invalid_email)

        return if (email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled
            true
        }
    }

}