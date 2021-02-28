package com.hossin.linkparser.domain.repo

import com.hossin.linkparser.data.model.MetaData
import io.reactivex.rxjava3.core.Single

interface LinkPreviewRepo {

  fun getMetaData(
    url: String
  ): Single<MetaData>
}