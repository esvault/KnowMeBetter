package com.example.knowmebetter

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.knowmebetter.model.Profile
import com.example.knowmebetter.model.ProfilePool

class PersonEditActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var  dataLayout: LinearLayout
    private lateinit var addBtn: Button
    private lateinit var saveBtn: Button
//    private lateinit var profile: Profile
    private lateinit var profilePool: ProfilePool
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_edit)

        index = intent.getIntExtra("index", -1)
//        profile = intent.getSerializableExtra("profile") as Profile
//        profilePool = intent.getSerializableExtra("profile_pool") as ProfilePool
        profilePool = MainActivity.objectToPass

        saveBtn = findViewById(R.id.save_btn)
        addBtn = findViewById(R.id.add_btn)
        dataLayout = findViewById(R.id.dataEditLayout)

        showProfile(dataLayout)

        saveBtn.setOnClickListener(this)
        addBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_btn -> {
                val lParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1F
                )
                val field = LinearLayout(this)
                field.layoutParams = lParams
                field.orientation = LinearLayout.HORIZONTAL
                val key = EditText(this)
                key.hint = "Key"
                val value = EditText(this)
                value.hint = "Value"

                field.addView(key)
                field.addView(value)

                (dataLayout.getChildAt(2) as LinearLayout).addView(field)
            }
            R.id.save_btn -> {
                editProfile()
                val intent = Intent(this, PersonActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                intent.putExtra("index", index)
//                intent.putExtra("profile", profile)
//                intent.putExtra("profile_pool", profilePool)
                MainActivity.objectToPass = profilePool
                MainActivity.writeRecordsToFile(applicationContext, profilePool)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun editProfile() {
        val nameField = dataLayout.getChildAt(0) as EditText
        val shortDescription = (dataLayout
            .getChildAt(1) as LinearLayout)
            .getChildAt(1) as EditText
        val fields = dataLayout.getChildAt(2) as LinearLayout
        val changesMap = mutableMapOf<String, String>()
//        profile.fullName = nameField.text.toString()
        val count = fields.childCount
        for (i in 0 until count) {
            val cur = fields.getChildAt(i) as LinearLayout
            val key = (cur.getChildAt(0) as EditText).text.toString()
            val value = (cur.getChildAt(1) as EditText).text.toString()
            changesMap[key] = value
        }
        profilePool.editProfileName(index, nameField.text.toString())
        profilePool.editProfileDesc(index, shortDescription.text.toString())
        profilePool.editProfile(index, changesMap)
    }

    private fun showProfile(dataLayout: LinearLayout) {
        val profile = profilePool.getProfile(index)
        val name = SpannableStringBuilder(profile.fullName)
        val desc = SpannableStringBuilder(profile.shortDescription)
        val fieldsMap = profile.fields
        val nameView = dataLayout.getChildAt(0) as EditText
        nameView.text = name
        val descView = (dataLayout
            .getChildAt(1) as LinearLayout)
            .getChildAt(1) as EditText
        descView.text = desc
        val fields = dataLayout.getChildAt(2) as LinearLayout
        val lParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1F
        )
        fieldsMap.forEach {
            val editKey = EditText(this).apply {
                text = SpannableStringBuilder(it.key)
            }
            val editValue = EditText(this).apply {
                text = SpannableStringBuilder(it.value)
            }
            val llField = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                addView(editKey, lParams)
                addView(editValue, lParams)
            }
            fields.addView(llField)
        }
    }
}