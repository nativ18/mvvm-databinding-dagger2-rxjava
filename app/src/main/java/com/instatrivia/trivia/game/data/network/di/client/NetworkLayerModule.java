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

package com.instatrivia.trivia.game.data.network.di.client;

import com.instatrivia.trivia.game.data.network.Constants;
import com.instatrivia.trivia.game.data.network.Constants.Difficulty;
import com.instatrivia.trivia.game.data.network.Constants.GameType;
import com.instatrivia.trivia.model.TriviaRequest;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nativ on 22/03/2017.
 */
@NetworkLayerScope
@Module
public class NetworkLayerModule {

    @Provides
    public Retrofit retrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.followRedirects(false);
        builder.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            return chain.proceed(requestBuilder.build());
        });
        OkHttpClient httpClient = builder.build();

        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(Constants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public TriviaRequest triviaSettings() {
        return new TriviaRequest(Constants.PAGE_SIZE, Constants.SCIENCE_CATEGORY,
                Difficulty.EASY.getValue(),
                GameType.MULTIPLE.getValue());

    }

}
