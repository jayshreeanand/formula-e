package com.fiaformulae.wayfinder.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxUtils {
  /**
   * {@link Observable.Transformer} that transforms the source observable to subscribe in the
   * io thread and observe on the Android's UI thread.
   */
  private static final Observable.Transformer ioToMainThreadSchedulerTransformer;

  static {
    ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
  }

  /**
   * Get {@link Observable.Transformer} that transforms the source observable to subscribe in
   * the io thread and observe on the Android's UI thread.
   *
   * Because it doesn't interact with the emitted items it's safe ignore the unchecked casts.
   *
   * @return {@link Observable.Transformer}
   */
  @SuppressWarnings("unchecked")
  private static <T> Observable.Transformer<T, T> createIOToMainThreadScheduler() {
    return tObservable -> tObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @SuppressWarnings("unchecked")
  public static <T> Observable.Transformer<T, T> applyIOToMainThreadSchedulers() {
    return ioToMainThreadSchedulerTransformer;
  }

  public static void clearCompositeSubscription(CompositeSubscription mCompositeSubscription) {
    if (mCompositeSubscription != null) {
      mCompositeSubscription.clear();
    }
  }
}
