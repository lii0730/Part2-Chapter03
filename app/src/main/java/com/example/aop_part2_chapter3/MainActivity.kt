package com.example.aop_part2_chapter3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val UnLock_Button: Button by lazy {
        findViewById(R.id.UnLock_Button)
    }

    private val Change_PW_Button: Button by lazy {
        findViewById(R.id.Change_PW_Button)
    }

    private val FirstNum: NumberPicker by lazy {
        findViewById(R.id.FirstNum)
    }

    private val SecondNum: NumberPicker by lazy {
        findViewById(R.id.SecondNum)
    }

    private val ThirdNum: NumberPicker by lazy {
        findViewById(R.id.ThirdNum)
    }

    private var isChange: Boolean = false
    private lateinit var password: String
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initNumberPickerValue(FirstNum, 0, 9)
        initNumberPickerValue(SecondNum, 0, 9)
        initNumberPickerValue(ThirdNum, 0, 9)

        /**
         * SharedPreference 생성
         */
        createSharedPreferences()

        /**
         * 비밀번호 맞으면 다이어리 오픈
         */
        UnLock_Button.setOnClickListener {
            clickUnLockButton()
        }

        /**
         * 비밀번호 변경 초기 비밀번호 000 설정
         */
        Change_PW_Button.setOnClickListener {
            clickChangePWButton()
        }

    }

    /**
     * Activity가 다시 시작될 때 NumberPicker Value 초기화
     */
    override fun onRestart() {
        super.onRestart()
        FirstNum.value = 0
        SecondNum.value = 0
        ThirdNum.value = 0
    }

    /**
     * NumberPicker 3개 숫자 범위 초기화
     */
    private fun initNumberPickerValue(numberPicker: NumberPicker, min: Int = 0, max: Int = 9) {
        numberPicker.minValue = min
        numberPicker.maxValue = max
    }

    private fun clickUnLockButton() {
        val pw = FirstNum.value.toString() + SecondNum.value.toString() + ThirdNum.value.toString()
        if ((sp.getString("password", password)).equals(pw)) {
            val intent = Intent(this@MainActivity, TextActivity::class.java)
            startActivity(intent)
        } else {
            val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Password Error!")
                .setPositiveButton("OK", null)
                .setMessage("Retry input your password")
                .create()
                .show()
        }
    }

    private fun clickChangePWButton() {
        val pw = FirstNum.value.toString() + SecondNum.value.toString() + ThirdNum.value.toString()
        if (!isChange) {
            if (sp.getString("password", password).equals(pw)) {
                Change_PW_Button.text = resources.getString(R.string.Save)
                isChange = true
            } else {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Password Error!")
                    .setPositiveButton("OK", null)
                    .setMessage("Retry input your password")
                    .create()
                    .show()
            }
        } else {
            val editor = sp.edit()
            editor.putString(
                "password",
                FirstNum.value.toString() + SecondNum.value.toString() + ThirdNum.value.toString()
            )
            editor.apply()
            password = sp.getString(
                "password",
                FirstNum.value.toString() + SecondNum.value.toString() + ThirdNum.value.toString()
            ).toString()
            Change_PW_Button.text = resources.getString(R.string.ChangeBtn_text)
            isChange = false
            Toast.makeText(this@MainActivity, "비밀번호가 저장되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createSharedPreferences() {
        sp = getSharedPreferences("MyPassword", MODE_PRIVATE)
        password = sp.getString("password", "000").toString()
    }

}