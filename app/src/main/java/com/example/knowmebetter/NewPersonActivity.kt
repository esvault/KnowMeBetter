package com.example.knowmebetter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.knowmebetter.model.Profile
import com.example.knowmebetter.model.ProfilePool

class NewPersonActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var  dataLayout: LinearLayout
    private lateinit var addBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var profilePool: ProfilePool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_person)

        profilePool = MainActivity.objectToPass

        saveBtn = findViewById(R.id.new_save_btn)
        addBtn = findViewById(R.id.new_add_btn)
        dataLayout = findViewById(R.id.dataNewLayout)

        saveBtn.setOnClickListener(this)
        addBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.new_add_btn -> {
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
            R.id.new_save_btn -> {
                createProfile()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                intent.putExtra("profile", profile)
//                intent.putExtra("profile_pool", profilePool)
                MainActivity.objectToPass = profilePool
                MainActivity.writeRecordsToFile(this, profilePool)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun createProfile() {
        val nameField = dataLayout.getChildAt(0) as EditText
        val shortDescription = (dataLayout
            .getChildAt(1) as LinearLayout)
            .getChildAt(1) as EditText
        val fields = dataLayout.getChildAt(2) as LinearLayout
        val changesMap = mutableMapOf<String, String>()
        val count = fields.childCount
        for (i in 0 until count) {
            val cur = fields.getChildAt(i) as LinearLayout
            val key = (cur.getChildAt(0) as EditText).text.toString()
            val value = (cur.getChildAt(1) as EditText).text.toString()
            changesMap[key] = value
        }
        val profile = Profile(nameField.text.toString())
        profile.shortDescription = shortDescription.text.toString()
        changesMap.forEach {
            profile.editField(it.key, it.value)
        }
        profilePool.addProfile(profile)
    }
}