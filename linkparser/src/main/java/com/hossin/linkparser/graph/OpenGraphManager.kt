package com.hossin.linkparser.graph

import android.util.Log
import com.hossin.linkparser.data.model.MetaData
import com.hossin.linkparser.domain.interactor.GraphUseCase
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class OpenGraphManager {

  private val linkParserUseCase = GraphUseCase()
  private val mURLCache: MutableMap<String, MetaData?> = HashMap()
  private val mURLProgress: MutableMap<String, Boolean?> = HashMap()

  interface OnLinkPreview {
    fun onSuccess(position: Int?=0, metaData: MetaData)
    fun onError(position: Int?=0,throwable: Throwable)
  }

  var mOnLinkPreview: OnLinkPreview? = null

  fun isLoading(url: String) = mURLProgress[url]

  fun getMetaData(url: String?, position: Int?=0): MetaData? {
    if (url == null) return null
    if (mURLCache.containsKey(url))
      return mURLCache[url]

    if (mURLProgress[url] == true) return null

    mURLProgress[url] = true
    linkParserUseCase.execute(object : DisposableSingleObserver<MetaData>() {
      override fun onSuccess(t: MetaData) {
        Log.d(javaClass.simpleName,"Meta Parsing success $t")
        mURLCache[url] = t
        mOnLinkPreview?.onSuccess(position, t)
        mURLProgress[url] = false
      }

      override fun onError(e: Throwable) {
        Log.d(javaClass.simpleName,"Meta Parsing failed $e")
        mURLCache[url] = null
        mURLProgress[url] = false
        mOnLinkPreview?.onError(position, e)
      }
    }, GraphUseCase.Params(url))
    return null
  }

  fun dispose() {
    mOnLinkPreview = null
    linkParserUseCase.dispose()
  }

}