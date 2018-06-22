package com.example.tc.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;

public class MIneButton extends AppCompatButton {

    public MIneButton(Context context) {super(context);}

    private String value;
    public boolean revealed=false;

    public void setx(){this.value="X";}

    public void reveal() {
        setBackgroundColor(Color.parseColor("white"));
        setTextColor(Color.parseColor("black"));
        if (this.value == "X") {
            setText("X");
            MainActivity.gameover=true;
            setEnabled(false);
        }
        else {
            setText(value);
            setEnabled(false);
            this.revealed=true;
        }
    }

    public String getvalue(){ return this.value;}

    public void setValue(int count) {
        String a = Integer.toString(count);
        this.value=a;
    }
}