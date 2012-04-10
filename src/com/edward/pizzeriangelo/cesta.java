package com.edward.pizzeriangelo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class cesta extends Activity{
	private Button PedirButton; 
    private Button ResetButton; 
    private Button SeguirButton; 
    private TableLayout tb;



	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);      
	        setContentView(R.layout.cesta);//Inicializacion de las views y otras variables
	        PedirButton = (Button)findViewById(R.id.Pedir_btn);
	        ResetButton  = (Button)findViewById(R.id.buttonreset);
	        SeguirButton  = (Button)findViewById(R.id.Seguir_btn);
	        tb=(TableLayout)findViewById(R.id.tabla_cuerpo);
	        updateLaTabla();
			
			PedirButton.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					sendPedido();
					}
		    	});
			
			ResetButton.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					SQLiteDatabase db = (new DatabaseHelper(cesta.this)).getWritableDatabase();
					ContentValues cv2 = new ContentValues();
					cv2.put(DatabaseHelper.cantidad,0);
					db.update("essen", cv2, null, null);
					db.close();
					updateLaTabla();
				}
		    	});
			SeguirButton.setOnClickListener(new View.OnClickListener() {
				@Override 		    	    		    	    		    	    		    	 
				public void onClick(View v) {
					Intent intentpizza = new Intent(cesta.this,ShowEssen.class);
					intentpizza.putExtra("tipo",0);
		            startActivity(intentpizza);
				}
		    	});
			
	    	
}
	 
	 private void sendPedido(){
		 
	 }
	 
	 private void updateLaTabla(){
		 int count = tb.getChildCount();
		 Log.i("hh",""+count);
		 for (int i = 0; i < count; i++){
			 Log.i("i",""+i);
			 //if (i>2) tb.removeViewAt(i);
		 }
		 SQLiteDatabase db = (new DatabaseHelper(cesta.this)).getWritableDatabase();
			//DatabaseHelper.onUpgrade(db,1,2);
			      
	        Cursor mCursor = db.query("essen", new String[] {DatabaseHelper.name,DatabaseHelper.cantidad,DatabaseHelper.preis}, 
	          		DatabaseHelper.cantidad+">0", null, null, null, null);
			mCursor.moveToFirst();
			List views = new ArrayList();
			if (mCursor.moveToFirst()){
				do{
				LayoutInflater factory1 = LayoutInflater.from(this);
				View view = factory1.inflate(R.layout.tablerow, null);
				TextView textView1 = (TextView) view.findViewById(R.id.textView1p);
				TextView textView2 = (TextView) view.findViewById(R.id.textView2p);
				TextView textView3 = (TextView) view.findViewById(R.id.textView3p);
				textView1.setBackgroundResource(R.drawable.celda_cuerpo);
				textView2.setBackgroundResource(R.drawable.celda_cuerpo);
				textView3.setBackgroundResource(R.drawable.celda_cuerpo);
				textView1.setText(""+mCursor.getString(0));
				textView2.setText(""+mCursor.getInt(1));
				textView3.setText("€"+mCursor.getString(2));
				views.add(view);		
			}while (mCursor.moveToNext());
			}
			for(int i = 0; i < views.size(); i++)
			tb.addView((View) views.get(i));
			//mCursor.moveToNext = mCursor.getString(1);(mCursor.getCount()==0) db.insert("dias",null ,cv);db.update("cesta",cv,"_id=1",null); 
			mCursor.close();
			db.close();  
	 }
}