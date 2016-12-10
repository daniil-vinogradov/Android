package ru.vino.movies.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

public interface Restorable {

    void saveState(Bundle bundle);

    void restoreState(@Nullable Bundle bundle);
}