package com.example.callscreenapp.permission

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat

class Permission {
    companion object {
        const val PERMISSION_PHONE_CALL_CODE = 1
        const val PERMISSION_STORAGE_CODE = 2
        const val PERMISSION_CONTACTS_CODE = 3
        const val REQUEST_CODE_SET_DEFAULT_DIALER = 4
    }

}