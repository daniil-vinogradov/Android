package ru.vinogradov.translator.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ru.vinogradov.translator.R;
import ru.vinogradov.translator.loaders.TranslatorLoader;

public class TranslatorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    private static int TRANSLATOR_LOADER = 0;
    private static String TEXT_TO_TRANSLATE = "ru.vinogradov.translator.ui.TEXT_TO_TRANSLATE";
    private static String LANG_TO_TRANSLATE = "ru.vinogradov.translator.ui.LANG";

    String lang = "ru-en";

    TextView firstLang;
    TextView secondLang;
    EditText input;
    TextView translate;
    ImageButton replace;
    ImageButton clear;

    LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firstLang = (TextView) findViewById(R.id.first_lang);
        secondLang = (TextView) findViewById(R.id.second_lang);
        input = (EditText) findViewById(R.id.input);
        translate = (TextView) findViewById(R.id.translate);
        replace = (ImageButton) findViewById(R.id.replace);
        clear = (ImageButton) findViewById(R.id.clear);

        loaderManager = getSupportLoaderManager();

        clear.setOnClickListener(this);
        replace.setOnClickListener(this);

        if (savedInstanceState != null)
            lang = savedInstanceState.getString(LANG_TO_TRANSLATE);

        setLanguages();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String lastSymbol = s.subSequence(s.length() - 1, s.length()).toString();
                    if (lastSymbol.equals(" ")) {
                        Bundle bundle = new Bundle();
                        bundle.putString(TEXT_TO_TRANSLATE, s.toString());
                        bundle.putString(LANG_TO_TRANSLATE, lang);
                        loaderManager.restartLoader(TRANSLATOR_LOADER, bundle, TranslatorActivity.this).onContentChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setLanguages() {
        String[] abbrevs = lang.split("-");
        firstLang.setText(getResources().getString(this.getResources().getIdentifier(abbrevs[0], "string", this.getPackageName())));
        secondLang.setText(getResources().getString(this.getResources().getIdentifier(abbrevs[1], "string", this.getPackageName())));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LANG_TO_TRANSLATE,lang);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new TranslatorLoader(this, args.getString(TEXT_TO_TRANSLATE), args.getString(LANG_TO_TRANSLATE));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data == null)
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        else
            translate.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                input.setText("");
                translate.setText("");
                break;
            case R.id.replace:
                String[] abbrevs = lang.split("-");
                lang = abbrevs[1] + "-" + abbrevs[0];
                setLanguages();
                break;
        }
    }
}
