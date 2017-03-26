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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.instatrivia.BuildConfig;
import com.instatrivia.R;
import com.instatrivia.trivia.game.components.di.TriviaModel;
import com.instatrivia.trivia.model.TriviaItem;
import com.instatrivia.trivia.utils.Injector;
import java.io.IOException;
import java.util.ArrayList;
import timber.log.Timber;

/**
 * The app main activity. It encapsulates the view pager that contains all of the trivia items. The
 * Architecture of the app is MVVM, it means that the Model, which is in our case - this Activity
 * must know the ViewModel Which is #{@link TriviaViewModel}.
 */
public class TriviaActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager mItemsPager;

    /**
     * The ViewModel link. Inside this class all of the magic of Data Binding occurs.
     */
    private TriviaViewModel mTriviaViewModel;

    /**
     * A ViewPager adapter that contains manage the rendering of each TriviaItem.
     */
    private TriviaAdapter mTriviaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTriviaViewModel = new TriviaViewModel();

        Injector.injectRepository(mTriviaViewModel);

        setup();
    }

    private void setup() {
        mTriviaAdapter = new TriviaAdapter(getSupportFragmentManager());
        mItemsPager.setAdapter(mTriviaAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mTriviaViewModel.start(mTriviaAdapter::addItems);
        } catch (IOException e) {
            Timber.e("exception while resuming app: %s", e.getMessage());

            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTriviaViewModel.stop();
    }

    public class TriviaAdapter extends FragmentStatePagerAdapter {

        ArrayList<TriviaItem> mTriviaItems;

        /**
         * for sync loading of items
         */
        public TriviaAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Updating the adapter when there're available items. Being called by method reference at
         * #{@link TriviaViewModel#start}
         */
        public void addItems(ArrayList<TriviaItem> items) {
            Timber.i("notifyDataSetChanged with %s items", items.size());
            mTriviaItems = items;
            TriviaActivity.this.runOnUiThread(() -> notifyDataSetChanged());
        }

        @Override
        public int getCount() {
            return mTriviaItems == null || mTriviaItems.isEmpty() ? 0 : mTriviaItems.size();
        }

        /**
         * executes Dagger 2 injection. Injecting the ViewModel(#{@link TriviaModel}) that renders
         * the item. The decoupling is being created here. If a change in the way we represent the
         * data need to take place - we could replace the used ViewModel with a new one without
         * breaking other parts of the application.
         */
        @Override
        public TriviaFragment getItem(int position) {

            TriviaFragment fragment = TriviaFragment.getInstance();
            Injector.injectTriviaViewModel(fragment, mTriviaItems.get(position));

            return fragment;
        }
    }
}
