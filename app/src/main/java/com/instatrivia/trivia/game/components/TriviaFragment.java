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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.instatrivia.R;
import com.instatrivia.databinding.FragTriviaBinding;
import com.instatrivia.trivia.game.components.di.TriviaScope;
import javax.inject.Inject;

/**
 * This fragment defines the ViewPager's pages. Each fragment represent a trivia question
 * and uses Data Binding along with its ViewModel #{@link TriviaItemViewModel} to render its views.
 */
@TriviaScope
public class TriviaFragment extends Fragment {

    @Inject
    TriviaItemViewModel mTriviaViewModel;
    private FragTriviaBinding mFragTriviaBinding;

    public static TriviaFragment getInstance() {
        return new TriviaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.frag_trivia, container, false);

        if (mFragTriviaBinding == null) {
            mFragTriviaBinding = FragTriviaBinding.bind(root);
        }

        mFragTriviaBinding.setTriviaItem(mTriviaViewModel);

        return root;
    }
}
