package ru.kudesnik.mymovie.ui.content_provider

import android.app.Instrumentation
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.ContentProviderFragmentBinding
import java.util.jar.Manifest

private const val REQUEST_CODE = 42

class ContentProviderFragment : Fragment() {

    private var _binding: ContentProviderFragmentBinding? = null
    private val binding get() = _binding!!

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
//                getContacts()
            } else {
                Toast.makeText(
                    context, getString(R.string.need_permissions_to_read_contacts),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContentProviderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        context?.let { notNullContext ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    notNullContext,
                    android.Manifest.permission.READ_CONTACTS
                ) -> {
                    //доступ к контактам на телефоне есть
//                    getContacts()
                }
                else -> {
                    //Запрашиваем разрешение
                    requestPermission()
                }
            }
        }
    }

    /*private fun getContacts() {
        context?.let { nonNullContext ->
            //Отправляем запрос на получение контактов и получаем ответ в виде Cursor'a
            val cursorWithContacts: Cursor? = nonNullContext.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC"

            )
            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    //Переходим на позицию в Cursor'e
                    if (cursor.moveToPosition(i)) {
                        //Берем из Cursor'a столбец с именем
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(name)

                    }
                }
            }
            cursorWithContacts?.close()
        }
    }*/

    private fun requestPermission() {
        permissionResult.launch(android.Manifest.permission.READ_CONTACTS)
//        requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }
}