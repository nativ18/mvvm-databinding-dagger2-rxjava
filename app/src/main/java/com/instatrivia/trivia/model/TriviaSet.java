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

package com.instatrivia.trivia.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by nativ on 20/03/2017.
 */
public class TriviaSet {

    @SerializedName("results")
    @Expose
    ArrayList<TriviaItem> mItems;
    @SerializedName("response_code")
    @Expose
    private int responseCode;

    // region Getters region
    public ArrayList<TriviaItem> getmItems() {
        return mItems;
    }

    // endregion
}
