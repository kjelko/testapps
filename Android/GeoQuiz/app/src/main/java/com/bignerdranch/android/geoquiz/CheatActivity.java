package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    // This is a new line!

    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    private static final String KEY_IS_ANSWER_SHOWN = "is_answer_shown";
    private static final String KEY_IS_ANSWER_BUTTON_VISIBLE = "is_answer_button_visible";

    // Intent extras
    private boolean mAnswerIsTrue;

    // UI widgets
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mApiLevelTextView;

    // Persistent state
    private boolean mIsAnswerShown;
    private int mAnswerStrId;
    private int mShowAnswerVisibility = View.VISIBLE;

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN);
            mShowAnswerVisibility = savedInstanceState.getInt(KEY_IS_ANSWER_BUTTON_VISIBLE);
        }

        // Retrieve answer value from parent activity
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerStrId = mAnswerIsTrue ? R.string.true_button : R.string.false_button;

        mAnswerTextView = findViewById(R.id.answer_text_view);

        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAnswerShown = true;
                setAnswerShownResult();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerVisibility = View.INVISIBLE;
                            mShowAnswerButton.setVisibility(mShowAnswerVisibility);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerVisibility = View.INVISIBLE;
                    mShowAnswerButton.setVisibility(mShowAnswerVisibility);
                }
            }
        });

        // Challenge: display API level
        mApiLevelTextView = findViewById(R.id.api_level_text_view);
        mApiLevelTextView.setText("API Level " + Build.VERSION.SDK_INT);

        // Update initial state of widgets
        if (mIsAnswerShown) {
            setAnswerShownResult();
        }
        mShowAnswerButton.setVisibility(mShowAnswerVisibility);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_ANSWER_SHOWN, mIsAnswerShown);
        savedInstanceState.putInt(KEY_IS_ANSWER_BUTTON_VISIBLE, mShowAnswerVisibility);
    }

    private void setAnswerShownResult() {
        mAnswerTextView.setText(mAnswerStrId);

        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, data);
    }
}
