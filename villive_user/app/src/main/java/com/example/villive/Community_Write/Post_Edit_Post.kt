package com.example.villive.Community_Write

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.villive.R

class Post_Edit_Post : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etWrite: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_edit_post)

        etTitle = findViewById(R.id.et_edit_title)
        etWrite = findViewById(R.id.et_edit_write)

        etTitle.setText(intent.getStringExtra("title"))
        etWrite.setText(intent.getStringExtra("write"))

        findViewById<Button>(R.id.btn_edit_save).setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {
        val intent = Intent()
        intent.putExtra("new_title", etTitle.text.toString())
        intent.putExtra("new_write", etWrite.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
