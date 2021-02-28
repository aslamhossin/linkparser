package com.hossin.linkparser.system.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class SingleUseCase<Result, in Params> : BaseReactiveUseCase() {

  protected abstract fun buildUseCaseSingle(params: Params): Single<Result>

  fun execute(@NonNull observer: DisposableSingleObserver<Result>, params: Params) {
    val single: Single<Result> = this.buildUseCaseSingle(params)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
    addDisposable(single.subscribeWith(observer))
  }
}
