package com.michaelchen27.eratanitest

import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.michaelchen27.eratanitest.data.api.UserAPIInterface
import com.michaelchen27.eratanitest.data.vo.user.UserItem
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiCallingActivity : AppCompatActivity() {
    private val TAG = ApiCallingActivity::class.simpleName
    private lateinit var tlUsers: TableLayout
    private val gson: Gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_calling)
        initView()
        loadData()
    }

    private fun initView() {
        tlUsers = findViewById(R.id.tl_users)
    }

    private fun loadData() {
        val requestInterface = Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val api: UserAPIInterface = requestInterface.create(UserAPIInterface::class.java)

        val call: Call<ResponseBody> = api.getUsers()

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val jsonResponseString = response.body()?.string()
                        Log.d(TAG, "onResponse: $jsonResponseString")
                        setupTable(jsonResponseString.toString())
                    }
                } else {
//                    errToast(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
            }
        })
    }

    private fun setupTable(responses: String) {
        val jsonArray = JSONArray(responses)
        val arrLen = jsonArray.length()
        for (i in 0 until arrLen) {
            val jsonObj = jsonArray.getJSONObject(i)
            val json = jsonObj.toString()
            val userItem: UserItem = gson.fromJson(json, UserItem::class.java)

            val row = TableRow(this)
            val lp: TableRow.LayoutParams =
                TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            row.layoutParams = lp

            val id = TextView(this)
            id.setPadding(0, 0, 30, 0)
            id.text = userItem.id.toString()

            val name = TextView(this)
            name.setPadding(0, 0, 30, 0)
            name.text = userItem.name

            val email = TextView(this)
            email.setPadding(0, 0, 30, 0)
            email.text = userItem.email

            val gender = TextView(this)
            gender.setPadding(0, 0, 30, 0)
            gender.text = userItem.gender

            val status = TextView(this)
            status.setPadding(0, 0, 30, 0)
            status.text = userItem.status

            row.addView(id)
            row.addView(name)
            row.addView(email)
            row.addView(gender)
            row.addView(status)

            tlUsers.addView(row)
        }


    }
}