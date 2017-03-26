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

package com.instatrivia.trivia.game.components.di;

import com.instatrivia.trivia.game.components.TriviaItemViewModel;
import com.instatrivia.trivia.model.TriviaItem;
import dagger.Module;
import dagger.Provides;
import java.io.UnsupportedEncodingException;

/**
 * Dagger 2 module. Provides #{@link TriviaItemViewModel} for DI purposes.
 */
@TriviaScope
@Module
public class TriviaModel {

    private TriviaItem mTriviaItem;

    public TriviaModel(TriviaItem triviaItem) {
        mTriviaItem = triviaItem;
    }

    /**
     * Provides the injected object. Being used by the #{@link TriviaComponent} for the code
     * generation that is the "glue" between the different players.
     */
    @Provides
    public TriviaItemViewModel triviaViewModel() {
        try {
            return new TriviaItemViewModel(mTriviaItem);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                    String.format("Exception while data binding model TriviaItem. %s",
                            e.getMessage()));
        }
    }

}
