package com.nusantarian.autoscad.fragment

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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.databinding.FragmentRegisterBinding
import com.nusantarian.autoscad.model.User

class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction
    private lateinit var users: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        users = FirebaseFirestore.getInstance().collection("users")
        ft = activity!!.supportFragmentManager.beginTransaction()
        binding.btnSignup.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_signup) {
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()
            val name = binding.tilName.editText?.text.toString()
            val phone = binding.tilPhone.editText?.text.toString()
            val pass = binding.tilPassword.editText?.text.toString()
            val confirm = binding.tilConfpass.editText?.text.toString()

            if (!isDataValid(email, pass, name, phone, confirm)) {
                binding.progress.visibility = View.GONE
            } else {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val id = auth.currentUser?.uid
                        pushData(id!!, email, name, phone)
                    } else {
                        Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG).show()
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

    private fun pushData(id : String, email : String, name : String, phone : String){
        val user = User(email, name, phone)
        users.document(id).set(user).addOnCompleteListener {
            if (it.isSuccessful){
                ft.replace(R.id.frame_auth, LoginFragment())
                        .commit()
                Toast.makeText(activity, "Silahkan Login Terlebih Dahulu", Toast.LENGTH_LONG).show()
            } else {
                Log.e("pushData", "Failed Push Data")
                Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            Log.e("pushData", it.toString())
        }
    }

    private fun isDataValid(email: String, pass: String, name: String, phone: String, confirm: String): Boolean {
        val empty = activity!!.resources.getString(R.string.text_empty_field)
        val invalid = activity!!.resources.getString(R.string.text_invalid_email)
        val match = activity!!.resources.getString(R.string.text_not_match)

        return if (email.isEmpty()) {
            binding.tilEmail.error = empty
            false
        } else if (pass.isEmpty()) {
            binding.tilPassword.error = empty
            false
        } else if (name.isEmpty()) {
            binding.tilName.error = empty
            false
        } else if (phone.isEmpty()) {
            binding.tilPhone.error = empty
            false
        } else if (pass != confirm) {
            binding.tilPassword.error = match
            binding.tilConfpass.error = match
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilPassword.error = null
            binding.tilPhone.error = null
            binding.tilName.error = null
            binding.tilConfpass.error = null

            binding.tilEmail.isErrorEnabled
            binding.tilPassword.isErrorEnabled
            binding.tilName.isErrorEnabled
            binding.tilPhone.isErrorEnabled
            binding.tilConfpass.isErrorEnabled

            true
        }
    }
}