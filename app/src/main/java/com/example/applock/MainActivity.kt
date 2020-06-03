package com.example.applock

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBiomerticDeviceStatus()


        btBiometricApi.setOnClickListener {
            authenticateViaBiometricApi()
        }

        btKeyguardManager.setOnClickListener {
            authenticateViaKeyguardManagerAPi()
        }
    }

    fun isKeyguardEnabled(activity: AppCompatActivity): Boolean {

        val keyguardManager = activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager.isDeviceSecure
        } else {
            keyguardManager.isKeyguardSecure
        }
    }

    private fun authenticateViaBiometricApi() {

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                when (errorCode) {
                    BiometricConstants.ERROR_CANCELED -> {
                        tvStatusvalue.text = "ERROR_CANCELED"
                    }

                    BiometricConstants.ERROR_USER_CANCELED -> {
                        tvStatusvalue.text = "ERROR_USER_CANCELED"
                    }
                    BiometricConstants.ERROR_LOCKOUT -> {
                        tvStatusvalue.text = "ERROR_LOCKOUT"
                    }


                    BiometricConstants.ERROR_HW_UNAVAILABLE -> {
                        tvStatusvalue.text = "ERROR_HW_UNAVAILABLE"
                    }
                    BiometricConstants.ERROR_UNABLE_TO_PROCESS -> {
                        tvStatusvalue.text = "ERROR_UNABLE_TO_PROCESS"
                    }
                    BiometricConstants.ERROR_TIMEOUT -> {
                        tvStatusvalue.text = "ERROR_TIMEOUT"
                    }
                    BiometricConstants.ERROR_NO_SPACE -> {
                        tvStatusvalue.text = "ERROR_NO_SPACE"
                    }

                    BiometricConstants.ERROR_VENDOR -> {
                        tvStatusvalue.text = "ERROR_VENDOR"
                    }
                    BiometricConstants.ERROR_LOCKOUT_PERMANENT -> {
                        tvStatusvalue.text = "ERROR_LOCKOUT_PERMANENT"
                    }
                    BiometricConstants.ERROR_NO_BIOMETRICS -> {
                        tvStatusvalue.text = "ERROR_NO_BIOMETRICS"
                    }
                    BiometricConstants.ERROR_HW_NOT_PRESENT -> {
                        tvStatusvalue.text = "ERROR_HW_NOT_PRESENT"
                    }
                    BiometricConstants.ERROR_NEGATIVE_BUTTON -> {
                        tvStatusvalue.text = "ERROR_NEGATIVE_BUTTON"
                    }
                    BiometricConstants.ERROR_NO_DEVICE_CREDENTIAL -> {
                        tvStatusvalue.text = "ERROR_NO_DEVICE_CREDENTIAL"
                    }
                }

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                tvStatusvalue.text = "onAuthenticationSucceeded()"
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                tvStatusvalue.text = "onAuthenticationFailed()"
            }

        })


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric API")
                .setSubtitle("Testing Biometric API")
                .setDescription("Biometric API Description")
                .setConfirmationRequired(false)
                .setDeviceCredentialAllowed(true)
                .build()

        biometricPrompt.authenticate(promptInfo)

    }

    private fun initBiomerticDeviceStatus() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                tvDeviceStatusvalue.text = "BIOMETRIC_SUCCESS"
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_NO_HARDWARE"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_HW_UNAVAILABLE"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_NONE_ENROLLED"

        }
    }

    private fun authenticateViaKeyguardManagerAPi() {

    }
}
