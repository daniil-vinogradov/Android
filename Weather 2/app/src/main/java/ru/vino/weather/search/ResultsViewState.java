package ru.vino.weather.search;

import java.util.List;

import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public class ResultsViewState {

    private List<AutocompleteResponseModel> results;

    public List<AutocompleteResponseModel> getResults() {
        return results;
    }

    public void setResults(List<AutocompleteResponseModel> results) {
        this.results = results;
    }
}
