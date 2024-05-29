package com.example.callscreenapp.ui.fragment.premission

import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telecom.TelecomManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.callscreenapp.Impl.WriteSettingsPermissionListener
import com.example.callscreenapp.R
import com.example.callscreenapp.adapter.PhoneCallListImageAdapter
import com.example.callscreenapp.data.ListCategoryAll
import com.example.callscreenapp.data.ListCategoryAnimal
import com.example.callscreenapp.data.ListCategoryAnime
import com.example.callscreenapp.data.ListCategoryCastle
import com.example.callscreenapp.data.ListCategoryFantasy
import com.example.callscreenapp.data.ListCategoryGame
import com.example.callscreenapp.data.ListCategoryLove
import com.example.callscreenapp.data.ListCategoryNature
import com.example.callscreenapp.data.ListCategorySea
import com.example.callscreenapp.data.ListCategoryTech
import com.example.callscreenapp.permission.hasWriteSettingsPermission
import com.example.callscreenapp.permission.isDefaultDialer
import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.activity.ShowImageActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial

@Suppress("DEPRECATION")
class PremissionSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = PremissionSheetFragment()

        const val WRITE_SETTINGS_PERMISSION_REQUEST_CODE = 1002
    }
    private var storeSubscription: (() -> Unit)? = null
    private var writeSettingsPermissionListener: WriteSettingsPermissionListener? = null
    private val viewModel: PremissionSheetViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    private val dialerRequestLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                Log.d("requestDefaultDialerCheck", "1")
                setDialerPermission(requireView())
                checkAllPermission(requireView())
            } else {
                Log.d("requestDefaultDialerCheck", "2")
                setDialerPermission(requireView())
                checkAllPermission(requireView())
            }
        }

    private val writeSettingsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (Settings.System.canWrite(requireContext())) {
                setWriteSettingsPermission(requireView())
                checkAllPermission(requireView())
            } else {
                setWriteSettingsPermission(requireView())
                checkAllPermission(requireView())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_premission_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDefaultDialer = isDefaultDialer(requireContext())
        var isWriteSettingsPermission = hasWriteSettingsPermission(requireContext())

        val permissionDefaultDialer: SwitchMaterial =
            view.findViewById(R.id.permission_phone_default)
        val permissionWriteSettingsPermission: SwitchMaterial =
            view.findViewById(R.id.permission_system_setting)
        val permissionAll: SwitchMaterial = view.findViewById(R.id.permission_all)

        permissionAll.isChecked = isDefaultDialer && isWriteSettingsPermission

        permissionDefaultDialer.isChecked = isDefaultDialer
        permissionWriteSettingsPermission.isChecked = isWriteSettingsPermission

        permissionDefaultDialer.setOnClickListener {
            if (!isDefaultDialer) {
                requestDefaultDialer(requireContext())
            }
        }

        permissionWriteSettingsPermission.setOnClickListener {
            if (!isWriteSettingsPermission) {
                requestWriteSettingsPermission(requireActivity())
                isWriteSettingsPermission = hasWriteSettingsPermission(requireContext())
                permissionWriteSettingsPermission.isChecked = isWriteSettingsPermission
            }
        }

        permissionAll.setOnClickListener {
            setAllPermission(view)
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setDialerPermission(view: View) {
        val permissionDefaultDialer: SwitchMaterial = view.findViewById(R.id.permission_phone_default)
        val isDefaultDialer = isDefaultDialer(requireContext())
        permissionDefaultDialer.isChecked = isDefaultDialer
    }

    private fun setWriteSettingsPermission(view: View) {
        val permissionWriteSettingsPermission: SwitchMaterial =
            view.findViewById(R.id.permission_system_setting)
        val isWriteSettingsPermission = hasWriteSettingsPermission(requireContext())
        permissionWriteSettingsPermission.isChecked = isWriteSettingsPermission
    }

    private fun checkAllPermission(view: View) {
        val permissionDefaultDialer: SwitchMaterial = view.findViewById(R.id.permission_phone_default)
        val permissionWriteSettingsPermission: SwitchMaterial =
            view.findViewById(R.id.permission_system_setting)
        val permissionAll: SwitchMaterial = view.findViewById(R.id.permission_all)
        permissionAll.isChecked = permissionDefaultDialer.isChecked && permissionWriteSettingsPermission.isChecked

        if (permissionAll.isChecked) {
            val intent = Intent(context, ShowImageActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setAllPermission(view: View) {
        val permissionAll: SwitchMaterial = view.findViewById(R.id.permission_all)
        requestDefaultDialer(requireContext())
        requestWriteSettingsPermission(requireActivity())
        val permissionDefaultDialer: SwitchMaterial =
            view.findViewById(R.id.permission_phone_default)
        val permissionWriteSettingsPermission: SwitchMaterial =
            view.findViewById(R.id.permission_system_setting)
        permissionAll.isChecked = permissionDefaultDialer.isChecked && permissionWriteSettingsPermission.isChecked
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestDefaultDialer(context: Context) {
        context.let { ctx ->
            val telecomManager =
                ctx.getSystemService(Context.TELECOM_SERVICE) as? TelecomManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val roleManager = ctx.getSystemService(Context.ROLE_SERVICE) as? RoleManager
                roleManager?.let {
                    val intent = it.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                    dialerRequestLauncher.launch(intent)
                }
            } else {
                Log.d("requestDefaultDialerCheck", "2")
                val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).apply {
                    putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        ctx.packageName
                    )
                }
                ctx.startActivity(intent)
            }
        }
    }


    private fun requestWriteSettingsPermission(activity: Activity) {
        if (!Settings.System.canWrite(activity.applicationContext)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            writeSettingsPermissionLauncher.launch(intent)
        }
    }

    fun FragmentActivity.showCustomBottomSheet() {
        PremissionSheetFragment().show(this.supportFragmentManager, PremissionSheetFragment::class.java.simpleName)
    }
}
