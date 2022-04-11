package com.example.knowmebetter

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.knowmebetter.model.Profile
import com.example.knowmebetter.model.ProfilePool

class PersonActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var editBtn: Button
    private lateinit var dataLayout: LinearLayout
    private lateinit var backBtn: Button
    private lateinit var deleteBtn: Button
//    private lateinit var profile: Profile
    private lateinit var profilePool: ProfilePool
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person)

        index = intent.getIntExtra("index", -1)
//        profile = intent.getSerializableExtra("profile") as Profile
//        profilePool = intent.getSerializableExtra("profile_pool") as ProfilePool
        profilePool = MainActivity.objectToPass
        editBtn = findViewById(R.id.edit_btn)
        backBtn = findViewById(R.id.back)
        deleteBtn = findViewById(R.id.delete_btn)
        dataLayout = findViewById(R.id.dataLayout)

        showPerson(dataLayout)

        deleteBtn.setOnClickListener(this)
        backBtn.setOnClickListener(this)
        editBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.edit_btn -> {
                val intent = Intent(this, PersonEditActivity::class.java)
                intent.putExtra("index", index)
//                intent.putExtra("profile", profile)
//                intent.putExtra("profile_pool", profilePool)
                MainActivity.objectToPass = profilePool
                startActivity(intent)
                finish()
            }
            R.id.back -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                MainActivity.objectToPass = profilePool
                startActivity(intent)
                finish()
            }
            R.id.delete_btn -> {
                val index = intent.getIntExtra("index", -1)
                profilePool.deleteProfile(index)
                MainActivity.objectToPass = profilePool
                MainActivity.writeRecordsToFile(this, profilePool)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showPerson(dataLayout: LinearLayout) {
        val profile = profilePool.getProfile(index)
        val name = profile.fullName
        val fieldsMap = profile.fields
        val nameView = dataLayout.getChildAt(0) as TextView
        nameView.text = name
        val shortDescription = (dataLayout
            .getChildAt(1) as LinearLayout)
            .getChildAt(1) as TextView
        val desc = "Короткое описание:\n" + profile.shortDescription
        shortDescription.text = desc
        val fields = dataLayout.getChildAt(2) as LinearLayout
        fieldsMap.forEach {
            val value = it.key + ": " + it.value
            val textView = TextView(this)
            textView.text = value
            fields.addView(textView)
        }
    }
}