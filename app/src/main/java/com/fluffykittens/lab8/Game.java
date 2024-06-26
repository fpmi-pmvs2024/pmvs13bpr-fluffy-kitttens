package com.fluffykittens.lab8;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fluffykittens.lab8.data.RecordData;
import com.fluffykittens.lab8.data.RecordRepository;

public class Game extends AppCompatActivity implements View.OnClickListener {

    DrawView drawView;
    GameState gameState;
    RelativeLayout gameButtons;

    Button down;
    Button left;
    Button right;
    Button rotateAc;
    FrameLayout game;
    Button pause;
    TextView score;
    Button difficultyToggle;
    Handler handler;
    Runnable loop;
    int delayFactor;
    int delay;
    int delayLowerLimit;

    RecordRepository recordRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameState = new GameState(24, 20, TetraminoType.getRandomTetramino());

        drawView = new DrawView(this, gameState);
        drawView.setBackgroundColor(Color.WHITE);

        game = new FrameLayout(this);
        gameButtons = new RelativeLayout(this);

        delay = 500;
        delayLowerLimit = 200;
        delayFactor = 2;

        left = new Button(this);
        left.setText(R.string.left);
        left.setId(R.id.left);

        right = new Button(this);
        right.setText(R.string.right);
        right.setId(R.id.right);

        rotateAc = new Button(this);
        rotateAc.setText(R.string.rotate_ac);
        rotateAc.setId(R.id.rotate_ac);

        pause = new Button(this);
        pause.setText(R.string.pause);
        pause.setId(R.id.pause);

        score = new TextView(this);
        score.setText(R.string.score);
        score.setId(R.id.score);
        score.setTextSize(20);

        difficultyToggle = new Button(this);
        difficultyToggle.setText(R.string.easy);
        difficultyToggle.setId(R.id.difficulty);

        down = new Button(this);
        down.setText(R.string.down);
        down.setId(R.id.down);


        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams leftButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams rightButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams downButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams pausebutton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams scoretext = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams speedbutton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams forDownButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        gameButtons.setLayoutParams(rl);
        gameButtons.addView(left);
        gameButtons.addView(right);
        gameButtons.addView(rotateAc);
        gameButtons.addView(pause);
        gameButtons.addView(score);
        gameButtons.addView(difficultyToggle);
        gameButtons.addView(down);

        leftButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        rightButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        downButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        downButton.addRule(RelativeLayout.ABOVE, down.getId());

        pausebutton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        pausebutton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        scoretext.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        scoretext.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        speedbutton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        speedbutton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        forDownButton.addRule(RelativeLayout.ABOVE, RelativeLayout.TRUE);
        forDownButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        forDownButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        left.setLayoutParams(leftButton);
        right.setLayoutParams(rightButton);
        rotateAc.setLayoutParams(downButton);
        pause.setLayoutParams(pausebutton);
        score.setLayoutParams(scoretext);
        down.setLayoutParams(forDownButton);
        difficultyToggle.setLayoutParams(speedbutton);

        game.addView(drawView);
        game.addView(gameButtons);
        setContentView(game);

        View leftButtonListener = findViewById(R.id.left);
        leftButtonListener.setOnClickListener(this);

        View rightButtonListener = findViewById(R.id.right);
        rightButtonListener.setOnClickListener(this);

        View rotateACButtonListener = findViewById(R.id.rotate_ac);
        rotateACButtonListener.setOnClickListener(this);

        View pauseButtonListener = findViewById(R.id.pause);
        pauseButtonListener.setOnClickListener(this);

        View speedButtonListener = findViewById(R.id.difficulty);
        speedButtonListener.setOnClickListener(this);

        View downButtonListener = findViewById(R.id.down);
        downButtonListener.setOnClickListener(this);

        recordRepository = new RecordRepository(this);

        handler = new Handler(Looper.getMainLooper());

        loop = new Runnable() {
            public void run() {
                if (gameState.status) {
                    if (!gameState.pause) {
                        boolean success = gameState.moveFallingTetraminoDown();
                        if (!success) {
                            gameState.paintTetramino(gameState.falling);
                            gameState.lineRemove();

                            gameState.pushNewTetramino(TetraminoType.getRandomTetramino());

                            if (gameState.score % 10 == 9 && delay >= delayLowerLimit) {
                                delay = delay / delayFactor + 1;
                            }
                            gameState.incrementScore();
                        }
                        drawView.invalidate();
                        handler.postDelayed(this, delay);
                    } else {
                        handler.postDelayed(this, delay);
                    }
                } else {
                    // Вызываем AsyncTask после завершения цикла
                    new DatabaseTask(Game.this).execute();
                    pause.setText(R.string.start_new_game);
                }
            }
        };

        loop.run();
    }

    @Override
    public void onClick(View action) {
        if (action == left) {
            gameState.moveFallingTetraminoLeft();

        } else if (action == right) {
            gameState.moveFallingTetraminoRight();

        } else if (action == rotateAc) {
            gameState.rotateFallingTetraminoAntiClock();

        } else if (action == down) {
          gameState.falling.moveDown();

        } else if (action == pause) {
            if (gameState.status) {
                if (gameState.pause) {
                    gameState.pause = false;
                    pause.setText(R.string.pause);

                } else {
                    pause.setText(R.string.play);
                    gameState.pause = true;

                }
            } else {
                pause.setText(R.string.start_new_game);
            }
        } else if (action == difficultyToggle) {
            if (!gameState.difficultMode) {
                delay = delay / delayFactor;
                gameState.difficultMode = true;
                difficultyToggle.setText(R.string.hard);

            } else {
                delay = delay * delayFactor;
                difficultyToggle.setText(R.string.easy);
                gameState.difficultMode = false;

            }
        }
    }

    void setRecordIntoDatabase() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        recordRepository.insertRecord(
                username,
                gameState.score,
                new RecordRepository.InsertCallback() {
                    @Override
                    public void onInsertSuccess(RecordData record) {
                        Log.d("GAME", "SUCCESS INSERTION INTO DATABASE");
                    }

                    @Override
                    public void onInsertFailure(String message) {
                        Log.d("GAME", "FAILURE INSERTION INTO DATABASE");
                    }
                }
        );
    }
}
