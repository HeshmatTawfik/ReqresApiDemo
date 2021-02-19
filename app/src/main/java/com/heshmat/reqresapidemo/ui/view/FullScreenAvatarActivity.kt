package com.heshmat.reqresapidemo.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.heshmat.reqresapidemo.R
import kotlinx.android.synthetic.main.activity_full_screen_avatar.*

class FullScreenAvatarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_avatar)
        val avatarUrl = intent.getStringExtra("IMG_URL")
        Glide.with(this)
            .asBitmap()
            .load(avatarUrl)
            .into(fullScreenAvatar)
    }
}
