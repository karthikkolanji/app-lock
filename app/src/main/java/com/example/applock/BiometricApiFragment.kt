package com.example.applock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.biometric.*


class BiometricApiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.biometric, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBiomerticDeviceStatus()
        btAuthenticate.setOnClickListener {
            authenticateViaBiometricApi()
        }

    }


    private fun authenticateViaBiometricApi() {

        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    when (errorCode) {
                        BiometricConstants.ERROR_CANCELED -> {
                            tvStatusvalue.text = "ERROR_CANCELED"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }

                        BiometricConstants.ERROR_USER_CANCELED -> {
                            tvStatusvalue.text = "ERROR_USER_CANCELED"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_LOCKOUT -> {
                            tvStatusvalue.text = "Multiple attempts made, please wait"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                            Toast.makeText(
                                requireActivity(),
                                "Multiple attempts made, please wait",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


                        BiometricConstants.ERROR_HW_UNAVAILABLE -> {
                            tvStatusvalue.text = "ERROR_HW_UNAVAILABLE"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_UNABLE_TO_PROCESS -> {
                            tvStatusvalue.text = "ERROR_UNABLE_TO_PROCESS"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_TIMEOUT -> {
                            tvStatusvalue.text = "ERROR_TIMEOUT"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_NO_SPACE -> {
                            tvStatusvalue.text = "ERROR_NO_SPACE"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }

                        BiometricConstants.ERROR_VENDOR -> {
                            tvStatusvalue.text = "ERROR_VENDOR"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_LOCKOUT_PERMANENT -> {
                            tvStatusvalue.text = "ERROR_LOCKOUT_PERMANENT"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_NO_BIOMETRICS -> {
                            tvStatusvalue.text = "ERROR_NO_BIOMETRICS"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_HW_NOT_PRESENT -> {
                            tvStatusvalue.text = "ERROR_HW_NOT_PRESENT"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_NEGATIVE_BUTTON -> {
                            tvStatusvalue.text = "ERROR_NEGATIVE_BUTTON"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                        BiometricConstants.ERROR_NO_DEVICE_CREDENTIAL -> {
                            tvStatusvalue.text = "ERROR_NO_DEVICE_CREDENTIAL"
                            tvStatusvalue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    android.R.color.holo_red_dark
                                )
                            )
                        }
                    }

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    tvStatusvalue.text = "onAuthenticationSucceeded()"
                    tvStatusvalue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_dark
                        )
                    )
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    tvStatusvalue.text = "onAuthenticationFailed()"
                    tvStatusvalue.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_dark
                        )
                    )
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
        val biometricManager = BiometricManager.from(requireActivity())
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                tvDeviceStatusvalue.text = "BIOMETRIC HARDWARE AVAILABLE"
                tvDeviceStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_green_dark
                    )
                )
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_NO_HARDWARE"
                tvDeviceStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_red_dark
                    )
                )

            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_HW_UNAVAILABLE"
                tvDeviceStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_red_dark
                    )
                )
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                tvDeviceStatusvalue.text = "BIOMETRIC_ERROR_NONE_ENROLLED"
                tvDeviceStatusvalue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_red_dark
                    )
                )

            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BiometricApiFragment()
    }
}
