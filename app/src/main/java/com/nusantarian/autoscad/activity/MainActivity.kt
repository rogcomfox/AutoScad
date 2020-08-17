package com.nusantarian.autoscad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.test.runner.AndroidJUnit4
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.autoscad.R
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.app_name)
        val drawer = findViewById<DrawerLayout?>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView?>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        findViewById<View?>(R.id.btn_mulai).setOnClickListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout?>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item.getItemId()) {
            R.id.nav_about -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
            R.id.nav_settings -> Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
            R.id.nav_help -> Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem?): Boolean {
        when (item.getItemId()) {
            R.id.nav_profile -> {
            }
            R.id.nav_history -> {
            }
            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Yakin Ingin Mengeluarkan Akun?")
                builder.setPositiveButton("Ya") { dialogInterface, i ->
                    mAuth.signOut()
                    startActivity(Intent(this@MainActivity, LandingActivity::class.java))
                    finish()
                }
                builder.setNegativeButton("Tidak") { dialogInterface, i -> }
                builder.show()
            }
        }
        val drawer = findViewById<DrawerLayout?>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        if (v.getId() == R.id.btn_mulai) {
            startActivity(Intent(this@MainActivity, ScanActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}