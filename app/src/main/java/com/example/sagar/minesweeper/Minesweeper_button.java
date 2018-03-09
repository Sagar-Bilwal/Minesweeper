package com.example.sagar.minesweeper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Created by SAGAR on 02-02-2018.
 */

public class Minesweeper_button extends AppCompatButton
{
    int isbomb=0;int result=0;int button_num=0;int x=-1;int y=-1;int flag_set=0;
    public  Minesweeper_button(Context context)
    {
        super(context);
    }
    public void behaviour(int isbomb)
    {
        this.isbomb=isbomb;
    }
    public int check()
    {
        if(isbomb==-1) {
            result=-1;
        }
        return result;
    }
    public void put_num(int button_num)
    {
        this.button_num=button_num;
    }

    public int get_num()
    {
        return button_num;
    }
    public void put_x(int x)
    {
        this.x=x;
    }

    public int get_x()
    {
        return x;
    }

    public void setFlag_set(int flag_set) {
        this.flag_set = flag_set;
    }

    public int getFlag_set() {
        return flag_set;
    }

    public void put_y(int y)
    {
        this.y=y;
    }

    public int get_y()
    {
        return y;
    }
}
