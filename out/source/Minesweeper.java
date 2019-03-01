import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.guido.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {




public final static int NUM_ROWS = 20;
public final static int NUM_COLS = 20;
private MSButton[][] buttons; //2d array of minesweeper buttons
public final static int NUM_BOMB = 40;
private boolean isLose = false;
private ArrayList <MSButton> bombs = new ArrayList <MSButton> (); //ArrayList of just the minesweeper buttons that are mined

public void setup ()
{
    
    textAlign(CENTER,CENTER);
    
    // make the manager
    Interactive.make( this );
    
    //your code to initialize buttons goes here
    buttons = new MSButton[NUM_ROWS][NUM_COLS];
    for(int r = 0; r < NUM_ROWS; r++)
      for(int c = 0; c < NUM_COLS; c++)
        buttons[r][c] = new MSButton (r, c);
    setBombs();
    
}
public void setBombs()
{
    while (bombs.size() < NUM_BOMB)
    {
      int r = (int)(Math.random() * NUM_ROWS);
      int c = (int)(Math.random() * NUM_COLS);
      if (!bombs.contains(buttons[r][c]))
      {
        bombs.add(buttons[r][c]);
      }
      
      
    }
        
}

public void draw ()
{
    background( 0 );
    if(isWon())
        displayWinningMessage();
    if(isLose())
        displayLosingMessage();
}
public boolean isWon()
{
    //your code here
    return false;
}
public boolean isLose()
{
    //your code here
    return true;
}
public void displayLosingMessage()
{
    //your code here
}
public void displayWinningMessage()
{
    //your code here
}

public class MSButton
{
    private int r, c;
    private float x,y, width, height;
    private boolean clicked, marked;
    private String label;
    
    public MSButton ( int rr, int cc )
    {
        width = 400/NUM_COLS;
        height = 400/NUM_ROWS;
        r = rr;
        c = cc; 
        x = c*width;
        y = r*height;
        label = "";
        marked = clicked = false;
        Interactive.add( this ); // register it with the manager
    }
    public boolean isMarked()
    {
        return marked;
    }
    public boolean isClicked()
    {
        return clicked;
    }
    // called by manager
    
    public void mousePressed () 
    {
        clicked = true;
        marked = false;
        if (mouseButton == RIGHT)
        {
            marked = !marked;
            if(marked == false)
                clicked = false;
        }
        else if(bombs.contains(this))
        {
            isLose = true;
        }
        else if (countBombs(r,c) > 0 )
        {
            setLabel(str(countBombs(r,c)));
        }
        else
        {
            for( int row = r-1; row < r+2; row++)
                for (int col = c-1; col < c+2; col++)
                    if(isValid(row, col) && !buttons[row][col].isClicked())
                        buttons[row][col].mousePressed();
        }
    }

    public void draw () 
    {    
        if (marked)
            fill(0);
        else if( clicked && bombs.contains(this) ) 
            fill(255,0,0);
        else if(clicked)
            fill( 200 );
        else 
            fill( 100 );

        rect(x, y, width, height);
        fill(0);
        text(label,x+width/2,y+height/2);
    }
    public void setLabel(String newLabel)
    {
        label = newLabel;
    }
    public boolean isValid(int r, int c)
    {
        if( r >= 0 && r < NUM_ROWS && c >= 0 && c < NUM_COLS)
            return true;
        return false;
    }
    public int countBombs(int row, int col)
    {
        int numBombs = 0;
        for(int r = row-1; r < row+2; r++)
            for(int c = col -1; c< col+2; c++)
                if(isValid(r,c) && bombs.contains(buttons[r][c]) == true)
                    numBombs++;
        /* if(isValid(row, col-1)&& buttons[row][col-1].isMarked())
            numBombs++;
        if(isValid(row-1, col)&& buttons[row-1][col].isMarked())
            numBombs++;
        if(isValid(row+1, col)&& buttons[row+1][col].isMarked())
            numBombs++;
        if(isValid(row, col+1)&& buttons[row][col+1].isMarked())
            numBombs++;
        if(isValid(row+1, col+1)&& buttons[row+1][col+1].isMarked())
            numBombs++;
        if(isValid(row-1, col-1)&& buttons[row-1][col-1].isMarked())
            numBombs++;
        if(isValid(row-1, col+1)&& buttons[row-1][col+1].isMarked())
            numBombs++;
        if(isValid(row+1, col-1)&& buttons[row+1][col-1].isMarked())
            numBombs++; */ 
        return numBombs;
    }
}
  public void settings() {  size(400, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
