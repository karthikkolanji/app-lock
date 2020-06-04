package com.example.applock

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentTransactionUtil private constructor() {

    init {
        throw AssertionError()
    }

    companion object {

        fun replaceFragment(fragmentManager: FragmentManager,
                            fragment: Fragment,
                            @IdRes fragmentContainerId: Int,
                            addToBackStack: Boolean) {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(fragmentContainerId, fragment)
            if (addToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commit()
        }
    }
}