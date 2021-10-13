package com.udacity.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.udacity.databinding.ActivityDetailBinding
import com.udacity.extensions.getDownloadManager
import com.udacity.extensions.getLongDefault
import com.udacity.main.MainActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DOWNLOAD_ID = "DOWNLOAD_ID"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityDetailBinding.inflate(layoutInflater).let {

            viewModel = createViewModel(intent.getLongDefault(DOWNLOAD_ID))

            it.detailViewModel = viewModel
            it.lifecycleOwner = this

            setContentView(it.root)
            setSupportActionBar(it.toolbar)

            it.contentDetail.confirmButton.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    private fun createViewModel(downloadId: Long) = ViewModelProvider(
        this,
        DetailViewModelFactory(
            this,
            downloadId
        )
    ).get(DetailViewModel::class.java)

}
