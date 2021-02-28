package com.hossin.linkparser.system.rx

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseReactiveUseCase {

  private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  protected fun addDisposable(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }

  fun dispose() = if (!compositeDisposable.isDisposed) compositeDisposable.dispose() else Unit
}
