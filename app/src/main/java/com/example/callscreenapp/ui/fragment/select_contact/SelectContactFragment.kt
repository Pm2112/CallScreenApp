package com.example.callscreenapp.ui.fragment.select_contact

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.callscreenapp.R
import com.example.callscreenapp.data.realm
import com.example.callscreenapp.database.BackgroundTheme
import com.example.callscreenapp.database.ContactDb
import com.example.callscreenapp.model.Contact
import com.example.callscreenapp.process.downloadImageAndSave
import com.example.callscreenapp.redux.store.store
import com.example.callscreenapp.ui.activity.ContactActivity
import com.example.callscreenapp.ui.activity.NotificationActivity
import io.realm.kotlin.ext.query

class SelectContactFragment : Fragment() {

    private var contactList = mutableListOf<Contact>()
    companion object {
        fun newInstance() = SelectContactFragment()
    }

    private val viewModel: SelectContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_select_contact, container, false)
        val overlayView: View = rootView.findViewById(R.id.fragment_select_contact_overlay)
        val btnAllContact: CardView =
            rootView.findViewById(R.id.fragment_select_contact_all_contact_card)
        val btnSpecificContact: CardView =
            rootView.findViewById(R.id.fragment_select_contact_specific_contact_card_view)
        val iconAllContact: ImageView =
            rootView.findViewById(R.id.fragment_select_contact_all_contact_icon)
        val iconSpecificContact: ImageView =
            rootView.findViewById(R.id.fragment_select_contact_specific_contact_icon)
        val btnSubmit: ImageView = rootView.findViewById(R.id.fragment_select_contact_btn_submit)

        fetchContacts(false)

        overlayView.setOnClickListener {
            // Ẩn fragment khi click vào overlay
            requireActivity().supportFragmentManager.beginTransaction().hide(this).commit()
        }
        iconAllContact.isSelected = true
        btnAllContact.setOnClickListener() {
            iconAllContact.isSelected = true
            iconSpecificContact.isSelected = false
        }
        btnSpecificContact.setOnClickListener() {
            iconSpecificContact.isSelected = true
            iconAllContact.isSelected = false
        }
        btnSubmit.setOnClickListener() {
            if (iconAllContact.isSelected) {
                val urlAvatar = store.state.avatarUrl
                val urlItem = store.state.backgroundUrl
                val urlBtnCallGreen = store.state.iconCallShowGreen
                val urlBtnCallRed = store.state.iconCallShowRed

                Log.d("urlItem", urlItem)

                downloadImageAndSave(requireContext(), urlItem, "background_theme")
                downloadImageAndSave(requireContext(), urlAvatar, "avatar_theme")
                downloadImageAndSave(requireContext(), urlBtnCallGreen, "icon_answer")
                downloadImageAndSave(requireContext(), urlBtnCallRed, "icon_reject")

                realm.writeBlocking {
                    val query = query<ContactDb>().find()
                    delete(query)
                    contactList.forEach { contact ->
                        copyToRealm(ContactDb().apply {
                            contactPhone = contact.phoneNumber
                            contactName = contact.name
                            avatarTheme = "avatar_theme"
                            backgroundTheme = "background_theme"
                            btnAnswer = "icon_answer"
                            btnReject = "icon_reject"
                        })
                    }
                }

                realm.writeBlocking {
                    copyToRealm(BackgroundTheme().apply {
                        backgroundTheme = urlItem
                        avatarUrl = urlAvatar
                        iconAnswer = urlBtnCallGreen
                        iconReject = urlBtnCallRed
                    })
                }

                val intent = Intent(requireContext(), NotificationActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, ContactActivity::class.java)
                startActivity(intent)
            }

        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @SuppressLint("Range")
    private fun fetchContacts(isSelected: Boolean) {
        contactList.clear()
        val cursor: Cursor? = requireActivity().contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        cursor?.use {
            while (it.moveToNext()) {
                val name: String =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val id: String = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val phoneCursor: Cursor? = requireActivity().contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null
                )

                phoneCursor?.use { phoneCursorInner ->
                    if (phoneCursorInner.moveToNext()) {
                        val phoneNumber: String = phoneCursorInner.getString(
                            phoneCursorInner.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                        val contact = Contact(name, phoneNumber, isSelected)
                        contactList.add(contact)
                    }
                }
                phoneCursor?.close()
            }
        }
        cursor?.close()
    }

}