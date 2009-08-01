package android.com.DotGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class menu extends Activity {
    
	//Screen Elements
	ListView menu;
	Button back;
	
	//Variables
	Intent gameIntent;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.main);
        
        initialize();
    }
    /*/** Called when screen is flipped *
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    }*/
    
    public void initialize(){
    	//set up ListView
		ListView menu = (ListView)findViewById(R.id.Action_list);
		
		//set up adapter to list view
		Icon_text_list_adapter itla = new Icon_text_list_adapter(this);

        // Add Items to list view.
        itla.addItem(new Icon_text(
               "Start Game", getResources().getDrawable(R.drawable.start)));
        itla.addItem(new Icon_text(
               "Settings / Options", getResources().getDrawable(R.drawable.options)));
        itla.addItem(new Icon_text(
               "Instructions", getResources().getDrawable(R.drawable.info)));
                
        // Display it
        menu.setAdapter(itla); 
        Log.d("Initialized","Waiting for Selection");
        menu.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO: launch activity based on selection	
				switch(arg2){
					case 0: /* Game Choices */
							gameIntent = new Intent(menu.this, game.class);
							startActivity(gameIntent);
						break;
					case 1: /* Settings */
						break;
					case 2: /* Instructions */
							displayInstructions();
						break;
				}					
			}
        });
    }
    
    
    public void displayInstructions(){
    	setContentView(R.layout.instructions);
    	back = (Button)this.findViewById(R.id.backToMain);
    	back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.main);
            	initialize();
            }
    	});
    }
    
    public static int getXDim() {
    	return 8;
    }
    
    public static int getYDim() {
    	return 8;
    }
    
    
}


