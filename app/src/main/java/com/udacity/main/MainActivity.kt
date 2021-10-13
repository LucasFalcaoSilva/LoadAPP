package com.udacity.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.udacity.R
import com.udacity.components.ButtonState
import com.udacity.components.LoadingButton
import com.udacity.databinding.ActivityMainBinding
import com.udacity.extensions.getDownloadManager
import com.udacity.extensions.getNotificationManager
import com.udacity.extensions.showMessage

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var loadingButton: LoadingButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).let {

            viewModel = createViewModel()

            it.viewModel = viewModel
            it.lifecycleOwner = this

            setContentView(it.root)
            setSupportActionBar(it.toolbar)

            loadingButton = it.contentMain.customButton

            createLiveData()
            createChannel()
        }
    }

    private fun createViewModel() = ViewModelProvider(
        this,
        MainViewModelFactory(
            getDownloadManager()
        )
    ).get(MainViewModel::class.java)

    private fun createLiveData() {
        viewModel.eventNoOption.observe(this,
            {
                if (it) {
                    showMessage(getString(R.string.msg_no_option))
                    viewModel.onEventNoOptionCompleted()
                }
            }
        )
        viewModel.eventInvalidURL.observe(this,
            {
                if (it) {
                    showMessage(getString(R.string.invalid_url))
                    viewModel.onEventInvalidURLCompleted()
                }
            }
        )

        viewModel.eventAnimateButton.observe(this,
            {
                if (it) {
                    loadingButton.setState(ButtonState.Clicked)
                    viewModel.onEventAnimatedButtonCompleted()
                }
            }
        )
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().createNotificationChannel(
                NotificationChannel(
                    MainViewModel.CHANNEL_ID,
                    MainViewModel.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
    }

}
