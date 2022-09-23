package com.michaelchen27.eratanitest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.michaelchen27.eratanitest.data.api.UserAPIInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class BottomSheetRegisterUserFragment(registerListener: OnRegisterListener) :
    BottomSheetDialogFragment(),
    View.OnClickListener {

    private val TAG = BottomSheetRegisterUserFragment::class.simpleName

    private lateinit var etName: TextInputEditText
    private lateinit var tilName: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilEmail: TextInputLayout
    private lateinit var rgGender: RadioGroup
    private lateinit var rgStatus: RadioGroup
    private lateinit var btnRegisterUser: Button

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var gender: String
    private lateinit var status: String

    private var onRegisterListener: OnRegisterListener

    private var isAllFieldsChecked: Boolean = false

    init {
        this.onRegisterListener = registerListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_user_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {
        etName = view.findViewById(R.id.et_name)
        tilName = view.findViewById(R.id.til_name)
        etEmail = view.findViewById(R.id.et_email)
        tilEmail = view.findViewById(R.id.til_email)
        rgGender = view.findViewById(R.id.rg_gender)
        rgStatus = view.findViewById(R.id.rg_status)
        btnRegisterUser = view.findViewById(R.id.btn_register_user)
        btnRegisterUser.setOnClickListener(this)
    }

    private fun getInputs() {
        // Name
        if (etName.text!!.isNotEmpty()) {
            name = etName.text.toString()
        }

        // Email
        if (etEmail.text!!.isNotEmpty()) {
            email = etEmail.text.toString()
        }

        // Gender
        if (rgGender.checkedRadioButtonId == R.id.rb_male) {
            gender = "male"
        } else if (rgGender.checkedRadioButtonId == R.id.rb_female) {
            gender = "female"
        }

        // Status
        if (rgStatus.checkedRadioButtonId == R.id.rb_active) {
            status = "active"
        } else if (rgStatus.checkedRadioButtonId == R.id.rb_inactive) {
            status = "inactive"
        }

    }

    private fun checkAllFields(): Boolean {
        if (etName.text!!.isEmpty()) {
            tilName.error = "Name field can not be empty!"
            return false
        } else {
            tilName.error = null
        }

        if (etEmail.text!!.isEmpty()) {
            tilEmail.error = "Email field can not be empty!"
            return false
        } else {
            tilEmail.error = null
        }

        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(activity, "Gender is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (rgStatus.checkedRadioButtonId == -1) {
            Toast.makeText(activity, "Status is required", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun postRequest() {
        val requestInterface = Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val api: UserAPIInterface = requestInterface.create(UserAPIInterface::class.java)

        val call: Call<ResponseBody> = api.postUser(
            APIConstant.ACCESS_TOKEN, name, email, gender, status
        )

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val jsonResponseString = response.body()?.string()
                        Log.d(TAG, "onResponse: $jsonResponseString")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
            }
        })

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_user -> {
                isAllFieldsChecked = checkAllFields()
                if (isAllFieldsChecked) {
                    getInputs()
                    Log.d(TAG, "Name: $name Email: $email Gender: $gender Status: $status")
                    postRequest()
                    onRegisterListener.onRegisterClick()
                    this.dismiss()
                }
            }
        }
    }

    interface OnRegisterListener {
        fun onRegisterClick()
    }
}
