package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_IS_CHEATER = "is_cheater";
    private static final String KEY_CHEAT_COUNT = "cheat_count";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";

    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mCheatCountTextView;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;

    // This is a comment
    // This is another comment
    // NEw comment
    // This is another comment

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private Map<Question, Boolean> mResponses = new HashMap<>();

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private int mCheatCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Log.d(TAG, "onCreate(Bundle) called");

        // Inflate the layout
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
            mCheatCount = savedInstanceState.getInt(KEY_CHEAT_COUNT, 0);
        }

        // Retrieve Question model object from array and update TextView with string
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // True button and its corresponding listener
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateTrueFalseButtons();
            }
        });

        // False button and its corresponding listener
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateTrueFalseButtons();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mCheatCountTextView = findViewById(R.id.cheat_count_text_view);

        // Prev button decrements the current index and updates the TextView accordingly
        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1);
                if (mCurrentIndex < 0) mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
                updateTrueFalseButtons();
            }
        });

        // Next button increments the current index and updates the TextView accordingly
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                updateTrueFalseButtons();
            }
        });

        // Set initial state of UI widgets
        updateQuestion();
        updateTrueFalseButtons();
        updateCheatCount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mCheatCount++;
            updateCheatCount();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    // This method is called before onStop(), except when the user presses the Back button
    //
    // Back tells Android the user is done with the activity, so Android wipes the activity from memory
    // completely and does not make any attempt to save data
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
        savedInstanceState.putInt(KEY_CHEAT_COUNT, mCheatCount);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateTrueFalseButtons() {
        boolean alreadyAnswered = mResponses.containsKey(mQuestionBank[mCurrentIndex]);
        mTrueButton.setEnabled(!alreadyAnswered);
        mFalseButton.setEnabled(!alreadyAnswered);
    }

    private void updateCheatCount() {
        mCheatCountTextView.setText("Remaining cheat tokens: " + (3 - mCheatCount));
        if (mCheatCount == 3) {
            mCheatButton.setEnabled(false);
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mResponses.put(mQuestionBank[mCurrentIndex], answerIsTrue);

        int toastMessageStrId = 0;

        if (mIsCheater) {
            toastMessageStrId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                toastMessageStrId = R.string.correct_toast;
            } else {
                toastMessageStrId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, toastMessageStrId, Toast.LENGTH_SHORT).show();

        // Challenge: calculate score at end of quiz
        if (mResponses.size() == mQuestionBank.length) {
            // Calculate percentage
            int correct = 0;
            for (boolean correctAnswer : mResponses.values()) {
                if (correctAnswer) {
                    correct++;
                }
            }
            int score = (int) ((float) correct / mQuestionBank.length * 100);
            Toast toast = Toast.makeText(QuizActivity.this, score + "%", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }
}
