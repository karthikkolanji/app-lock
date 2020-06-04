package com.example.applock

import android.app.Activity
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.keyguard_manager.*
import org.joda.time.DateTime

class KeyguardManagerApiFragment : Fragment() {


    private var putToBackground = false
    private lateinit var onViewCreatedCalledTime: DateTime
    private lateinit var onStopCalledTime: DateTime
    private val minTimeDifference = 2000 // 2 seconds
    private var isFullScreenLock = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.keyguard_manager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedCalledTime = DateTime.now()

       btAuthenticate.setOnClickListener {
           authenticateViaKeyguardManagerAPi()
       }
    }

    override fun onStop() {
        super.onStop()

        onStopCalledTime = DateTime.now()

        val difference = onStopCalledTime.millis - onViewCreatedCalledTime.millis

        isFullScreenLock = difference <= minTimeDifference

        putToBackground = true

    }

    private fun authenticateViaKeyguardManagerAPi() {
        if (isKeyguardEnabled(requireActivity())) {
            val keyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            val keyguardIntent = keyguardManager.createConfirmDeviceCredentialIntent(
                "Keyguard Manager API",
                "Confirm Lock"
            )
            try {
                startActivityForResult(keyguardIntent, AUTHENTICATE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireActivity(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } else {
            val intent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
            startActivityForResult(intent, DEVICE_LOCK_SETUP_SCREEN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DEVICE_LOCK_SETUP_SCREEN) {
            val isDeviceSecured = isKeyguardEnabled(requireActivity())

            if (isDeviceSecured != null && isDeviceSecured) {
                authenticateViaKeyguardManagerAPi()
            } else {
                tvStatusvalue.text = "Please setup security"
                tvStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_red_dark
                    )
                )
            }
        }

        if (requestCode == AUTHENTICATE) {
            if (resultCode == Activity.RESULT_OK) {
                tvStatusvalue.text = "SUCCESS"
                tvStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_green_dark
                    )
                )
            } else {

                if (isFullScreenLock) {
                    tvStatusvalue.text = "FAILED"
                    tvStatusvalue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_red_dark
                        )
                    )

                } else {
                    if (putToBackground) {
                        authenticateViaKeyguardManagerAPi()
                    } else {
                        tvStatusvalue.text = "FAILED"
                        tvStatusvalue.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.holo_red_dark
                            )
                        )
                    }
                    putToBackground = false
                }
            }
        }
    }

    private fun isKeyguardEnabled(activity: FragmentActivity): Boolean {

        val keyguardManager = activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager.isDeviceSecure
        } else {
            keyguardManager.isKeyguardSecure
        }
    }

    companion object {
        const val DEVICE_LOCK_SETUP_SCREEN = 1445
        const val AUTHENTICATE = 1446

        fun newInstance() =
            KeyguardManagerApiFragment()
    }
}
