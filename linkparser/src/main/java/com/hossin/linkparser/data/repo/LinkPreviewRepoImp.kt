package com.hossin.linkparser.data.repo

import com.hossin.linkparser.graph.GraphParser
import com.hossin.linkparser.data.model.MetaData
import com.hossin.linkparser.domain.repo.LinkPreviewRepo
import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class LinkPreviewRepoImp : LinkPreviewRepo {
  companion object {
    const val PARSE_TIME_OUT = 30 * 1000
  }

  override fun getMetaData(url: String): Single<MetaData> {
    return Single.create { emitter ->
      val doc: Document
      try {
        doc = Jsoup.connect(url).timeout(PARSE_TIME_OUT).get()
        val metaData = GraphParser.getMetaData(doc, url)
        if (metaData != null) {
          emitter.onSuccess(metaData)
        } else emitter.onError(Throwable("Meta tag not found"))
      } catch (exception: Exception) {
        emitter.onError(exception)
      }
    }
  }

}