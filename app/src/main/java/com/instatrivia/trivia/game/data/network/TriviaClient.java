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

import com.instatrivia.trivia.model.TriviaSet;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for a free trivia questions api - https://opentdb.com/api_config.php
 */
public interface TriviaClient {

    @GET("/api.php")
    Call<TriviaSet> get(@Query("amount") int amount, @Query("category") int category,
            @Query("difficulty") String difficulty, @Query("type") String type,
            @Query("encode") String encode);
}
