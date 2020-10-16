package com.practice.myroad.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.practice.myroad.R
import com.practice.myroad.databinding.ActivityMainBinding
import com.practice.myroad.internal.LoadingState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myRoadViewModel: MyRoadViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)


        btnSearch.setOnClickListener {view ->
            etSearch?.let { editText ->
                var query = editText.text.toString()
                if (!query.isNullOrBlank()) {
                    myRoadViewModel.getRoadData(query)
                } else {
                    tvRequestStatus.text = getString(R.string.error_empty_search)
                }
            }
            view?.let { view.context?.hideKeyboard(it) }
        }

        myRoadViewModel.roadData.observe(this, Observer { road ->
            binding.result = road
        })

        myRoadViewModel.loadingState.observe(this, Observer { loadingState ->
            when (loadingState.status) {
                LoadingState.Status.FAILED -> {
                    clearFields()
                    Toast.makeText(
                        this,
                        loadingState.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                    tvRequestStatus.text = "FAILED"
                }
                LoadingState.Status.RUNNING -> {
                    clearFields()
                    Toast.makeText(
                        this, "Loading", Toast.LENGTH_SHORT
                    )
                        .show()
                }
                LoadingState.Status.SUCCESS -> {
                    Toast.makeText(
                        this, "Success", Toast.LENGTH_SHORT
                    ).show()
                    tvRequestStatus.text = "SUCCESS"
                }
            }
        })

    }

    private fun clearFields() {
        binding.result = null
        tvRequestStatus.text = ""
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}