//@author Bhavya
package com.example.listviewfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import com.hzh.savefoodies.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity 
{
	
	// an array of countries to display in the list    
    String[] ITEMS_ARY = new String[]
    { 
    		/*"East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
            "Eritrea", "Estonia", "Ethiopia", "Faeroe Islands",
            "Falkland Islands", "Fiji", "Finland", "Afghanistan", "Albania",
            "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
            "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
           "Bangladesh", "Barbados", "Belarus", "Belgium", "Monaco",
            "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar",
          "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Guyana", "Haiti",
          "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
           "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
           "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger",
            "Nigeria", "Niue", "Norfolk Island", "North Korea",
            "Northern Marianas", "Norway", "Oman", "Pakistan", "Palau",
            "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "French Southern Territories", "Gabon", "Georgia", "Germany",
            "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada",
            "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
            "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico",
            "Micronesia", "Moldova", "Bosnia and Herzegovina", "Botswana",
            "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino",
            "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
            "South Africa", "South Georgia and the South Sandwich Islands",
            "South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
            "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
            "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
            "United Arab Emirates", "United Kingdom", "United States",
            "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
            "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
            "Virgin Islands", "Wallis and Futuna", "Western Sahara",
            "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso",
            "Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada",
            "Cape Verde", "Cayman Islands", "Central African Republic", "Chad",
            "Chile", "China", "Reunion", "Romania", "Russia", "Rwanda",
            "Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis",
            "Saint Lucia", "Saint Pierre and Miquelon", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Christmas Island",
            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
            "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus",
            "Czech Republic", "Democratic Republic of the Congo", "Denmark",
            "Djibouti", "Dominica", "Dominican Republic",
            "Former Yugoslav Republic of Macedonia", "France", "French Guiana",
            "French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Yemen",
            "Yugoslavia", "Zambia", "Zimbabwe" ,"Aaaaaaa"  */
    		"��ʳ-�׷�                                          116�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-��ͷ                                          221�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-���                                          312�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-����                                          106 �󿨣�ÿһ�ٿˣ�",
    		"��ʳ-����Ƭ                                     367�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-������                                     76�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-����                                          284�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-���ӣ������ڣ�                   227�󿨣�ÿһ�ٿˣ�",
    		"��ʳ-������                                     472�󿨣�ÿһ�ٿˣ�",
    		"����-���⣨�ݣ�                            143�󿨣�ÿһ�ٿˣ� ",
    		"����-���ȳ�                                     212�󿨣�ÿһ�ٿˣ�",
    		"����-���ظ���                                 133�󿨣�ÿһ�ٿˣ�",
    		"����-ţ�⣨�ݣ�                            106�󿨣�ÿһ�ٿˣ�",
    		"����-����                                         330�󿨣�ÿһ�ٿˣ�",
    		"����-��С��                                    278�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-ƻ��                                         52�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-�㽶                                         91�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-��                                              44�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-��                                              47�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-����                                         51�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-����                                         43�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-��ݮ                                         30�󿨣�ÿһ�ٿˣ�",
    		"ˮ��-����                                         41�󿨣�ÿһ�ٿˣ�",
    		"�߲�-�㹽                                         19�󿨣�ÿһ�ٿˣ�",
    		"�߲�-ľ��                                         21�󿨣�ÿһ�ٿˣ�",
    		"�߲�-����                                         24�󿨣�ÿһ�ٿˣ�",
    		"�߲�-����                                         21�󿨣�ÿһ�ٿˣ�",
    		"�߲�-�ƹ�                                         15�󿨣�ÿһ�ٿˣ�",
    		"�߲�-��ײ�                                    17�󿨣�ÿһ�ٿˣ�",
    		"�ϵ»�����                                       220�󿨣�ÿһ�ٿˣ�",
    		"�ϵ»���У����           	   334�󿨣�ÿһ�ٿˣ�",
    		"���ͼ�ʿ����                               324�󿨣�ÿһ�ٿˣ�",
    		"�������㼦                                  234�󿨣�ÿһ�ٿˣ�",
    		"��������                                      234�󿨣�ÿһ�ٿˣ�"
    		 };
   
    // unsorted list items 
    ArrayList<String> mItems;
    
    // array list to store section positions
    ArrayList<Integer> mListSectionPos;
    
	// array list to store listView data
	ArrayList<String> mListItems;
    
    // custom list view with pinned header 
    PinnedHeaderListView mListView;  
    
    // custom adapter
    PinnedHeaderAdapter mAdaptor;
    
    // search box
    EditText mSearchView;
    
    // loading view
    ProgressBar mLoadingView;
    
    // empty view
    TextView mEmptyView;
    
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		// UI elements
		setupViews();
		
		// Array to ArrayList 
		mItems=new ArrayList<String>(Arrays.asList(ITEMS_ARY));
		mListSectionPos=new ArrayList<Integer>();
		mListItems=new ArrayList<String>();
		
		// for handling configuration change 		
		if(savedInstanceState!=null)
		{
			mListItems=savedInstanceState.getStringArrayList("mListItems");
			mListSectionPos=savedInstanceState.getIntegerArrayList("mListSectionPos");
			
			if(mListItems!=null && mListItems.size()>0 && mListSectionPos!=null && mListSectionPos.size()>0)
				setListAdaptor();
						
			String constraint=savedInstanceState.getString("constraint");			
			if(constraint!=null && constraint.length()>0)
			{
				mSearchView.setText(constraint);
				setIndexBarViewVisibility(constraint);
			}
			
		}
		else
		{
			new Poplulate().execute(mItems);
		}
		
	}
	
	private void setupViews() 
	{
		setContentView(R.layout.main_act);
		mSearchView=(EditText)findViewById(R.id.search_view);
		mLoadingView=(ProgressBar)findViewById(R.id.loading_view);
		mListView = (PinnedHeaderListView) findViewById(R.id.list_view);
		mEmptyView=(TextView)findViewById(R.id.empty_view);
		
	}
	
	
	//	I encountered an interesting problem with a TextWatcher listening for changes in an EditText.
	//	The afterTextChanged method was called, each time, the device orientation changed. 
	//	An answer on Stackoverflow let me understand what was happening: Android recreates the activity, and 
	//	the automatic restoration of the state of the input fields, is happening after onCreate had finished, 
	//	where the TextWatcher was added as a TextChangedListener.The solution to the problem consisted in adding 
	//	the TextWatcher in onPostCreate, which is called after restoration has taken place
	//
	//  http://stackoverflow.com/questions/6028218/android-retain-callback-state-after-configuration-change/6029070#6029070
	//  http://stackoverflow.com/questions/5151095/textwatcher-called-even-if-text-is-set-before-adding-the-watcher
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		mSearchView.addTextChangedListener(filterTextWatcher);
		super.onPostCreate(savedInstanceState);
	}

	private void setListAdaptor()
	{
		// create instance of PinnedHeaderAdapter and set adapter to list view
		mAdaptor=new PinnedHeaderAdapter(this,mListItems,mListSectionPos);
    	mListView.setAdapter(mAdaptor);    	

    	// set header view
    	View mPinnedHeaderView = LayoutInflater.from(this).inflate(R.layout.section_row_view, mListView, false);
    	mListView.setPinnedHeaderView(mPinnedHeaderView);
    	   	
    	// set index bar view
    	IndexBarView mIndexBarView =(IndexBarView) LayoutInflater.from(this).inflate(R.layout.index_bar_view, mListView, false);
    	mIndexBarView.setData(mListView,mListItems,mListSectionPos);   
    	mListView.setIndexBarView(mIndexBarView);
    	  
    	// set preview text view
    	View mPreviewTextView=LayoutInflater.from(this).inflate(R.layout.preview_view, mListView, false);
    	mListView.setPreviewView(mPreviewTextView);
    	
    	// for configure pinned header view on scroll change
    	mListView.setOnScrollListener(mAdaptor);
	}
	 
	private TextWatcher filterTextWatcher = new TextWatcher() 
	{
			
	    public void afterTextChanged(Editable s) {
	    	String str=s.toString();
	    	if(mAdaptor!=null && str!=null)
	    	mAdaptor.getFilter().filter(str);
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        
	    }

	}; 
		
	
	public class ListFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			// NOTE: this function is *always* called from a background thread, and
            // not the UI thread.
           
            FilterResults result = new FilterResults();
            	
 	            if(constraint != null && constraint.toString().length() > 0)
 	            {
 	            	
 	               ArrayList<String> filt=new ArrayList<String>();
 	               ArrayList<String> Items=new ArrayList<String>();
 	                synchronized(this)
 	                {
 	                    Items=mItems;
 	                
	 	                for(int i = 0;i<Items.size(); i++)
	 	                {
	 	                   String item = Items.get(i);
	 	                   if(item.toLowerCase(Locale.getDefault()).startsWith(constraint.toString().toLowerCase(Locale.getDefault())))
	 	                   {
	 	                    	 	filt.add(item);
	 	                   }
	 	                }
	 	                
	 	                result.count = filt.size();
	 	                result.values = filt;
 	               }
 	            }
 	            else
 	            {
 	            
 	                synchronized(this)
 	                {
 	                    result.count = mItems.size();
 	                    result.values = mItems;
 	                }
 	            	
 	            }
 	            return result;
            
           
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,FilterResults results) 
		{
				
				ArrayList<String> filtered = (ArrayList<String>)results.values;
				setIndexBarViewVisibility(constraint.toString());
				//sort array and extract sections in background Thread
				new Poplulate().execute(filtered);
				
		}
		
	}
	
	private void setIndexBarViewVisibility(String constraint)
	{
		// hide index bar for search results
		if(constraint!=null && constraint.length()>0)
			mListView.hideIndexBarView();
		else
			mListView.showIndexBarView();
	}
	
	// sort array and extract sections in background Thread here we use AsyncTask
	private class Poplulate extends AsyncTask<ArrayList<String>, Void, Void>
	{
		
		private void showLoading(View content_view,View mLoadingView,View mEmptyView) 
		{
			content_view.setVisibility(View.GONE);
			mLoadingView.setVisibility(View.VISIBLE);
			mEmptyView.setVisibility(View.GONE);
		}
		
		private void showContent(View content_view,View mLoadingView,View mEmptyView) 
		{
			content_view.setVisibility(View.VISIBLE);
			mLoadingView.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.GONE);
		}
		
		private void showEmptyText(View content_view,View mLoadingView,View mEmptyView) 
		{
			content_view.setVisibility(View.GONE);
			mLoadingView.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.VISIBLE);
		}
		
		
		@Override
		protected void onPreExecute() 
		{
			//show loading indicator
			showLoading(mListView,mLoadingView,mEmptyView);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(ArrayList<String>... params) 
		{
			mListItems.clear();
			mListSectionPos.clear();
			ArrayList<String> mItems=params[0];
			if(mItems.size()>0)
			{
	        	// NOT forget to sort array
	            Collections.sort(mItems);
	        		            
	            int i=0;
				String prev_section="";
				while(i<mItems.size())
				{
					String current_item=mItems.get(i).toString();
					String current_section = current_item.substring(0, 1).toUpperCase(Locale.getDefault());
					if(!prev_section.equals(current_section))
					{
						mListItems.add(current_section);
						mListItems.add(current_item);
						mListSectionPos.add(mListItems.indexOf(current_section));// array list of section positions
						prev_section=current_section;
					}
					else
					{
						mListItems.add(current_item);
					}
					i++;
				}
	    	   
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			if(!isCancelled())
			{
				if(mListItems.size()<=0)
				{
					showEmptyText(mListView, mLoadingView, mEmptyView);
				}
				else
				{
					setListAdaptor();
				    showContent(mListView,mLoadingView,mEmptyView);
				}
			}
			super.onPostExecute(result);
		} 
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		if(mListItems!=null && mListItems.size()>0)
			outState.putStringArrayList("mListItems", mListItems);
		
		if(mListSectionPos!=null && mListSectionPos.size()>0)
			outState.putIntegerArrayList("mListSectionPos", mListSectionPos);
		
		String search_text=mSearchView.getText().toString();		
		if(search_text!=null && search_text.length()>0)
			outState.putString("constraint",search_text);
		
		super.onSaveInstanceState(outState);
	}

}
