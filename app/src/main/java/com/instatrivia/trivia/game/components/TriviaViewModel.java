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

import com.instatrivia.trivia.game.data.network.Repository;
import com.instatrivia.trivia.game.data.network.Repository.FirstPageEvent;
import com.instatrivia.trivia.model.TriviaItem;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;
import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A View Model pattern class that doesn't use Data Binding. Instead it uses RXJava along with
 * method referencing(#{@link TriviaViewModel::start}).
 *
 * Sometimes you cannot use the native Data Binding mechanism because the activity or fragment are
 * being used just as containers. For example - our #{@link TriviaActivity} only wraps a ViewPager
 * which is the wrapper of our data representing views.
 *
 * In this case we mimic the Data Binding mechanism - when we retrieve the items from the server we
 * notifying a pre subscribed method of the Model link which then uses the second level ViewModel of
 * the internal UI component - #{@link TriviaItemViewModel}
 */
public class TriviaViewModel {

    /**
     * An object that responsible on retrieving items from server or local storage. It notifying its
     * subscribers by broadcasting event #{@link FirstPageEvent};
     */
    @Inject
    Repository mRepository;
    /**
     * An Observable that updates its subscribers when a new list of items is ready for ui
     * rendering.
     */
    private ReplaySubject<ArrayList<TriviaItem>> mTriviaItemsDispatcher = ReplaySubject
            .createWithSize(1);
    /**
     * Reference for a subscriber that needs to be dispose relative to life cycle event.
     */
    private Disposable mSubscription;

    /**
     * Lifecycle event that sets this object state to its initial state.
     */
    public void start(Consumer<ArrayList<TriviaItem>> newItemsCollector) throws IOException {
        mSubscription = mTriviaItemsDispatcher.subscribe(newItemsCollector);
        mRepository.start();
        EventBus.getDefault().register(this);
    }

    /**
     * Lifecycle event that sets this object state to its final state.
     */
    public void stop() {
        mSubscription.dispose();
        mRepository.stop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Collect event that contains the availability of new items.
     */
    @Subscribe
    public void onFirstPageGotten(FirstPageEvent firstPageRetrieved) {
        mTriviaItemsDispatcher.onNext(firstPageRetrieved.mItems);
    }

}
