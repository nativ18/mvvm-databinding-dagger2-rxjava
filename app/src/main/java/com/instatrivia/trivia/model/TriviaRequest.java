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

import com.instatrivia.trivia.game.data.network.Constants;

/**
 * Created by nativ on 22/03/2017.
 */
public class TriviaRequest {

    private int pageSize;
    private int category;
    private String difficulty;
    private String type;
    private String encoding;

    public TriviaRequest(int pageSize, int category, String difficulty, String type) {
        this.pageSize = pageSize;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;

        encoding = Constants.ENCODING;
    }

    // region Getters region
    public int getPageSize() {
        return pageSize;
    }

    public int getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }

    public String getEncoding() {
        return encoding;
    }
    // endregion
}
