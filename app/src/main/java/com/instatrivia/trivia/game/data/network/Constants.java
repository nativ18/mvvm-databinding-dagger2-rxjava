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


/**
 * Networking constants.
 */
public class Constants {

    public static final int PAGE_SIZE = 20;
    public static final int SCIENCE_CATEGORY = 18;

    public static final String ENCODING = "url3986";
    public static final String ENDPOINT = "https://opentdb.com/";

    public enum Difficulty {
        EASY("easy"), MEDIUM("medium"), HARD("hard");

        String value;

        Difficulty(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }
    }


    public enum GameType {
        MULTIPLE("multiple"), BOOLEAN("boolean");

        String value;

        GameType(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }
    }
}
