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

import com.instatrivia.trivia.game.components.TriviaFragment;
import com.instatrivia.trivia.game.components.TriviaItemViewModel;
import com.instatrivia.trivia.game.components.TriviaViewModel;
import com.instatrivia.trivia.game.data.network.Repository;
import dagger.Component;

/**
 * Dagger 2 component. Being used for creating generated code that injects #{@link Repository} and
 * #{@link TriviaItemViewModel} into #{@link TriviaFragment} and  #{@link TriviaViewModel}
 */
@TriviaScope
@Component(modules = {TriviaModel.class, RepositoryModule.class})
public interface TriviaComponent {

    /**
     * Injects provided objects into #{@link TriviaFragment}
     */
    void inject(TriviaFragment triviaFragment);

    /**
     * Injects provided objects into #{@link TriviaViewModel}
     */
    void inject(TriviaViewModel triviaViewModel);
}
