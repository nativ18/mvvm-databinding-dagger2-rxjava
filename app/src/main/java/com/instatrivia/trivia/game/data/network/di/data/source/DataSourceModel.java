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

package com.instatrivia.trivia.game.data.network.di.data.source;

import com.instatrivia.trivia.game.data.network.LocalDataSource;
import com.instatrivia.trivia.game.data.network.RemoteDataSource;
import com.instatrivia.trivia.game.data.network.di.client.DaggerNetworkLayerComponent;
import com.instatrivia.trivia.game.data.network.di.client.NetworkLayerModule;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nativ on 19/03/2017.
 */
@DataSourceScope
@Module
public class DataSourceModel {

    @Provides
    public LocalDataSource localDataSource() {
        return new LocalDataSource();
    }

    @Provides
    public RemoteDataSource remoteDataSource() {
        RemoteDataSource remoteDataSource = new RemoteDataSource();
        DaggerNetworkLayerComponent.builder().networkLayerModule(new NetworkLayerModule()).build()
                .inject(remoteDataSource);

        return remoteDataSource;
    }
}
