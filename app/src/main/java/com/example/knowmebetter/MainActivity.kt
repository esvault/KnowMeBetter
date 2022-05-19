package com.example.knowmebetter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.knowmebetter.model.ProfilePool
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


//const val matchParent = LinearLayout.LayoutParams.MATCH_PARENT

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var newPersonBtn: Button
    private lateinit var persons: LinearLayout
    private lateinit var profilePool: ProfilePool

    companion object {
        lateinit var objectToPass: ProfilePool
        private const val fileName = "myRecords.txt"

        fun writeRecordsToFile(applicationContext: Context, profilePool: ProfilePool): Boolean {
            val fos: FileOutputStream
            var oos: ObjectOutputStream? = null
            return try {
                fos = applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE)
                oos = ObjectOutputStream(fos)
                oos.writeObject(profilePool)
                true
            } catch (e: Exception) {
//            Log.e(getClassName(), "Cant save records" + e.message)
                false
            } finally {
                if (oos != null) try {
                    oos.close()
                } catch (e: Exception) {
//                Log.e(getClassName(), "Error while closing stream " + e.message)
                }
            }
        }

        fun readRecordsFromFile(applicationContext: Context): ProfilePool? {
            val fin: FileInputStream
            var ois: ObjectInputStream? = null
            return try {
                fin = applicationContext.openFileInput(fileName)
                ois = ObjectInputStream(fin)
                val profilePool = ois.readObject() as ProfilePool
//            Log.v(getClassName(), "Records read successfully")
                profilePool
            } catch (e: Exception) {
//            Log.e(getClassName(), "Cant read saved records" + e.message)
                null
            } finally {
                if (ois != null) try {
                    ois.close()
                } catch (e: Exception) {
//                Log.e(getClassName(), "Error in closing stream while reading records" + e.message)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profilePool = readRecordsFromFile(applicationContext) ?: ProfilePool()

        persons = findViewById(R.id.persons)

        displayPersons(profilePool, persons)

        newPersonBtn = findViewById(R.id.new_person_btn)

        newPersonBtn.setOnClickListener(this)

//        val inflater = layoutInflater
//        val view = inflater.inflate(R.layout.text, null, false)
//        val lp = view.layoutParams
//
//        val linLayout = findViewById<LinearLayout>(R.id.linLayout)
//        linLayout.addView(view)
    }

    override fun onResume() {
        super.onResume()

        persons.removeAllViews()
        displayPersons(profilePool, persons)

    }

    override fun onPause() {
        super.onPause()
        writeRecordsToFile(applicationContext, profilePool)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.new_person_btn -> {
                val intent = Intent(this, NewPersonActivity::class.java)
                objectToPass = profilePool
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, "Else branch", Toast.LENGTH_LONG).show()
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        menu!!.add("menu1")
//        menu!!.add("menu2")
//        menu!!.add("menu3")
//        return super.onCreateOptionsMenu(menu)
//    }

    private fun displayPersons(from: ProfilePool, to: LinearLayout) {
        for (i in 0 until from.size) {
            val fullName = from.getProfile(i).fullName
            val button = Button(this)
            val lParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = fullName
            button.setOnClickListener {
                val indent = Intent(this, PersonActivity::class.java)
                indent.putExtra("index", i)
//                indent.putExtra("profile_pool", from)
                MainActivity.objectToPass = from
                startActivity(indent)
            }
            to.addView(button, lParams)
        }
    }


}