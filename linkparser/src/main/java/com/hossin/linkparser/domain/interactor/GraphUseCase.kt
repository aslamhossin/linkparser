package com.hossin.linkparser.domain.interactor

import com.hossin.linkparser.data.model.MetaData
import com.hossin.linkparser.data.repo.LinkPreviewRepoImp
import com.hossin.linkparser.system.rx.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GraphUseCase : SingleUseCase<MetaData, GraphUseCase.Params>() {

  override fun buildUseCaseSingle(params: Params): Single<MetaData> {
    return LinkPreviewRepoImp().getMetaData(params.url)
  }

  data class Params(val url: String)

}
