package com.example.masterrollerdice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class viewModel :ViewModel() {

    var nightMode = MutableLiveData<Boolean>(false)
    var soundsSwitch = MutableLiveData<Boolean>(false)





}