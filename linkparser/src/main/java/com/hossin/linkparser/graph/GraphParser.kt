package com.hossin.linkparser.graph

import android.webkit.URLUtil
import android.webkit.URLUtil.isHttpsUrl
import com.hossin.linkparser.data.model.MetaData
import org.jsoup.nodes.Document
import org.jsoup.select.Selector
import java.net.URI
import java.net.URISyntaxException


object GraphParser {

  fun getMetaData(doc: Document, url: String): MetaData? {
    val title = getTitle(doc)
    val description = getDescription(doc)
    val imageUrl = getImageUrlFromMeta(doc, url) ?: getImageUrl(doc, url)
    return if (title == null && imageUrl == null && description == null) null
    else MetaData(url, imageUrl, title, description)
  }

  fun getTitle(doc: Document): String? {
    return try {
      if (doc.documentType() == null) throw Selector.SelectorParseException("Doc type null ")
      var title = doc.select("meta[property=og:title]").attr("content")
      if (title.isNullOrEmpty()) {
        title = doc.title()
      }
      title
    } catch (exception: Selector.SelectorParseException) {
      null
    }
  }

  fun getDescription(doc: Document): String? {
    return try {
      if (doc.documentType() == null) throw Selector.SelectorParseException("Doc type null ")
      var description = doc.select("meta[name=description]").attr("content")
      if (description.isNullOrEmpty()) description =
        doc.select("meta[name=Description]").attr("content")
      if (description.isNullOrEmpty()) description =
        doc.select("meta[property=og:description]").attr("content")
      return description
    } catch (exception: Selector.SelectorParseException) {
      null
    }

  }

  fun getImageUrlFromMeta(doc: Document, url: String): String? {
    return try {
      if (doc.documentType() == null) throw Selector.SelectorParseException("Doc type null ")
      val imageElements = doc.select("meta[property=og:image]")
      val image = imageElements.map {
        it?.attr("content")
      }.firstOrNull()
      if (image != null) {
        resolveImageUrl(url, image)
      } else null

    } catch (exception: Selector.SelectorParseException) {
      null
    }
  }

  fun getImageUrl(doc: Document, url: String): String? {
    return try {
      if (doc.documentType() == null) throw Selector.SelectorParseException("Doc not found")
      var src = doc.select("link[rel=image_src]").attr("href")
      if (src.isNullOrEmpty()) src = doc.select("link[rel=apple-touch-icon]").attr("href")
      if (src.isNullOrEmpty()) src =
        doc.select("link[rel=apple-touch-icon-precomposed]").attr("href")
      if (src.isNullOrEmpty()) src = doc.select("link[rel=icon]").attr("href")
      return resolveImageUrl(url, src)
    } catch (exception: Selector.SelectorParseException) {
      null
    }
  }

  private fun resolveImageUrl(url: String, part: String): String {
    val imageUrl: String?
    if (part.startsWith("http") || part.startsWith("https")) {
      imageUrl = part
    } else {
      var baseUri: URI? = null
      try {
        baseUri = URI(url)
      } catch (e: URISyntaxException) {
        e.printStackTrace()
      }
      baseUri = baseUri?.resolve(part)
      imageUrl = baseUri.toString()
    }
    return imageUrl
  }

}
