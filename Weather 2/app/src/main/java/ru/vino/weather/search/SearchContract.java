package ru.vino.weather.search;


import java.util.List;

import ru.vino.weather.base.BasePresenter;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public interface SearchContract {

    interface ISearchView {
        void presentResults(List<AutocompleteResponseModel> results);

        void showError();
    }

    abstract class AbstractSearchPresenter extends BasePresenter<ISearchView> {
        public abstract void search(String q);
    }

}
