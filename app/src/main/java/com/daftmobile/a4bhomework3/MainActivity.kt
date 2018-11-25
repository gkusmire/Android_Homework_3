package com.daftmobile.a4bhomework3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EMAIL_RETRIEVER = EmailRetriever.Impl(applicationContext)
    }

    companion object {
        lateinit var EMAIL_RETRIEVER: EmailRetriever
    }

    fun startSending(@Suppress("UNUSED_PARAMETER") view: View)
    {
        pickEmailAddress()
    }

     fun sendEmail(emailAdress: String, defaultSubject: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)

		emailIntent.setData(Uri.parse("mailto:"+emailAdress));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, defaultSubject)

        val title = resources.getString(R.string.chooser_title)

        val chooser = Intent.createChooser(emailIntent, title)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }

    }

    private fun pickEmailAddress(){
        val intent = Intent(Intent.ACTION_PICK)
		intent.type = ContactsContract.CommonDataKinds.Email.CONTENT_TYPE

		startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {

                data?.let{
                    val uri = data.data
                    uri?.let{
                        val emailAddress = EMAIL_RETRIEVER.retrieve(uri)
                        emailAddress?.let {
                            sendEmail(emailAddress, "Wiadomość z pracy domowej")
                        }
                    }
                }
            }
        }
    }

}



