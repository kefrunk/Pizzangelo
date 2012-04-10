package com.edward.pizzeriangelo;


import java.net.Socket;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private Intent intent;
    private Resources res;
    private Intent ServerIntent;
	ServiceConnection _connection;
    static final int TIME_DIALOG_ID = 0;
    static final int DAYS_DIALOG_ID = 1;
    static final int AGUA_ID = 7;
	private TextView AutoONTxt;
	private Button PizzenButton;
	private Button SalateButton;
	private Button DolciButton;
	private Button DiversiButton;
	private Button PastaButton;
	private Button ReservarButton;
	private Button ConfirmButton;
	private RelativeLayout PantallaConfig;
	private RelativeLayout PantallaGeneral;	
	private String TipoProg = "P1";
	private String orden ="";
	private ProgressDialog dialogwait;
	private Socket socket = null;
    private int count;
    private byte [] ipAddress = new byte[] {(byte)192,(byte)168,(byte)1,(byte)3};
    private int puerto = 5205;
    private String mensText = "Abrir Puerta";
    private boolean finalizada = false;
    private static final int CONFIG = Menu.FIRST;
    private static final int IP = Menu.FIRST + 1;
    private static final int MSG = Menu.FIRST + 2;
    private static final int AGUA = Menu.FIRST + 3;
    private static final int PROG = Menu.FIRST + 4;
    private static final int DIALOGO_IP = 2;
    private static final int DIALOGO_PIZZA = 5;
    private static final int DIALOGO_MSG = 3;
    private static final int ID_DIALOG_LOADING = 4;
    private Toast mToast;
	private SoundPool soundPool;
	private int ID_SOUND_INICIO;
	private int ID_SOUND_E1;
	private int ID_SOUND_E2;
	private int ID_SOUND_E3;
	private int ID_SOUND_E4;
	private int PIZZA=0;
	private int SALAT=1;
	private int DOLCI=2;
	private int DIVERSI=3;
	private int PASTA=4;
    //private InetAddress addr;
	
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
    		R.drawable.nougatpala,
    		R.drawable.nougatpala
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

  String[][] Caption ={PizzaCaption, SalatCaption,DolciCaption,DiversiCaption,PastaCaption};

  String[] PizzaName = {
		  "MARGHERITA","CARDINALE","SALAMI","GUSTOSO","HAWAII","PzMOZZARELLA",
		  "TONNO","SARDELLA","POPEYE","PROVENCIALE","QUATTRO STAGIONI",
		  "RUSTICANA","ANGELO","DIAVOLO","2 STÜCK PIZZASTANGERLN",	  
  };
  String[] PastaName = {
		  "AGLIO OLIO","POMODORO","BOLOGNESE","CARBONARA","SPINACI PANNA"
  };
  String[] DolciName = {
		  "TOPFENTRUDEL MIT SCHLAGOBERS","MOHR IM HEMD","MARMELADENPALATSCHINKEN",
		  "NUTELLAPALATSCHINKEN", "TIRAMISU","ESIMARILLENKÖDEL"
  };
  String[] DiversiName = {
		  "PANIERTE SCHAFKÄSE-SPINAT PALATSCHINKEN",
		  "COMBO KORB MIT DIP SAUCE","CEVAPCICI MIT POMMES",
		  "HÜHNERSCHNITZEL MIT POMMES"
		  
  };
  String[] SalatName = {
		  "KLEINER GEMISCHER SALAT",
		  "GRIECHISCHER BAUERNSALAT",
		  "THUNFISCH SALAT",
		  "MOZZARELLA MIT TOMATEN"
  };
  
  String[][] Namen ={PizzaName, SalatName,DolciName,DiversiName,PastaName};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
       initConfig(); //Inicializacion de las views y otras variables
       
       
       // Definicion de onClick de los botones //

             
       PizzenButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
	    	public void onClick(View v) {
			Intent intentpizza = new Intent(MainActivity.this,ShowEssen.class);
			intentpizza.putExtra("tipo",PIZZA);
            startActivity(intentpizza);
			}
	    });
       
       SalateButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
			public void onClick(View v) {
				Intent intentpizza = new Intent(MainActivity.this,ShowEssen.class);
				intentpizza.putExtra("tipo",SALAT);
				startActivity(intentpizza);
				}
	    });
       DiversiButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
			public void onClick(View v) {
				Intent intentpizza = new Intent(MainActivity.this,ShowEssen.class);
				intentpizza.putExtra("tipo",DIVERSI);
				startActivity(intentpizza);
				}
	    });
       DolciButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
			public void onClick(View v) {
				Intent intentpizza = new Intent(MainActivity.this,ShowEssen.class);
				intentpizza.putExtra("tipo",DOLCI);
				startActivity(intentpizza);
				}
	    });
       PastaButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
			public void onClick(View v) {
				Intent intentpizza = new Intent(MainActivity.this,ShowEssen.class);
				intentpizza.putExtra("tipo",PASTA);
	            startActivity(intentpizza);
				}
	    });
       ConfirmButton.setOnClickListener(new View.OnClickListener() {
			@Override 		    	    		    	    		    	    		    	 
			public void onClick(View v) {
				Intent intentorder = new Intent(MainActivity.this,cesta.class);
				startActivity(intentorder);
				}
	    });

   	
    }
    
	@Override
	public void onResume() {
		super.onResume();		
		//startService(intent);
		//startService(ServerIntent);
		//registerReceiver(broadcastReceiver, new IntentFilter(UpdateService.BROADCAST_ACTION));
	}

	@Override
	public void onPause() {
		super.onPause();
		//unregisterReceiver(broadcastReceiver);
		//stopService(intent); 	
		//stopService(ServerIntent); 
	}	
      	    
    private void initConfig() 
    {
    	
      setContentView(R.layout.main);
      dialogwait = new ProgressDialog(MainActivity.this);
       res = getResources(); 
    	ConfirmButton = (Button) findViewById(R.id.MiniPizza_btn);
    	PizzenButton = (Button) findViewById(R.id.Pizza_btn);
    	SalateButton = (Button) findViewById(R.id.Salate_btn);
    	PastaButton =(Button) findViewById(R.id.Pasta_btn);
    	DolciButton =(Button) findViewById(R.id.Dolci_btn);
    	DiversiButton = (Button) findViewById(R.id.Diversi_btn);
    	//DatabaseHelper.onUpgrade(db,1,2);
    	//initDatabase();
    	SQLiteDatabase db = (new DatabaseHelper(MainActivity.this)).getWritableDatabase();
    	Cursor mCursor = db.query("essen", new String[] {"*"}, 
          		null, null, null, null, null); 
		int dtck=mCursor.getCount();
		mCursor.close();
		if(dtck==0)
		{
		
    	ContentValues cv = new ContentValues();
		cv.put(DatabaseHelper.cantidad,0);
		for (int j=0;j<5;j++){
			for (int i=0;i<Caption[j].length;i++){
				cv.put(DatabaseHelper.name,Namen[j][i]);
				cv.put(DatabaseHelper.tipo,j);
				cv.put(DatabaseHelper.item,i);
				cv.put(DatabaseHelper.zutaten,Caption[j][i]);
				cv.put(DatabaseHelper.image,1);
				cv.put(DatabaseHelper.preis,1);
				db.insert("essen", null, cv);
			}
		}
		}
		db.close();
    }
    

    
}    

    
    
    