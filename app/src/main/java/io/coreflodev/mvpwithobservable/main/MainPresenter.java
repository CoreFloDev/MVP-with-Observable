package io.coreflodev.mvpwithobservable.main;

import io.coreflodev.mvpwithobservable.common.Presenter;
import io.coreflodev.mvpwithobservable.common.PresenterView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements Presenter {

    private Disposable plusSubscription;
    private Disposable minusSubscription;

    private View view;

    private int state;

    public MainPresenter() {
        this.state = 42;
    }

    @Override
    public void bind(PresenterView view) {
        this.view = (View) view;
        this.view.setViewState(state);
        plusSubscription = this.view.onPlusPressed().subscribe(aVoid -> {
            state++;
            if (this.view != null) {
                this.view.setViewState(state);
            }
        });
        minusSubscription = this.view.onMinusPressed().subscribe(aVoid -> {
            state--;
            if (this.view != null) {
                this.view.setViewState(state);
            }
        });
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void destroy() {
        if(!plusSubscription.isDisposed()) {
            plusSubscription.dispose();
        }
        if(!minusSubscription.isDisposed()) {
            minusSubscription.dispose();
        }
    }

    public interface View extends PresenterView {
        void setViewState(int state);

        Observable<Object> onPlusPressed();

        Observable<Object> onMinusPressed();
    }
}
