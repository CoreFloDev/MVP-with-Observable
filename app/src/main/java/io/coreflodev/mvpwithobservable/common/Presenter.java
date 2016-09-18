package io.coreflodev.mvpwithobservable.common;

public interface Presenter {

    void bind(PresenterView view);

    void detach();

    void destroy();
}
