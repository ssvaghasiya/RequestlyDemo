package com.example.demoapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.R
import com.example.demoapp.model.User
import com.example.demoapp.retrofit.ApiInterface
import com.example.demoapp.retrofit.RetrofitClient
import com.example.demoapp.ui.adapter.UserListAdapter
import com.example.demoapp.utils.CustomSwipeToRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var apiInterface: ApiInterface
    private lateinit var textViewPageNo: AppCompatTextView
    private lateinit var textViewLabelDefaultPageNo: AppCompatTextView
    private lateinit var textViewLabelNoData: AppCompatTextView
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var mSwipeRefreshLayout: CustomSwipeToRefresh
    private lateinit var userListAdapter: UserListAdapter
    private val userList = ArrayList<User.Datum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        assignValue()
        mSwipeRefreshLayout.setOnRefreshListener {
            callApi()
        }
        setRecycleView()
        callApi()
    }

    private fun assignValue() {
        apiInterface = RetrofitClient.create()
        textViewPageNo = findViewById(R.id.textViewLabelPageNo)
        textViewLabelDefaultPageNo = findViewById(R.id.textViewLabelDefaultPageNo)
        textViewLabelNoData = findViewById(R.id.textViewLabelNoData)
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)
    }

    private fun setRecycleView() = with(recyclerViewUsers) {
        userListAdapter = UserListAdapter(userList)
        layoutManager = LinearLayoutManager(applicationContext)
        itemAnimator = DefaultItemAnimator()
        adapter = userListAdapter
    }

    private fun callApi() {
        apiInterface.getUsers("2").enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                response?.body()?.apply {
                    mSwipeRefreshLayout.isRefreshing = false
                    textViewPageNo.text = "${getString(R.string.label_current_page_no)} $page"
                    textViewLabelDefaultPageNo.text =
                        "${getString(R.string.label_default_page_no)} 2"
                    userList.clear()
                    data?.let { userList.addAll(it) }
                    userListAdapter.notifyDataSetChanged()
                    if (userList.isEmpty()) {
                        textViewLabelNoData.visibility = View.VISIBLE
                        recyclerViewUsers.visibility = View.GONE
                    } else {
                        textViewLabelNoData.visibility = View.GONE
                        recyclerViewUsers.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Toast.makeText(
                    this@MainActivity,
                    "Response Fail : ${t?.message}",
                    Toast.LENGTH_LONG
                ).show()
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }
}