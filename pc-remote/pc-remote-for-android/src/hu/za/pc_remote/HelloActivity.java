package hu.za.pc_remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.*;
import android.util.Log;
import hu.za.pc_remote.common.RCAction;
import hu.za.pc_remote.transport.ConnectionHandlingService;
import hu.za.pc_remote.ui.ConnectionSettings;

import java.lang.reflect.Type;

public class HelloActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private class KeyBoard extends KeyboardView{

        public KeyBoard(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
