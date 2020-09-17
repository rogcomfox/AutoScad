package com.nusantarian.autoscad.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.activity.LoginActivity
import com.nusantarian.autoscad.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout ->
                raiseAlertDialog()
            R.id.nav_edit_profile ->
                ft.replace(R.id.frame_main, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun raiseAlertDialog() {
        val builder = MaterialAlertDialogBuilder(context!!)
        builder.setTitle(R.string.keluar)
        builder.setMessage(R.string.message_logout)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity!!.finish()
        }
        builder.setNegativeButton(R.string.no) { _, _ ->

        }
        builder.show()
    }
}