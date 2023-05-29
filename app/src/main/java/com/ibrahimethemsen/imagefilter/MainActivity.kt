package com.ibrahimethemsen.imagefilter

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ibrahimethemsen.imagefilter.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val _setSaturation = MutableLiveData<Boolean>(false)
    private val setSaturation : LiveData<Boolean> get() = _setSaturation
    private val _setBlur = MutableLiveData<Boolean>(false)
    private val setBlur : LiveData<Boolean> get() = _setBlur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setSaturation.setOnCheckedChangeListener { _, isChecked ->
            _setSaturation.value = isChecked
        }
        binding.setBlur.setOnCheckedChangeListener { _, isChecked ->
            _setBlur.value = isChecked
        }
        setImageFilter()
    }

    private fun setImageFilter(){
        setBlur.observe(this){
            binding.imageFilter.setBlur(it)
        }
        setSaturation.observe(this){
            binding.imageFilter.setSaturation(it)
        }
    }


}