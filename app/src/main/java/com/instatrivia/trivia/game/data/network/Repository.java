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

package com.instatrivia.trivia.game.data.network;

import static com.google.common.base.Preconditions.checkNotNull;

import com.instatrivia.trivia.game.components.TriviaViewModel;
import com.instatrivia.trivia.game.components.di.TriviaScope;
import com.instatrivia.trivia.game.data.network.di.data.source.DataSourceScope;
import com.instatrivia.trivia.model.TriviaItem;
import com.instatrivia.trivia.model.TriviaSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * The app's data provider. Contains the business logic for the data retrieving and caching.
 */
@TriviaScope
@DataSourceScope
@Singleton
public class Repository {

    @Inject
    LocalDataSource localDataSource;
    @Inject
    RemoteDataSource remoteDataSource;
    private LinkedHashSet<TriviaItem> mCache = new LinkedHashSet<>(10);

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void put(TriviaSet items) {
        checkNotNull(items);
        boolean isFirstPage = mCache.isEmpty();
        mCache.addAll(items.getmItems());

        if (isFirstPage) {
            EventBus.getDefault().post(new FirstPageEvent(new ArrayList(mCache)));
        }

        // TODO - persist data
    }

    public void start() throws IOException {
        EventBus.getDefault().register(this);

        localDataSource.retrieve();
        remoteDataSource.retrieve();
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * An event class for connecting the Data - #{@link Repository} and the ViewModel  - #{@link
     * TriviaViewModel} links
     */
    public static class FirstPageEvent {

        public final ArrayList<TriviaItem> mItems;

        public FirstPageEvent(ArrayList<TriviaItem> mCache) {
            this.mItems = mCache;
        }
    }
}
