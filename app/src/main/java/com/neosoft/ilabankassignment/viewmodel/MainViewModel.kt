package com.neosoft.ilabankassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neosoft.ilabankassignment.R
import com.neosoft.ilabankassignment.model.ImageData

class MainViewModel : ViewModel() {

    var dummmyData = MutableLiveData<ArrayList<ImageData>>()
    var data = ArrayList<ImageData>()
    var currentPage  = 0
     var selectedDot = 0
     var unselectedDot = 0
    fun fetchDymmyData() {
        data.add(
            ImageData(
                R.drawable.bird,
                null,
                null,
                mutableListOf("Eagle", "Peocock", "Owl", "Pigeon", "Penguin","Mallard","Cuckoo","Sparrow", "Crow","Parrot","Hen","Swan","Bluebird","Gannet","Oriole","Weaverbird","Catbird")


            )
        )
        data.add(
            ImageData(
                R.drawable.animal,
                null,
                null,
                mutableListOf("Lion", "Tiger", "Bear", "Crocodile","Donkey","Deer","Cat","Dog","Goat", "Wolf", "Fox","Leopard","Elephant","Horse","Monkey")
            )
        )
        data.add(
            ImageData(
                R.drawable.flower,
                null,
                null,
                mutableListOf("Rose", "Jasmine", "Sunflower", "Lotus", "Lavender", "Marigold","Hibiscus","Millingtonia Hortensis")
            )
        )
        if (dummmyData.value?.size ?: 0 == 0)
        dummmyData.value = data
    }
}