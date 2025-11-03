package com.example.getdataform

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var maleRadio: RadioButton
    private lateinit var femaleRadio: RadioButton
    private lateinit var birthdayInput: EditText
    private lateinit var selectButton: Button
    private lateinit var cardCalendar: CardView
    private lateinit var calendarView: CalendarView
    private lateinit var addressInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var termsCheckbox: CheckBox
    private lateinit var registerButton: Button

    private var isCalendarVisible = false
    private val defaultColor = Color.WHITE
    private val errorColor = Color.parseColor("#FFCDD2") // Màu đỏ nhạt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các view
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        maleRadio = findViewById(R.id.maleRadio)
        femaleRadio = findViewById(R.id.femaleRadio)
        birthdayInput = findViewById(R.id.birthdayInput)
        selectButton = findViewById(R.id.selectButton)
        cardCalendar = findViewById(R.id.cardCalendar)
        calendarView = findViewById(R.id.calendarView)
        addressInput = findViewById(R.id.addressInput)
        emailInput = findViewById(R.id.emailInput)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        registerButton = findViewById(R.id.registerButton)

        setupInputFields()
        setupRadioButtons()
        setupCalendar()
        setupCheckbox()
        setupRegisterButton()
    }

    // Cấu hình các trường input
    private fun setupInputFields() {
        // Không cho phép nhập tay ngày sinh
        birthdayInput.showSoftInputOnFocus = false
        birthdayInput.isFocusable = false
        birthdayInput.isClickable = true

        // Không cho xuống dòng ở các trường tên và email
        firstNameInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        firstNameInput.setSingleLine(true)

        lastNameInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        lastNameInput.setSingleLine(true)

        emailInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailInput.setSingleLine(true)

        // Khi focus vào các trường, đổi lại màu nền về mặc định
        firstNameInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) firstNameInput.setBackgroundColor(defaultColor)
        }

        lastNameInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) lastNameInput.setBackgroundColor(defaultColor)
        }

        birthdayInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) birthdayInput.setBackgroundColor(defaultColor)
        }

        addressInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) addressInput.setBackgroundColor(defaultColor)
        }

        emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) emailInput.setBackgroundColor(defaultColor)
        }

        // Cũng có thể dùng TextWatcher để reset màu khi bắt đầu gõ
        firstNameInput.addTextChangedListener {
            firstNameInput.setBackgroundColor(defaultColor)
        }

        lastNameInput.addTextChangedListener {
            lastNameInput.setBackgroundColor(defaultColor)
        }

        addressInput.addTextChangedListener {
            addressInput.setBackgroundColor(defaultColor)
        }

        emailInput.addTextChangedListener {
            emailInput.setBackgroundColor(defaultColor)
        }
    }

    // Cấu hình RadioButton để có thể bỏ chọn
    private fun setupRadioButtons() {
        var lastCheckedMale = false
        var lastCheckedFemale = false

        maleRadio.setOnClickListener {
            if (lastCheckedMale) {
                // Nếu đã chọn rồi thì bỏ chọn
                maleRadio.isChecked = false
                lastCheckedMale = false
            } else {
                // Chọn male và bỏ chọn female
                maleRadio.isChecked = true
                femaleRadio.isChecked = false
                lastCheckedMale = true
                lastCheckedFemale = false
            }
        }

        femaleRadio.setOnClickListener {
            if (lastCheckedFemale) {
                // Nếu đã chọn rồi thì bỏ chọn
                femaleRadio.isChecked = false
                lastCheckedFemale = false
            } else {
                // Chọn female và bỏ chọn male
                femaleRadio.isChecked = true
                maleRadio.isChecked = false
                lastCheckedFemale = true
                lastCheckedMale = false
            }
        }
    }

    // Cấu hình Calendar
    private fun setupCalendar() {
        // Nút Select → ẩn/hiện CardView chứa lịch
        selectButton.setOnClickListener {
            isCalendarVisible = !isCalendarVisible
            cardCalendar.visibility = if (isCalendarVisible) View.VISIBLE else View.GONE
        }

        // Khi chọn ngày → cập nhật EditText
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
            birthdayInput.setText(date)
            birthdayInput.setBackgroundColor(defaultColor) // Reset màu khi chọn ngày
            cardCalendar.visibility = View.GONE
            isCalendarVisible = false
        }
    }

    // Cấu hình Checkbox
    private fun setupCheckbox() {
        termsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Khi thay đổi trạng thái checkbox, đổi lại màu chữ về đen
            if (isChecked) {
                termsCheckbox.setTextColor(Color.BLACK)
            }
        }
    }

    // Cấu hình nút Register
    private fun setupRegisterButton() {
        registerButton.setOnClickListener {
            validateForm()
        }
    }

    // Hàm kiểm tra form
    private fun validateForm() {
        var isValid = true

        // Reset màu nền về mặc định
        val fields = listOf(
            firstNameInput,
            lastNameInput,
            birthdayInput,
            addressInput,
            emailInput
        )
        fields.forEach { it.setBackgroundColor(defaultColor) }
        termsCheckbox.setTextColor(Color.BLACK)

        // Kiểm tra First Name
        if (firstNameInput.text.isNullOrEmpty()) {
            firstNameInput.setBackgroundColor(errorColor)
            isValid = false
        }

        // Kiểm tra Last Name
        if (lastNameInput.text.isNullOrEmpty()) {
            lastNameInput.setBackgroundColor(errorColor)
            isValid = false
        }

        // Kiểm tra Gender
        if (!maleRadio.isChecked && !femaleRadio.isChecked) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Kiểm tra Birthday
        if (birthdayInput.text.isNullOrEmpty()) {
            birthdayInput.setBackgroundColor(errorColor)
            isValid = false
        }

        // Kiểm tra Address
        if (addressInput.text.isNullOrEmpty()) {
            addressInput.setBackgroundColor(errorColor)
            isValid = false
        }

        // Kiểm tra Email
        if (emailInput.text.isNullOrEmpty()) {
            emailInput.setBackgroundColor(errorColor)
            isValid = false
        }

        // Kiểm tra Terms Checkbox
        if (!termsCheckbox.isChecked) {
            termsCheckbox.setTextColor(Color.RED)
            isValid = false
        }

        // Hiển thị kết quả
        if (isValid) {
            Toast.makeText(this, "Register successfully!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
        }
    }
}