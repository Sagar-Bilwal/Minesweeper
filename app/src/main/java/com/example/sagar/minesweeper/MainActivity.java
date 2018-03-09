package com.example.sagar.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    LinearLayout root_layout;int size=8;ArrayList<Integer> bomb_fix1;ArrayList<Integer> bomb_fix2;
    ArrayList<Integer> bomb_fix3;Minesweeper_button placement[][];int count_bomb=0;int count_disabled=0;
    int count_bomb2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root_layout=findViewById(R.id.root_layout);
        root_layout.setBackgroundResource(R.drawable.gradient);
        placement=new Minesweeper_button[size][size];
        bomb_fix3=new ArrayList<>();
        bomb_fix2=new ArrayList<>();
        bomb_fix1=new ArrayList<>();
        shuffle();
        layout();
    }
    public void shuffle()
    {
        for (int x=0;x<size;x++)
        {
            bomb_fix1.add(x);
            bomb_fix2.add(x);
            bomb_fix3.add(x);
        }
        Collections.shuffle(bomb_fix1);
        Collections.shuffle(bomb_fix2);
        Collections.shuffle(bomb_fix3);
    }
    public void reset()
    {
        root_layout.removeAllViews();
        placement=new Minesweeper_button[size][size];
        bomb_fix3=new ArrayList<>();
        bomb_fix2=new ArrayList<>();
        bomb_fix1=new ArrayList<>();
        count_disabled=0;
        count_bomb=0;
        shuffle();
        layout();
    }

    public void layout()
    {
        for(int x=0;x<size;x++)
        {
            LinearLayout row_layout=new LinearLayout(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            row_layout.setLayoutParams(params);
            for(int y=0;y<size;y++)
            {
                Minesweeper_button button=new Minesweeper_button(this);
                LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(param);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                row_layout.addView(button);
                placement[x][y]=button;
                button.setBackgroundResource(R.drawable.button);
                button.put_x(x);
                button.put_y(y);
            }
            root_layout.addView(row_layout);
        }
        allot();
        check();
    }
    public int count_bomb()
    {
        for(int x=0;x<size;x++)
        {
            for(int y=0;y<size;y++)
            {
                if(placement[x][y].check()==-1)
                {
                    count_bomb2++;
                }
            }
        }
        return count_bomb2;
    }

    public void allot()
    {
        int a,b;
        for(int x=0;x<size;x++)
        {
            a=bomb_fix1.get(x);
            b=bomb_fix2.get(x);
            placement[a][b].behaviour(-1);
        }

        for(int x=0;x<size/3;x++)
        {
            a=bomb_fix2.get(x);
            b=bomb_fix3.get(x);
            placement[b][a].behaviour(-1);
        }
    }
    public void lost()
    {
        for(int x=0;x<size;x++)
        {
            for(int y=0;y<size;y++)
            {
                if(placement[x][y].check()==-1)
                {
                    placement[x][y].setBackgroundResource(R.drawable.bomb);
                }
                placement[x][y].setEnabled(false);
            }
        }
        Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
    }
    public void won()
    {
        for(int x=0;x<size;x++)
        {
            for(int y=0;y<size;y++)
            {
                placement[x][y].setEnabled(false);
            }
        }
        Toast.makeText(this,"You Won",Toast.LENGTH_LONG).show();
    }
    public void check()
    {
        for(int x=0;x<size;x++)
        {
            for (int y=0;y<size;y++)
            {
                if(placement[x][y].isbomb!=-1)
                {
                    check_top(x,y);
                    check_topleft(x,y);
                    check_topright(x,y);
                    check_bottom(x,y);
                    check_bottomleft(x,y);
                    check_bottomright(x,y);
                    check_left(x,y);
                    check_right(x,y);
                }
                placement[x][y].put_num(count_bomb);
                count_bomb=0;
            }
        }
    }

    public void check_top(int x,int y)
    {
        if((y-1)>=0)
        {
            if(placement[x][y-1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_topleft(int x,int y)
    {
        if((y-1)>=0 && (x-1)>=0)
        {
            if(placement[x-1][y-1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_topright(int x,int y)
    {
        if((y-1)>=0 && (x+1)<size)
        {
            if(placement[x+1][y-1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_bottom(int x,int y)
    {
        if((y+1)<size)
        {
            if(placement[x][y+1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_bottomright(int x,int y)
    {
        if((y+1)<size && (x+1)<size)
        {
            if(placement[x+1][y+1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_bottomleft(int x,int y)
    {
        if((y+1)<size && (x-1)>=0)
        {
            if(placement[x-1][y+1].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_right(int x,int y)
    {
        if((x+1)<size)
        {
            if(placement[x+1][y].isbomb==-1)
                count_bomb++;
        }
    }
    public void check_left(int x,int y)
    {
        if((x-1)>=0)
        {
            if(placement[x-1][y].isbomb==-1)
                count_bomb++;
        }
    }
    public void reveal_nearby(int x,int y)
    {
        if((x-1)>=0 &&placement[x-1][y].get_num()!=0)
        {
            placement[x-1][y].setText(placement[x-1][y].get_num()+"");
            placement[x-1][y].setEnabled(false);
            placement[x-1][y].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((x+1)<size&&placement[x+1][y].get_num()!=0)
        {
            placement[x+1][y].setText(placement[x+1][y].get_num()+"");
            placement[x+1][y].setEnabled(false);
            placement[x+1][y].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y+1)<size && (x-1)>=0&&placement[x-1][y+1].get_num()!=0)
        {
            placement[x-1][y+1].setText(placement[x-1][y+1].get_num()+"");
            placement[x-1][y+1].setEnabled(false);
            placement[x-1][y+1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y+1)<size && (x+1)<size&&placement[x+1][y+1].get_num()!=0)
        {
            placement[x+1][y+1].setText(placement[x+1][y+1].get_num()+"");
            placement[x+1][y+1].setEnabled(false);
            placement[x+1][y+1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y+1)<size&&placement[x][y+1].get_num()!=0)
        {
            placement[x][y+1].setText(placement[x][y+1].get_num()+"");
            placement[x][y+1].setEnabled(false);
            placement[x][y+1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y-1)>=0 && (x+1)<size&&placement[x+1][y-1].get_num()!=0)
        {
            placement[x+1][y-1].setText(placement[x+1][y-1].get_num()+"");
            placement[x+1][y-1].setEnabled(false);
            placement[x+1][y-1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y-1)>=0 && (x-1)>=0&&placement[x-1][y-1].get_num()!=0)
        {
            placement[x-1][y-1].setText(placement[x-1][y-1].get_num()+"");
            placement[x-1][y-1].setEnabled(false);
            placement[x-1][y-1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
        if((y-1)>=0&&placement[x][y-1].get_num()!=0)
        {
            placement[x][y-1].setText(placement[x][y-1].get_num()+"");
            placement[x][y-1].setEnabled(false);
            placement[x][y-1].setBackgroundResource(R.drawable.gradient);
            count_disabled++;
        }
    }

    public void expand(Minesweeper_button click)
    {
        int x =click.get_x();
        int y= click.get_y();
        reveal_nearby(x,y);
        if(click.get_num()==0){
            click.setText("");
            reveal_nearby(x,y);
        click.setEnabled(false);
            click.setBackgroundResource(R.drawable.gradient);
        }
        if((x-1)>=0 &&placement[x-1][y].get_num()==0 && placement[x-1][y].isEnabled())
        {
            reveal_nearby(x-1,y);
            expand(placement[x-1][y]);
        }
        if((x+1)<size && placement[x+1][y].get_num()==0&& placement[x+1][y].isEnabled())
        {
            reveal_nearby(x+1,y);
            expand(placement[x+1][y]);
        }
        if((y+1)<size && (x-1)>=0)
        {
            if(placement[x-1][y+1].get_num()==0&& placement[x-1][y+1].isEnabled()) {
                reveal_nearby(x - 1, y + 1);
                expand(placement[x - 1][y + 1]);
            }
        }
        if((y+1)<size && (x+1)<size &&placement[x+1][y+1].get_num()==0&&placement[x+1][y+1].isEnabled())
        {
            reveal_nearby(x+1,y+1);
            expand(placement[x+1][y+1]);
        }
        if((y+1)<size&&placement[x][y+1].get_num()==0&&placement[x][y+1].isEnabled())
        {
            reveal_nearby(x,y+1);
            expand(placement[x][y+1]);
        }
        if((y-1)>=0 && (x+1)<size &&placement[x+1][y-1].get_num()==0 &&placement[x+1][y-1].isEnabled())
        {
            reveal_nearby(x+1,y-1);
            expand(placement[x+1][y-1]);
        }
        if((y-1)>=0 && (x-1)>=0 &&placement[x-1][y-1].get_num()==0 &&placement[x-1][y-1].isEnabled())
        {
            reveal_nearby(x-1,y-1);
            expand(placement[x-1][y-1]);
        }
        if((y-1)>=0&&placement[x][y-1].get_num()==0&&placement[x][y-1].isEnabled())
        {
            reveal_nearby(x,y-1);
            expand(placement[x][y-1]);
        }
    }
    boolean flag_set=false;
    public boolean check_flag()
    {
        for(int x=0;x<size;x++)
        {
            for (int y=0;y<size;y++)
            {
                if(placement[x][y].isbomb==-1)
                {
                    if(placement[x][y].getFlag_set()==1)
                      flag_set=true;
                    else
                        flag_set=false;
                }
            }
        }
        return flag_set;
    }

    @Override
    public void onClick(View v) {
        Minesweeper_button click=(Minesweeper_button)v;
        if(click.getFlag_set()==1)
        {
            click.setText("");
            click.setBackgroundResource(0);
            click.setBackgroundResource(R.drawable.button);
            click.setFlag_set(0);
        }
        if(check_flag())
        {
            won();
        }
        if(click.check()==-1)
        {
            lost();
        }else if(click.get_num()!=0 && click.get_num()!=-1)
        {
            String set=click.get_num()+"";
            click.setText(set);
            click.setEnabled(false);
            count_disabled++;
            click.setBackgroundResource(R.drawable.gradient);
        }
        else if(click.get_num()==0)
            expand(click);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.easy)
        {
            size=8;
            item.setChecked(true);
            reset();
        }else if(id==R.id.medium)
        {
            size=10;
            item.setChecked(true);
            reset();
        }else if(id==R.id.difficult)
        {
            size=14;
            item.setChecked(true);
            reset();
        }
        else if(id==R.id.reset)
        {
            reset();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View v) {
        Minesweeper_button button1= (Minesweeper_button)v;
        if(button1.getFlag_set()==0){
        button1.setBackgroundResource(R.drawable.flag);
        button1.setPadding(2,4,4,4);
        button1.setFlag_set(1);
        }
        else if(button1.getFlag_set()==1)
        {
            button1.setText("");
            button1.setBackgroundResource(0);
            button1.setBackgroundResource(R.drawable.button);
            button1.setFlag_set(0);
        }
        return true;
    }
}
