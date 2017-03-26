/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.instatrivia.trivia.game.components;

import static dagger.internal.Preconditions.checkNotNull;

import android.databinding.ObservableField;
import com.instatrivia.trivia.game.components.di.TriviaScope;
import com.instatrivia.trivia.model.TriviaItem;
import io.reactivex.Observable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ViewModel for the Trivia fragment(#{@link TriviaFragment}).
 * <p>
 * This ViewModel only exposes {@link ObservableField}s, so it doesn't need to extend
 * {@link android.databinding.BaseObservable} and updates are notified automatically. See
 * how to deal with more complex scenarios.
 */
@TriviaScope
public class TriviaItemViewModel {

    public final ObservableField<String> question = new ObservableField<>();

    public final ObservableField<String> answer1 = new ObservableField<>();

    public final ObservableField<String> answer2 = new ObservableField<>();

    public final ObservableField<String> answer3 = new ObservableField<>();

    public final ObservableField<String> answer4 = new ObservableField<>();

    private final Map<Integer, ObservableField<String>> mIndexToFieldMap;

    public TriviaItemViewModel(TriviaItem item)
            throws UnsupportedEncodingException, NullPointerException {
        checkNotNull(item);

        mIndexToFieldMap = new HashMap<>();
        setupIndexerMap();

        int correctAnswerIndex = new Random().nextInt(4);

        Observable<Integer> values = Observable.range(0, 4);
        values.subscribe((index) -> {
            if (index == correctAnswerIndex) {
                mIndexToFieldMap.get(index).set(getFormatted(index, item.getCorrectAnswer()));
            } else {
                int wrongAnswerIndex = index > correctAnswerIndex ? index - 1 : index;
                mIndexToFieldMap.get(index)
                        .set(getFormatted(index, item.getIncorrectAnswers().get(wrongAnswerIndex)));
            }
        });

        question.set(decode(item.getQuestion()));
    }

    private String decode(String str) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(str, "UTF-8");
    }

    private void setupIndexerMap() {
        mIndexToFieldMap.put(0, answer1);
        mIndexToFieldMap.put(1, answer2);
        mIndexToFieldMap.put(2, answer3);
        mIndexToFieldMap.put(3, answer4);
    }

    private String getFormatted(int index, String str) throws UnsupportedEncodingException {
        return String.format("%s. %s", index, decode(str));
    }
}
