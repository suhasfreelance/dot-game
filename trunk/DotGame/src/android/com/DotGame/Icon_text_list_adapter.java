package android.com.DotGame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Icon_text_list_adapter extends BaseAdapter { 
	 /** Remember our context so we can use it when constructing views. */
    private Context mContext;

    private List<Icon_text> mItems = new ArrayList<Icon_text>();

    public Icon_text_list_adapter(Context context) {
         mContext = context;
    }

    public void addItem(Icon_text it) { mItems.add(it); }

    public void setListItems(List<Icon_text> lit) { mItems = lit; }

    /** @return The number of items in the */
    public int getCount() { return mItems.size(); }

    public Object getItem(int position) { return mItems.get(position); }

    /** Use the array index as a unique id. */
    public long getItemId(int position) {
         return position;
    }

    /** @param convertView The old view to overwrite, if one is passed
     * @returns a IconifiedTextView that holds wraps around an IconifiedText */
    public View getView(int position, View convertView, ViewGroup parent) {
         Icon_text_view btv;
         if (convertView == null) {
              btv = new Icon_text_view(mContext, mItems.get(position));
         } else { // Reuse/Overwrite the View passed
              // We are assuming(!) that it is castable!
              btv = (Icon_text_view) convertView;
              btv.setText(mItems.get(position).getText());
              btv.setIcon(mItems.get(position).getIcon());
         }
         return btv;
    }
}