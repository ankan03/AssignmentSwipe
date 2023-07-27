package com.example.assignment_ankan.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.assignment_ankan.R
import com.example.assignment_ankan.ui.fragment.GetProductFragment
import com.example.assignment_ankan.ui.fragment.NetworkUnavailableFragment
import com.example.assignment_ankan.utill.checkNetworkState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        if(checkNetworkState(this)){
            val fragment = GetProductFragment.newInstance()
            val fragmentManager:FragmentManager = supportFragmentManager
            val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.container,fragment)
            fragmentTransaction.commit()
        }else{
            val fragment = NetworkUnavailableFragment()
            val fragmentManager:FragmentManager = supportFragmentManager
            val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.container,fragment)
            fragmentTransaction.commit()
        }
    }
}