package com.example.tc.minesweeper;

import android.accessibilityservice.GestureDescription;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout rootlayout;

    ArrayList<LinearLayout> rows;
    MIneButton[][] grid;

    int[] arrayx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] arrayy = {-1, 0, 1, -1, 1, -1, 0, 1};
    int xbound;
    int ybound;

    public static int size = 8;

    public static boolean gameover = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootlayout = findViewById(R.id.root);

        setgrid();
        setmine();
        neighbours();

    }

    public void setgrid() {

        rows = new ArrayList<>();
        grid = new MIneButton[size][size];
        rootlayout.removeAllViews();

        for (int i = 0; i < size; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

            linearLayout.setLayoutParams(layoutParams);

            rootlayout.addView(linearLayout);

            rows.add(linearLayout);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                MIneButton button = new MIneButton(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);
                button.setTextSize(30);
                button.setTextColor(Color.parseColor("white"));
                button.setBackgroundResource(R.drawable.button_border);

                button.setOnClickListener(this);

                LinearLayout row = rows.get(i);
                row.addView(button);
                grid[i][j] = button;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!gameover) {
            MIneButton button = (MIneButton) view;
            button.reveal();
            button.revealed=true;
            revealneighbours();
        }
        if (gameover)
            revealallmines();
    }

    public void setmine() {
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            int k = random.nextInt(size);
            int l = random.nextInt(size);
            grid[k][l].setx();
        }
    }

    public void neighbours() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].getvalue() != "X") {
                    int minecount = 0;
                    for (int k = 0; k < 8; k++) {
                        xbound = i + arrayx[k];
                        ybound = j + arrayy[k];
                        if (xbound >= 0 && ybound >= 0 && xbound < 8 && ybound < 8) {
                            if (grid[xbound][ybound].getvalue() == "X")
                                minecount++;
                        }
                    }
                    grid[i][j].setValue(minecount);
                }
            }
        }
    }


    public void revealallmines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].getvalue() == "X")
                    grid[i][j].reveal();
            }
        }
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
    }

    public void revealneighbours() {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid[i][j].revealed)
                {
                    grid[i][j].revealed=false;
                    for (int k = 0; k < 8; k++)
                    {
                        xbound = i + arrayx[k];
                        ybound = j + arrayy[k];
                        if (xbound >= 0 && ybound >= 0 && xbound < 8 && ybound < 8)
                        {
                                if (grid[xbound][ybound].getvalue() != "X")
                                {
                                    grid[xbound][ybound].reveal();
                                    if (grid[xbound][ybound].getvalue() == "0") {
                                        grid[xbound][ybound].revealed = true;
                                        grid[xbound][ybound].revealed = false;
                                        revealneighbours();
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}