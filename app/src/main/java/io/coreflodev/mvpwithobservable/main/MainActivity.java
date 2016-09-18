package io.coreflodev.mvpwithobservable.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.coreflodev.mvpwithobservable.R;
import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    @BindView(R.id.tv_main_number)
    TextView tvNumber;

    @BindView(R.id.b_main_increment)
    View bIncrement;

    @BindView(R.id.b_main_decrement)
    View bDecrement;

    private MainPresenter presenter;

    private static Observable<Object> clicks(final View view) {
        return Observable.create(emitter -> {
            MainThreadDisposable.verifyMainThread();

            view.setOnClickListener(emitter::onNext);

            emitter.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    view.setOnClickListener(null);
                }
            });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.bind(this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void setViewState(int state) {
        tvNumber.setText(getString(R.string.main_value, state));
    }

    @Override
    public Observable<Object> onPlusPressed() {
        return clicks(bIncrement);
    }

    @Override
    public Observable<Object> onMinusPressed() {
        return clicks(bDecrement);
    }
}
