package com.example.newsx.activity

import android.app.PictureInPictureParams
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import com.example.newsx.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var extras = intent.extras

        if (extras != null) {
            var link = extras.get("link")
            webView.loadUrl(link.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        val display = windowManager.defaultDisplay
        val p = Point()
        display.getSize(p)

        val width = p.x
        val height = p.y

        val ratio = Rational(width, height)
        val pip_Builder = PictureInPictureParams.Builder()
        pip_Builder.setAspectRatio(ratio).build()
        enterPictureInPictureMode(pip_Builder.build())
    }
}
