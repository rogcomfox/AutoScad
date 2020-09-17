package com.nusantarian.autoscad.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.autoscad.R
import com.nusantarian.autoscad.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction
    private lateinit var user: DocumentReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        user = FirebaseFirestore.getInstance().collection("users")
                .document(auth.currentUser?.uid!!)
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.editprofile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_change_email ->
                Toast.makeText(context, "Change Email", Toast.LENGTH_LONG).show()
            R.id.nav_change_pass ->
                Toast.makeText(context, "Change Password", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}