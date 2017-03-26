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

package com.instatrivia.trivia.utils;

import com.instatrivia.trivia.game.components.TriviaFragment;
import com.instatrivia.trivia.game.components.TriviaViewModel;
import com.instatrivia.trivia.game.components.di.DaggerTriviaComponent;
import com.instatrivia.trivia.game.components.di.RepositoryModule;
import com.instatrivia.trivia.game.components.di.TriviaModel;
import com.instatrivia.trivia.model.TriviaItem;

/**
 * Created by nativ on 19/03/2017.
 */

public class Injector {

    public static void injectTriviaViewModel(TriviaFragment triviaFragment, TriviaItem triviaItem) {
        // injects the VM into the fragment. You could later on create this link however you desire.
        // E.g - add logic and so on
        DaggerTriviaComponent.builder()
                .triviaModel(new TriviaModel(triviaItem))
                .repositoryModule(new RepositoryModule())
                .build().inject(triviaFragment);
    }

    public static void injectRepository(TriviaViewModel triviaViewModel) {
        // injects the trivia items into the view pager. You could later to send different set of trivia items -
        // science / nature / history ...
        DaggerTriviaComponent.builder().repositoryModule(new RepositoryModule())
                .triviaModel(new TriviaModel(null)).build().inject(triviaViewModel);
    }
}
