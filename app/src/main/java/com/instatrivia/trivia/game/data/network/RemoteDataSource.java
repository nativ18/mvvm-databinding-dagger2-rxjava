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

import com.instatrivia.trivia.game.data.network.di.client.NetworkLayerScope;
import com.instatrivia.trivia.game.data.network.di.data.source.DataSourceScope;
import com.instatrivia.trivia.model.TriviaRequest;
import com.instatrivia.trivia.model.TriviaSet;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * A remote database engine retrieves the data using Retrofit client.
 */
@NetworkLayerScope
@DataSourceScope
public class RemoteDataSource extends IDataSource {

    @Inject
    Retrofit mRetrofit;

    @Inject
    TriviaRequest mTriviaRequest;

    /**
     * The lambda inside the defer gets executed only when the subscription occurs at #{@link
     * #retrieve()}
     */
    Observable<TriviaSet> mRequestObservable = Observable.defer(() -> {
        try {
            TriviaClient service = mRetrofit.create(TriviaClient.class);
            Call<TriviaSet> response = service
                    .get(mTriviaRequest.getPageSize(), mTriviaRequest.getCategory(),
                            mTriviaRequest.getDifficulty(), mTriviaRequest.getType(),
                            mTriviaRequest.getEncoding());
            return Observable.just(response.execute().body());
        } catch (Exception e) {
            // redirecting the exception to its Observer
            Observable.error(e);
            return null;
        }
    }).subscribeOn(Schedulers.newThread());

    @Override
    public void retrieve() throws IOException {
        mRequestObservable.subscribe(this::save);

    }

    @Override
    public void save(TriviaSet items) throws IOException {
        EventBus.getDefault().post(items);
    }
}
