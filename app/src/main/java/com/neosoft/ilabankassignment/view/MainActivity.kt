package com.neosoft.ilabankassignment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.neosoft.ilabankassignment.R
import com.neosoft.ilabankassignment.databinding.ActivityMainBinding
import com.neosoft.ilabankassignment.model.ImageData
import com.neosoft.ilabankassignment.view.adapters.MainRecycleAdapter
import com.neosoft.ilabankassignment.view.adapters.ViewPagerAdapter
import com.neosoft.ilabankassignment.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var dots: Array<ImageView?>? = null
    private var imageList = mutableListOf<ImageData>()
    private var originalList = mutableListOf<ImageData>()
    var dataSet = ArrayList<String>()
    private lateinit var mainViewModel: MainViewModel
    lateinit var adapter: MainRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Assign variable
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.contentView.recyclerView.layoutManager = LinearLayoutManager(this)

        //fetch dummy data
        mainViewModel.fetchDymmyData()
        viewPagerAdapter = ViewPagerAdapter(imageList)
        binding.viewPager.adapter = viewPagerAdapter
        adapter = MainRecycleAdapter(dataSet)
        binding.contentView.recyclerView.adapter = adapter
        pageListner()
        //observe data
        mainViewModel.dummmyData.observe(this) {
            imageList.clear()
            originalList.clear()
            dataSet.clear()
            imageList.addAll(it)
            mainViewModel.dummmyData.value?.clear()
            originalList = imageList
            viewPagerAdapter.notifyDataSetChanged()
            dataSet.addAll(imageList.first().dummyList)
            adapter.notifyDataSetChanged()
            setupDots(imageList.size)
            mainViewModel.dummmyData.removeObservers(this)
        }

        performSearch()

    }

    private fun pageListner() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.searchView.setQuery("",false)
                binding.searchView.clearFocus()
            }

            override fun onPageSelected(position: Int) {

                mainViewModel.currentPage = position
                adapter.updateList(imageList[mainViewModel.currentPage].dummyList)
                for (dot in dots!!) {
                    dot!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            mainViewModel.unselectedDot
                        )
                    )
                }
                dots!![position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        mainViewModel.selectedDot
                    )
                )

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // search function
    private fun performSearch() {
        binding.searchView.setOnCloseListener {
            adapter.updateList(originalList[mainViewModel.currentPage].dummyList)
            false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.updateList(originalList[mainViewModel.currentPage].dummyList)
                } else {
                    adapter.search(newText)
                }
                return true
            }
        })
    }

    // setup dots without using third party library
    private fun setupDots(size: Int) {
        val typedArray = applicationContext.theme.obtainStyledAttributes(
            null,
            R.styleable.ImageSlider,
            0,
            0
        )
        mainViewModel.selectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_iss_selected_dot,
            R.drawable.default_selected_dot
        )
        mainViewModel.unselectedDot = typedArray.getResourceId(
            R.styleable.ImageSlider_iss_unselected_dot,
            R.drawable.default_unselected_dot
        )
        binding.pagerDots.removeAllViews()
        dots = arrayOfNulls(size)

        for (i in 0 until size) {
            dots!![i] = ImageView(applicationContext)
            dots!![i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    mainViewModel.unselectedDot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.pagerDots.addView(dots!![i], params)
        }
        dots!![0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                mainViewModel.selectedDot
            )
        )
    }
}