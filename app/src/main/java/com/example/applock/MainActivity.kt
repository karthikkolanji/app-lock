package com.example.applock

import android.app.Activity
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btBiometricApi.setOnClickListener {

            replaceFragment(BiometricApiFragment.newInstance(), R.id.fragment_container, true)
        }

        btKeyguardManager.setOnClickListener {
            replaceFragment(KeyguardManagerApiFragment.newInstance(), R.id.fragment_container, true)
        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        @IdRes fragmentContainer: Int,
        addToBackStack: Boolean
    ) {

        FragmentTransactionUtil.replaceFragment(
            supportFragmentManager,
            fragment,
            fragmentContainer,
            addToBackStack
        )

    }
}
