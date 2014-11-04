package mx.com.sct.checkyourrute.comsult.from.to.activity;

import java.util.ArrayList;
import java.util.List;

import mx.com.sct.checkyourrute.R;
import mx.com.sct.checkyourrute.json.service.GplacesService;
import android.app.Activity;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ConsultFromToActivity extends Activity {
	private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
//	private EditText location,country,temperature,humidity,pressure;
//	private HandleJSON obj;
	private GplacesService gPlacesService;
	private RelativeLayout formLayout;
	private List<EditText> editList; 
	private List<Button> buttonList; 
	private List<TextView> textList; 
	private int indexDestination=0;
	private OnClickListener btnclick;
	private int labelIndex=100;
	private int buttonIndex=200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_from_to);
		formLayout = (RelativeLayout) findViewById (R.id.relative_form_layout);
		editList = new ArrayList<EditText>();
		buttonList = new ArrayList<Button>();
		textList= new ArrayList<TextView>();
		btnclick = new OnClickListener() {

		    @Override
		    public void onClick(View view) {
		    	int indexDestination = view.getId();
		    	try{
		    	formLayout.removeView(textList.get((indexDestination-1-labelIndex)));		    	
		    	textList.remove(indexDestination-1-labelIndex);
		    	formLayout.removeView(buttonList.get(indexDestination-1));
		    	buttonList.remove(indexDestination-1);
		    	formLayout.removeView(editList.get((indexDestination-1-buttonIndex)));		    	
		    	editList.remove(indexDestination-1-buttonIndex);
		    	}catch(Exception e){
		    		Log.e("onClick", e.toString());
		    	}
		    }
		};
	}
	
	public void consultFromTo(View view){
		gPlacesService = new GplacesService();
		gPlacesService.fetchJSON();
	}
	
	public void add(View view){
		
		indexDestination = editList.size();
		
		if (indexDestination<6){
				
			
			
			RelativeLayout.LayoutParams p3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView label = new TextView(this);
			label.setId(labelIndex + (textList.size()+1));
			label.setText("X");
			
			p3.addRule(RelativeLayout.BELOW, textList.size()>0?textList.get(textList.size()-1).getId():R.id.txt_a);
			label.setLayoutParams(p3);
	
			formLayout.addView(label);
			textList.add(label);
			
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
			
			EditText editText = new EditText(this);
			editText.setId(editList.size()+1);
			editText.setHint("Escribe aqui");
			
			p.addRule(RelativeLayout.BELOW, indexDestination>0?editList.get(editList.size()-1).getId():R.id.txt_from);
			p.addRule(RelativeLayout.RIGHT_OF, textList.get(textList.size()-1).getId());
			editText.setLayoutParams(p);
			formLayout.addView(editText);
			editList.add(editText);
		
			RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT);
	 
			Button removeButton = new Button(this);
			removeButton.setText("X");
			removeButton.setId(buttonIndex+(buttonList.size()+1));
			removeButton.setOnClickListener(btnclick);
			p1.addRule(RelativeLayout.RIGHT_OF, indexDestination+1);
			p1.addRule(RelativeLayout.BELOW, indexDestination>0?editList.get(indexDestination).getId():R.id.txt_from);
	
			removeButton.setLayoutParams(p1);
			
			formLayout.addView(removeButton);
			buttonList.add(removeButton);
		}	
	}
	
	private void updateForm(){
		
	}
}
