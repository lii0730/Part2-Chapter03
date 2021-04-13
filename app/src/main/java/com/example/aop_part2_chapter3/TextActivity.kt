package com.example.aop_part2_chapter3

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class TextActivity : AppCompatActivity() {


    private lateinit var sp: SharedPreferences

    private val DescriptionText: EditText by lazy {
        findViewById(R.id.DescriptionText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        initSharedPreferences()

        /**
         * 텍스트 입력 변화시 발생 이벤트
         */
        DescriptionText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
//                s는 EditText의 text
                saveText(s!!.toString())
            }
        })
    }

    /**
     * onCreate() 시 SharedPreferences 초기화
     */
    private fun initSharedPreferences() {
        if (getSharedPreferences("MyText", MODE_PRIVATE).contains("text")) {
            setText()
        } else {
            createSharedPreferences()
        }
    }


    /**
     * SharedPreference의 저장된 text 뿌리는 함수
     */
    private fun setText() {
        sp = getSharedPreferences("MyText", MODE_PRIVATE)
        DescriptionText.setText(sp.getString("text", "").toString())
    }


    /**
     * SharedPreferences 생성
     */
    private fun createSharedPreferences() {
        sp = getSharedPreferences("MyText", MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("text", "")
        editor.apply()
    }


    /**
     * text 저장 함수
     */
    private fun saveText(text: String) {
        val editor = sp.edit()
        editor.putString("text", text)
        editor.apply()
    }
}