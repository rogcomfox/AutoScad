package com.nusantarian.autoscad.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.databinding.ActivityLoginBinding
import com.nusantarian.autoscad.fragment.LoginFragment

class LoginActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var ft: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ft = supportFragmentManager.beginTransaction()

        supportFragmentManager.addOnBackStackChangedListener(this)
        mainFragment()
    }

    private fun mainFragment(){
        ft.add(R.id.frame_auth, LoginFragment())
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    override fun onBackStackChanged() {

    }
}