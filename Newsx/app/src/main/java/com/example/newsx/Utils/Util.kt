package com.example.newsx.Utils


import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.newsx.R
import com.squareup.picasso.Picasso


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {

        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
        centerRadius = 45f
        setColorSchemeColors(Color.BLACK)
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable?) {

    if (progressDrawable != null) {
        Picasso.get()
            .load(uri)
            .placeholder(progressDrawable)
            .error(R.drawable.sample_news_image)
            .into(this)
    } else {
        Picasso.get()
            .load(uri)
            .into(this)
    }
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, null)
}