

/**
 * Created by Benjamin on 8.6.2015.
 */
        package ba.hera.praksa;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivity;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, List<String>> _listDataChild;
    public ArrayList<Projekat> projekti = new ArrayList<>();

    public ExpandableListAdapter(Context context, List<String> listDataHeader,HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        final Button button = (Button) convertView.findViewById(R.id.BtnGoTab);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(projekti != null)
                {
                    //Toast.makeText(_context, projekti.get(groupPosition).ID, Toast.LENGTH_LONG).show();
                    SharedPreferences settings = _context.getSharedPreferences("Config", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("SelectedProjekatID", projekti.get(groupPosition).ID);
                    editor.apply();
                    StartNextActivity(projekti.get(groupPosition).ID);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;        //bilo true
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;   //bilo na true
    }

    public void SetListaProjekata(ArrayList<Projekat> projekatList)
    {
        if(projekatList != null) {
            projekti = new ArrayList<>();
            projekti = projekatList;
        }
    }
    public void StartNextActivity(String ID)
    {
        Intent intent = new Intent(_context, GrafActivity.class);
        _context.startActivity(intent);
    }

}
