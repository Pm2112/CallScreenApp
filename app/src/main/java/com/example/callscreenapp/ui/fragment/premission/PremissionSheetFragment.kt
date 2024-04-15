package com.example.callscreenapp.ui.fragment.premission

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.callscreenapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PremissionSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = PremissionSheetFragment()
        const val PERMISSION_PHONE_CALL_CODE = 1
        const val PERMISSION_STORAGE_CODE = 2
        const val PERMISSION_CONTACTS_CODE = 3
    }

    private val viewModel: PremissionSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_premission_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.permission_sheet_phone_call).setOnClickListener {
            checkAndRequestPermission(Manifest.permission.CALL_PHONE, PERMISSION_PHONE_CALL_CODE)
        }

        view.findViewById<CardView>(R.id.permission_sheet_phone_storage).setOnClickListener {
            checkAndRequestPermission(Manifest.permission.READ_CONTACTS, PERMISSION_CONTACTS_CODE)
        }

        view.findViewById<CardView>(R.id.permission_sheet_call_screen).setOnClickListener {

        }
    }

    private fun checkAndRequestPermission(permission: String, requestCode: Int) {
        context?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(permission), requestCode)
            } else {
                onPermissionGranted(requestCode)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted(requestCode)
        } else {
            Toast.makeText(
                context,
                "Permission denied for ${permissions.contentToString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onPermissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSION_PHONE_CALL_CODE -> makePhoneCall()
            PERMISSION_STORAGE_CODE -> handleStorageAccess()
            PERMISSION_CONTACTS_CODE -> handleContactsAccess()
        }
    }

    private fun makePhoneCall() {
        // Code to initiate a phone call
    }

    private fun handleStorageAccess() {
        // Code to handle storage access
    }

    private fun handleContactsAccess() {
        // Code to handle contacts access
    }
}
