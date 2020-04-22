package com.example.newsx.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsx.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var extra = intent.extras

        if (extra!= null) {
            var link = extra.get("link")
            webViewId.loadUrl(link.toString())
        }
    }
}
