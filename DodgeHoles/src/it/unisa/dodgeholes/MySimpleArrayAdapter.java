package it.unisa.dodgeholes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.Typeface;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  private final String[] values;
	  Typeface tf;

	  public MySimpleArrayAdapter(Context context, String[] values, String font) {
	    super(context, R.layout.my_text_view_list, values);
	    this.context = context;
	    this.values = values;
	    tf = Typeface.createFromAsset(context.getAssets(), font);
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.my_text_view_list, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.txt);
	    textView.setText(values[position]);
	    textView.setTypeface(tf);
	    
	    

	    return rowView;
	  }
	} 