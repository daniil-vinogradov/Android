package ru.vinogradov.translator.api;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslatorResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("text")
    private List<String> text;

    public TranslatorResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
