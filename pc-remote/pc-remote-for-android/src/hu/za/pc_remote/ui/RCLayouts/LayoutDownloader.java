package hu.za.pc_remote.ui.RCLayouts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import hu.za.pc_remote.R;
import hu.za.pc_remote.RCLayoutsManagement.LayoutListItem;
import hu.za.pc_remote.RCLayoutsManagement.NetworkManager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/20/11
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class LayoutDownloader extends Activity {

    private ListView listView = null;
    ArrayAdapter<LayoutListItem> adapter = null;
    NetworkManager networkManager = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutdownloader);

        adapter = new ArrayAdapter<LayoutListItem>(LayoutDownloader.this, R.layout.simplelistitem);

        listView = (ListView) findViewById(R.id.LayoutDownloaderListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new SaveClickListener());

        Button refreshButton = (Button) findViewById(R.id.refreshLayoutsButton);
        refreshButton.setOnClickListener(new RefreshListener());

    }

    private void refreshAdapter() {
        if (networkManager == null)
            return;

        final ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loading), true);

        new Thread() {
            @Override
            public void run() {
                final List<LayoutListItem> l = networkManager.listLayouts();
                runOnUiThread(
                        new Runnable() {
                            public void run() {
                                adapter.clear();
                                if (l != null)
                                    for (LayoutListItem item : l) {
                                        adapter.add(item);
                                    }
                                dialog.dismiss();
                            }
                        }
                );
            }
        }.start();
    }

    private class RefreshListener implements View.OnClickListener {

        public void onClick(View view) {
            EditText editText = (EditText) findViewById(R.id.UrlEditText);
            final String hostUrl = editText.getText().toString();
            networkManager = new NetworkManager(hostUrl);

            refreshAdapter();
        }
    }

    public class SaveClickListener implements ListView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LayoutListItem item = adapter.getItem(i);

            if (networkManager != null) {
                networkManager.saveLayout(item.getId());
            }
        }
    }
}