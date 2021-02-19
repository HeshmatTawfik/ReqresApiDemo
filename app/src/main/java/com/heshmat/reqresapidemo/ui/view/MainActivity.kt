package com.heshmat.reqresapidemo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heshmat.reqresapidemo.R
import com.heshmat.reqresapidemo.model.User
import com.heshmat.reqresapidemo.presenter.UserPresenter
import com.heshmat.reqresapidemo.ui.adapter.ItemClickListener
import com.heshmat.reqresapidemo.ui.adapter.UsersAdapter
import com.heshmat.reqresapidemo.utils.InternetUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.net.UnknownHostException

class MainActivity : AppCompatActivity(), UserView, ItemClickListener {

    lateinit var userPresenter: UserPresenter
    private val MAX_PAGE_NUM = 2
    private lateinit var adapter: UsersAdapter
    private var pageNum: Int = 0
    private var isLoading: Boolean = false
    private var isInternetConnected = false;
    private lateinit var llm: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userPresenter = UserPresenter(this)
        adapter = UsersAdapter(arrayListOf(), this)
        userRv.adapter = adapter
        llm = LinearLayoutManager(this)
        userRv.layoutManager = llm
        InternetUtil.init(this)
        InternetUtil.observe(this, Observer { status ->
            if (status) {
                noInternetTv.visibility = View.GONE

                isInternetConnected = true
                if (pageNum == 0) {
                    pageNum++
                    userPresenter.getUsers(pageNum)
                    addScrollListenToRv()

                }

            } else {
                noInternetTv.visibility = View.VISIBLE
                isInternetConnected = false
            }


        })


    }

    override fun usersReady(userArrList: List<User>) {
        adapter.addData(userArrList)
        adapter.notifyDataSetChanged()
    }

    override fun loading() {
        progressBar.visibility = View.VISIBLE

    }

    override fun success() {
        progressBar.visibility = View.GONE

    }

    override fun fail(t: Throwable) {
        progressBar.visibility = View.GONE
        showErrorMSG(t)
    }

    private fun showErrorMSG(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun addScrollListenToRv() {
        userRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && pageNum != MAX_PAGE_NUM && isInternetConnected) {
                    if (llm.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        pageNum++
                        userPresenter.getUsers(pageNum)
                        isLoading = true

                    }
                }
                // check if the last page is reached and the last user in the list was shown
                else if (pageNum == MAX_PAGE_NUM && llm.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    Toast.makeText(this@MainActivity, "No more result", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onItemClickListener(item: User) {
        val intent = Intent(this, FullScreenAvatarActivity::class.java).apply {
            putExtra("IMG_URL", item.avatar)
        }

        startActivity(intent)
    }
}
