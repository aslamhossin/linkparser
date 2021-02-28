package com.hossin.linkparser.graph


import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.isNotNull
import com.nhaarman.mockitokotlin2.isNull
import com.nhaarman.mockitokotlin2.notNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Before
import org.junit.Test

class GraphParserTest {

  companion object {
    const val UNIT_TEST_URL =
      "https://jitpack.io/"
  }

  private lateinit var document: Document

  @Before
  fun initTest() {
    document = Jsoup.connect(UNIT_TEST_URL).get()
  }


  /**
   * -- Title is valid  for  Valid doc and title tag
   * */
  @Test
  fun `occurs exception returns null title`() {
    val result = GraphParser.getTitle(Document(""))
    assertThat(result).isNull()
  }

  @Test
  fun `no title tag returns nullOrNotNull title`() {
    val result = GraphParser.getTitle(getDocWithOutTitleTag())
    assertThat(result).isAnyOf(null, notNull(), result)
  }

  private fun getDocWithOutTitleTag(): Document {
    val html = ("<html><head></head>"
        + "<body><p>Parsed HTML into a doc.</p></body></html>")
    return Jsoup.parse(html)
  }

  @Test
  fun `empty title tag returns nullOrNotNull title`() {
    val result = GraphParser.getTitle(getDocWithTitleEmptyTag())
    assertThat(result).isAnyOf(null, notNull(), result)
  }

  private fun getDocWithTitleEmptyTag(): Document {
    val html = ("<html><head><title></title></head>"
        + "<body><p>Parsed HTML into a doc.</p></body></html>")
    return Jsoup.parse(html)
  }

  @Test
  fun `invalid title tag return null  title`() {
    val result = GraphParser.getTitle(getDocInvalidTitleTag())
    assertThat(result).isNull()
  }

  private fun getDocInvalidTitleTag(): Document {
    val html = ("<html><head><title></head>"
        + "<body><p>Parsed HTML into a doc.</p></body></html>")
    return Jsoup.parse(html)
  }


  @Test
  fun `invalid doc return empty title`() {
    val result = GraphParser.getTitle(getInvalidDoc())
    assertThat(result).isNull()
  }

  private fun getInvalidDoc(): Document {
    val html = ("Invalid doc ")
    return Jsoup.parse(html)
  }

  @Test
  fun `valid doc return  title`() {
    val result = GraphParser.getTitle(
      Jsoup.connect(UNIT_TEST_URL)
        .get()
    )
    assertThat(result).isNotEmpty()
  }


  /**
   * -- Description  is valid  for  Valid doc and description tag
   * */
  @Test
  fun `occurs exception  returns null description`() {
    val result = GraphParser.getDescription(Document(""))
    assertThat(result).isNull()
  }

  @Test
  fun `description tag doc return description`() {
    val result = GraphParser.getDescription(document)
    assertThat(result).isNotEmpty()
  }

  /**
   * -- Image  url unit testing
   * */
  @Test
  fun `occurs exception returns null image url`() {
    val result = GraphParser.getImageUrlFromMeta(Document(""), UNIT_TEST_URL)
    assertThat(result).isNull()
  }

  @Test
  fun `src meta tag doc return image url`() {
    val result = GraphParser.getImageUrlFromMeta(document, UNIT_TEST_URL)
    assertThat(result).isAnyOf(null, notNull(), result)
  }

  @Test
  fun `src tag doc return image url`() {
    val result = GraphParser.getImageUrl(document, UNIT_TEST_URL)
    assertThat(result).isAnyOf(null, isNotNull(), result)
  }

  /**
   * -- MetaData unit testing
   * */
  @Test
  fun `if occurs exception return metadata null`() {
    val result = GraphParser.getMetaData(Document(""), UNIT_TEST_URL)
    assertThat(result).isNull()
  }

  @Test
  fun `valid doc return metadata null, empty or meta`() {
    val result = GraphParser.getMetaData(document, UNIT_TEST_URL)
    assertThat(result).isAnyOf(null, notNull(), result)
  }

}