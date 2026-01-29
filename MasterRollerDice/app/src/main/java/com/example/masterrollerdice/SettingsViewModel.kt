package com.example.masterrollerdice


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    var nightMode = MutableLiveData<Boolean>(false)
    var soundsSwitch = MutableLiveData<Boolean>(false)

}