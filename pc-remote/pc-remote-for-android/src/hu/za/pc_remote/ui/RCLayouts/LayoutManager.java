package hu.za.pc_remote.ui.RCLayouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import hu.za.pc_remote.R;
import hu.za.pc_remote.RCLayoutsManagement.FileManager;
import hu.za.pc_remote.RCLayoutsManagement.LayoutListItem;

import java.util.List;

import static android.view.View.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/23/11
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LayoutManager extends Activity {

    private ArrayAdapter<LayoutListItem> adapter;
    private TextView empty;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmanager);

        adapter = new ArrayAdapter<LayoutListItem>(this, R.layout.simplelistitem);

        empty = (TextView)findViewById(R.id.LayoutManagerNoResult);

        ListView listView = (ListView) findViewById(R.id.LayoutManagerListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemClieckedListener());

        Button button = (Button) findViewById(R.id.LayoutManagerAddNewButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(LayoutManager.this, LayoutDownloader.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }

    private void refreshAdapter() {
        adapter.clear();
        List<LayoutListItem> list = FileManager.listFiles();
        for (LayoutListItem item : list) {
            adapter.add(item);
        }
        empty.setVisibility(adapter.getCount()==0? VISIBLE:INVISIBLE);
    }

    private class ItemClieckedListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final LayoutListItem item = adapter.getItem(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(LayoutManager.this);
            builder.setTitle(item.getName());
            CharSequence[] options = {getString(R.string.deleteLayout)};
            builder.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    new Thread() {
                        @Override
                        public void run() {
                            FileManager.deleteFile(item);
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Toast.makeText(LayoutManager.this, getString(R.string.layoutDeleted), Toast.LENGTH_SHORT).show();
                                            refreshAdapter();
                                        }
                                    }
                            );
                        }
                    }.start();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}