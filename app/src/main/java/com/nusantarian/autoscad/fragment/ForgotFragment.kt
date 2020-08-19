package com.nusantarian.autoscad.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.databinding.FragmentForgotBinding

class ForgotFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener(this)
        ft = activity!!.supportFragmentManager.beginTransaction()
        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_back ->
                ft.replace(R.id.frame_auth, LoginFragment())
                        .addToBackStack(null)
                        .commit()
        }
    }

}