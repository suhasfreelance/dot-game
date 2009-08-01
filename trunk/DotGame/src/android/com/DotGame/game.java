package android.com.DotGame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class game extends Activity {
	
	
	//Screen Elements
	EditText player1name;
	EditText player2name;
	Spinner player1color;
	Spinner player2color;
	ToggleButton playermode;
	Button backToMain;
	Button startGame;
	
	//Variables
	String colors[][] = {
			{"Red", 	"#FF0000"},
			{"Orange", 	"#FF6600"},
			{"Yellow", 	"#FFFF00"},
			{"Green", 	"#00FF00"},
			{"Blue", 	"#0000FF"},
			{"White", 	"#FFFFFF"},
			{"Pink",	"#FF0099"}
	};
	ArrayList<String> colorSpinner = new ArrayList<String>();
	
	static //Game Variables
	String color1 = "";
	static String color2 = "";
	public static int width;
	public static int height;
	//private static Context context;
	Dot[][] grid;
	ArrayList<Line> lines = new ArrayList<Line>();
	ArrayList<Box> boxes = new ArrayList<Box>();
	private static GameView GameView;
	private static RelativeLayout layout;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startoptions);
        
        initializeStartOptions();
    }
    /** Called when screen is flipped **/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	
    	//Preserve Texty things!
    	player1name.setText(player1name.getText().toString());
		player2name.setText(player2name.getText().toString());
		
    }
    
    
    /*====================================================
     * 			Sets up Start Option Screen
     *===================================================*/
    public void initializeStartOptions() {
		player1name = (EditText)this.findViewById(R.id.playerOneName);
		player1color = (Spinner)this.findViewById(R.id.playerOneColor);
		player2name = (EditText)this.findViewById(R.id.playerTwoName);
		player2color = (Spinner)this.findViewById(R.id.playerTwoColor);
		playermode = (ToggleButton)this.findViewById(R.id.twoPlayerMode);
		backToMain = (Button)this.findViewById(R.id.backToMain);
		startGame = (Button)this.findViewById(R.id.beginGame);
				
		player1name.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(player1name.getText().toString().length() > 10) {
					player1name.setText(player1name.getText().toString().substring(0,11));
				}
				return false;
			}
			
		});
		player2name.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(player2name.getText().toString().length() > 10) {
					player2name.setText(player2name.getText().toString().substring(0,11));
				}
				return false;
			}
			
		});
		
		setupSpinner(player1color, " ");
		setupSpinner(player2color, " ");
		
		playermode.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				
				if((playermode.getText()).toString().equalsIgnoreCase("ON")) {
					player2name.setVisibility(0);
					player2color.setVisibility(0);
				}
				else if((playermode.getText()).toString().equalsIgnoreCase("OFF")) {
					player2name.setVisibility(4);
					player2color.setVisibility(4);
				}
				Log.d("2 Players:", (playermode.getText()).toString());
			}
		});
		Log.d("Initialized Start Options", "SUCCESSFUL");
		
		backToMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		startGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				initializeGame();
			}
		});
	}
    
    
    /*====================================================
     * 				Sets up Spinners
     *===================================================*/
    public void setupSpinner(final Spinner spinName, String removeColor) {
    	
    	colorSpinner.clear();
    	for(int j = 0; j < colors.length; j++){
    		if(!colorSpinner.contains(colors[j][0])) {
    			colorSpinner.add(colors[j][0]);
    		}
    		/*if(removeColor != null) {
    			colorSpinner.remove(removeColor);
    		}*/
    	}
    	ArrayAdapter<String> File_Types_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colorSpinner);
    	File_Types_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinName.setAdapter(File_Types_Adapter);
    	
    	spinName.setOnItemSelectedListener(new OnItemSelectedListener(){
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id){
    			//Set FLAG to go to correct next screen
    			if(spinName == player1color) {
    				color1 = colors[position][1];
    				//setupSpinner(player2color, colors[position][0]);
    			}
    			else if(spinName == player2color) {
    				color2 = colors[position][1];
    				//setupSpinner(player1color, colors[position][0]);
    			}
    		}
			public void onNothingSelected(AdapterView<?> arg0) {
				//DO NOTHING
			}
    	});
	}
    
    
    /*==================================================
     * 				Begin Game
     *=================================================*/
    public void initializeGame() {
    	setContentView(R.layout.gameboard);
    	//context = game.this;
        
        //Get screen size info
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        width = d.getWidth();
        height = d.getHeight();
    	
        grid = new Dot[menu.getXDim()][menu.getYDim()];
                
        for(int i = 0; i < menu.getXDim(); i++) {
        	for(int j = 0; j < menu.getYDim(); j++) {
        		grid[i][j] = new Dot(i, j, menu.getXDim(), menu.getYDim());
        	}
        }
        
        //Instantiate Screen
    	//Plot Nodes and links
    	GameView = new GameView(this, grid, lines, boxes, (player1name.getText()).toString(), (player2name.getText()).toString());
    	layout = new RelativeLayout(this);
    	layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
    	
    	//Add nodes and links to the screen
    	layout.addView(GameView);
    	
    	setContentView(layout);
    }
    
    public static int getWidth(){
    	return width;
    }
    
    public static int getHeight(){
    	return height;
    }
    
    public static String getPlayer1Color() {
    	return color1;
    }
    
    public static String getPlayer2Color() {
    	return color2;
    }
}