package com.edward.pizzeriangelo;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

public class ShowEssen extends Activity{

	private static final int PIZZA = 0;
	private static final int PASTA = 4;
	private static final int DOLCI = 2;
	private static final int DIVERSI = 3;
	private static final int SALAT = 1;
	private Integer[] pics={};
	private String[] caption={};
	private int tipo;
	private Button CestaButton;
	private Button Button_gopizza;
	private Button Button_godiversi;
	private Button Button_gosalat;
	private Button Button_gopasta;
	private Button Button_godolci;

	Integer[] SalatPics = {
    		R.drawable.kleinersalat,
    		R.drawable.greek,
    		R.drawable.thunfischsalat,
    		R.drawable.mozzarellasalat,
    };
	Integer[] PizzaPics = {
    		R.drawable.margherita,
    		R.drawable.cardinale,
    		R.drawable.salami,
    		R.drawable.gustoso,
    		R.drawable.hawaii,
    		R.drawable.mozzarella,
    		R.drawable.tonno,
    		R.drawable.sardella,
    		R.drawable.popeye,
    		R.drawable.provenciale,
    		R.drawable.quattro,
    		R.drawable.rusticana,
    		R.drawable.angelo,
    		R.drawable.diavolo,
    		R.drawable.pizzastangerl
    };
	Integer[] DiversiPics = {
    		R.drawable.palapaniert,
    		R.drawable.combo,
    		R.drawable.cevapcici,
    		R.drawable.huhnerschnitzel
    };
	Integer[] DolciPics = {
    		R.drawable.topfenstrudel,
    		R.drawable.mohr,
    		R.drawable.marmeladenpala,
    		R.drawable.nougatpala,
    		R.drawable.keinbild_thumb,
    		R.drawable.keinbild_thumb
    };
	Integer[] PastaPics = {
    		R.drawable.gnocchiaglioolio,
    		R.drawable.spaghettipomodoro,
    		R.drawable.bolognese,
    		R.drawable.carbonara,
    		R.drawable.spinaci
    };
	
  String[] PizzaCaption = {
		  "MARGHERITA\nTomaten, Käse",
		  "CARDINALE\nTomaten, Käse, Schinken",
		  "SALAMI\nTomaten, Käse, Salami",
		  "GUSTOSO\nTomaten, Käse, Schinken, Mais",
		  "HAWAII\nTomaten, Käse, Rauch-Schinken, Ananas",
		  "MOZZARELLA\nTomaten, Käse, Mozzarella, Tomatenscheiben, Basilikum",
		  "TONNO\nTomaten, Käse, Thunfisch, Zwiebel",
		  "SARDELLA\nTomaten, Käse, Sardellen, Oliven",
		  "POPEYE\nTomaten, Käse, Schafskäse, Blattspinat",
		  "PROVENCIALE\nTomaten, Käse, Schinken, Speck, Pfefferoni, Mais",
		  "QUATTRO STAGIONI\n4 Stationen Margherita mit: Schinken, Salami, Artischocken, Champignons",
		  "RUSTICANA\nTomaten, Käse, Rauchschinken, Spiegelei, Oliven, Artischocken",
		  "ANGELO\nTomaten, Käse, Schinken, Speck, Pfefferoni, Mozzarella",
		  "DIAVOLO\nTomaten, Käse, Schinken, Salami, Zwiebel und scharfe Pfefferoni",
		  "2 STÜCK PIZZASTANGERLN\nMit Knoblauch auf Wunsch auch ohne",	  
  };
  String[] PastaCaption = {
		  "AGLIO OLIO\nOlivenöl, Knoblauch & Oliven",
		  "POMODORO\nTomaten, Knoblauch, Oregano & Basilikum",
		  "BOLOGNESE\nFaschiertes mit pikanter Tomatensauce",
		  "CARBONARA\nSchinken, Speck, Obers & Knoblauch",
		  "SPINACI PANNA\nSchinken, Speck, Obers, Knoblauch, Spinat & Gorgonzolla"
  };
  String[] DolciCaption = {
		  "TOPFENTRUDEL MIT SCHLAGOBERS\n1 Stück - warm serviert",
		  "MOHR IM HEMD\n1 Stück mit Schlagobers",
		  "MARMELADENPALATSCHINKEN\n2 Stück mit Marillenmarmelade & Schlagobers",
		  "NUTELLAPALATSCHINKEN\n2 Stück mit Nutella & Schlagobers",
		  "TIRAMISU\n1 großes Stück hausgemachtes Tiramisu",
		  "ESIMARILLENKÖDEL\n1 Stück"
  };
  String[] DiversiCaption = {
		  "PANIERTE SCHAFKÄSE-SPINAT PALATSCHINKEN\nmit Salatgarnitur und Saucen",
		  "COMBO KORB MIT DIP SAUCE\nGKorb mit gebackenen Zwiebelringen, Mozzarella Sticks und Chicken Wings (auf Wunsch auch scharf erhältlich) - serviert mit einer Dip-Sauce.",
		  "CEVAPCICI MIT POMMES\nMit Salatgarnitur und Saucen",
		  "HÜHNERSCHNITZEL MIT POMMES\nMit Salatgarnitur, in Sesam-Brösel parniert"
		  
  };
  String[] SalatCaption = {
		  "KLEINER GEMISCHER SALAT\nKleiner gemischter Salat\nmit Dressing nach Wahl: Joghurt, American, oder Essig & Öl",
		  "GRIECHISCHER BAUERNSALAT\nGrüner Salat, Gurken, Tomaten,\nOliven, Schafskäse & Zwiebeln mit Olivenöl-Dressing",
		  "THUNFISCH SALAT\nGrüner Salat, Gurken, Tomaten,\nPaprikasalat, Mais, Thunfisch,\nOliven & Zwiebeln mit Dressing nach Wahl: Joghurt, American, oder Essig & Öl",
		  "MOZZARELLA MIT TOMATEN\nGrüner Salat, Tomaten, Mozzarella\n& frisches Basilikum mit Balsamico-Olivenöl-Dressing"
  };
    private ImageView imageView;
    private TextView textView;
    private Button addItemButton;
    private int selected;
    private int cantidad;
	
