package ru.vinogradov.translator.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import ru.vinogradov.translator.R;
import ru.vinogradov.translator.presenters.IPresenter;
import ru.vinogradov.translator.presenters.PresenterImpl;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements IView {

    IPresenter presenter;

    Toolbar toolbar;

    EditText input;
    TextView translate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = (EditText) findViewById(R.id.input);
        translate = (TextView) findViewById(R.id.translate);

        presenter = new PresenterImpl(this);
        presenter.initialize();

    }

    @Override
    public Observable<String> textChanged() {
        return RxTextView.textChangeEvents(input).map(textViewTextChangeEvent -> textViewTextChangeEvent.text().toString());
    }

    @Override
    public void showTranslation(String text) {
        translate.setText(text);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
