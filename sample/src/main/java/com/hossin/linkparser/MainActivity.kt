package com.hossin.linkparser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.hossin.linkparser.data.model.MetaData
import com.hossin.linkparser.graph.OpenGraphManager


class MainActivity : AppCompatActivity() {

  private lateinit var openGraphManager: OpenGraphManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    openGraphManager = OpenGraphManager()
    openGraphManager.getMetaData("https://github.com/ReactiveX/RxAndroid")

    openGraphManager.mOnLinkPreview = object : OpenGraphManager.OnLinkPreview {
      @SuppressLint("SetTextI18n")
      override fun onSuccess(position: Int?, metaData: MetaData) {
        Log.d("Meta", "$metaData")
        findViewById<TextView>(R.id.meta).text =
          metaData.title + "\n" + metaData.description + "\n" + metaData.imageurl
      }

      override fun onError(position: Int?, throwable: Throwable) {
        Log.d("Meta", "$throwable")
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    openGraphManager.dispose()
  }
}