		/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle bun= getIntent().getExtras();
	        tipo =bun.getInt("tipo");
	        switchtipo(tipo);
	        setContentView(R.layout.dialog_pizza_entry);
	    	CestaButton = (Button) findViewById(R.id.CestaButton);
	    	Button_gopizza = (Button) findViewById(R.id.Button_gopizza);
	    	Button_gopasta = (Button) findViewById(R.id.Button_gopasta);
	    	Button_godolci = (Button) findViewById(R.id.Button_godolci);
	    	Button_godiversi = (Button) findViewById(R.id.Button_godiversi);
	    	Button_gosalat = (Button) findViewById(R.id.Button_gosalat);

	       Gallery ga = (Gallery)findViewById(R.id.Gallery01);
	       ga.setAdapter(new ImageAdapter(this));
	       SQLiteDatabase dbo = (new DatabaseHelper(ShowEssen.this)).getWritableDatabase();
	       Cursor mCursoro = dbo.query("essen", new String[] {DatabaseHelper.cantidad,DatabaseHelper.name}, 
	          		DatabaseHelper.cantidad+">0", null, null, null, null);
			if(mCursoro.moveToFirst()){
				do{
				CestaButton.setText(" "+CestaButton.getText()+mCursoro.getInt(0)+mCursoro.getString(1)+"||");
				}while (mCursoro.moveToNext());
			}
			mCursoro.close();
			dbo.close();
	       //imageView = (ImageView)findViewById(R.id.ImageView01);
	       textView  = (TextView)findViewById(R.id.TextView01);
	       addItemButton  = (Button)findViewById(R.id.addCesta_btn);
	       ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			imageView.setImageResource(pics[arg2]);
				textView.setText(caption[arg2]);
					
					
				}
	        	
	        });    
	        ga.setOnItemSelectedListener(new OnItemSelectedListener() {

	            @Override
	            public void onItemSelected(AdapterView parent, View view,
	                 int position, long id) {
	            	selected=position;
	            	textView.setText(caption[position]);


	               }

	            @Override
	            public void onNothingSelected(AdapterView parent){
	            	textView.setText(caption[0]);

	            }
	              });
	        
	        CestaButton.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentcesta = new Intent(ShowEssen.this,cesta.class);
		            startActivity(intentcesta);
					}
		    	});
	        
	        addItemButton.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					SQLiteDatabase db = (new DatabaseHelper(ShowEssen.this)).getWritableDatabase();
					Cursor mCursor = db.query("essen", new String[] {DatabaseHelper.cantidad}, 
			          		DatabaseHelper.item+"="+ selected +" AND "+ DatabaseHelper.tipo+"="+tipo, null, null, null, null); 
					mCursor.moveToFirst();
					cantidad = mCursor.getInt(0);
					cantidad = cantidad +1;
					ContentValues cv2 = new ContentValues();
					cv2.put(DatabaseHelper.cantidad,cantidad);
					db.update("essen", cv2, "item="+selected +" AND tipo="+tipo, null);
					mCursor.close();
					db.close();
					updateTextView();
					}
		    	});
	        
	        Button_gopizza.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(ShowEssen.this,ShowEssen.class);
					intentpizza.putExtra("tipo",PIZZA);
		            startActivity(intentpizza);
					}
		    	});
	        
	        Button_gopasta.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(ShowEssen.this,ShowEssen.class);
					intentpizza.putExtra("tipo",PASTA);
		            startActivity(intentpizza);
					}
		    	});
	        
	       Button_gosalat.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(ShowEssen.this,ShowEssen.class);
					intentpizza.putExtra("tipo",SALAT);
		            startActivity(intentpizza);
					}
		    	});
	        
	       Button_godiversi.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(ShowEssen.this,ShowEssen.class);
					intentpizza.putExtra("tipo",DIVERSI);
		            startActivity(intentpizza);
					}
		    	});
	       Button_godolci.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(ShowEssen.this,ShowEssen.class);
					intentpizza.putExtra("tipo",DOLCI);
		            startActivity(intentpizza);
					}
		    	});
	             
   	
		}
	    
	    
	    @Override
		public void onResume() {
			super.onResume();		
			Bundle bun= getIntent().getExtras();
	        tipo =bun.getInt("tipo");
	        switchtipo(tipo);
	        updateTextView();
	       }
	
	    private void updateTextView() {
			SQLiteDatabase db = (new DatabaseHelper(ShowEssen.this)).getWritableDatabase();
			Cursor	mCursor = db.query("essen", new String[] {DatabaseHelper.cantidad,DatabaseHelper.name}, 
	          		DatabaseHelper.cantidad+">0", null, null, null, null);
			CestaButton.setText("");
			if(mCursor.moveToFirst()){
				do{
				CestaButton.setText(""+CestaButton.getText()+mCursor.getInt(0)+mCursor.getString(1)+"||");
				}while (mCursor.moveToNext());
			}
			mCursor.close();
			db.close();
		}

		public class ImageAdapter extends BaseAdapter {

	    	private Context ctx;
	    	int imageBackground;
	    	
	    	public ImageAdapter(Context c) {
				ctx = c;
				//TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
				//imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
				//ta.recycle();
			}

			@Override
	    	public int getCount() {
	    		
	    		return pics.length;
	    	}

	    	@Override
	    	public Object getItem(int arg0) {
	    		
	    		return arg0;
	    	}

	    	@Override
	    	public long getItemId(int arg0) {
	    		
	    		return arg0;
	    	}

	    	@Override
	    	public View getView(int arg0, View arg1, ViewGroup arg2) {
	    		ImageView iv = new ImageView(ctx);
	    		iv.setImageResource(pics[arg0]);
	    		iv.setScaleType(ImageView.ScaleType.FIT_XY);
	    		iv.setLayoutParams(new Gallery.LayoutParams(220,193));
	    		iv.setBackgroundResource(imageBackground);
	    		return iv;
	    	}

	    }
	
	

	    private void switchtipo(int tp){
	    	switch(tp){
      		case PIZZA:
      			pics = PizzaPics; 
      			caption = PizzaCaption;
          		break;
      		case PASTA:
      			pics = PastaPics; 
      			caption = PastaCaption;
          		break;
      		case SALAT:
      			pics = SalatPics; 
      			caption = SalatCaption ;
          		break;
      		case DOLCI:
      			pics = DolciPics; 
      			caption = DolciCaption;
          		break;
      		case DIVERSI:
      			pics = DiversiPics; 
      			caption = DiversiCaption;
          		break;
      		}
	    }
        

	    
}
