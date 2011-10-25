package hu.za.pc_remote.ui.RCBuilder;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import hu.za.pc_remote.common.KeyCode;
import hu.za.pc_remote.common.RCAction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/13/11
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCXmlParser extends XMLReaderAdapter {

    Context context;

    private static final String tableTag = "table";
    private static final String rowTag = "row";
    private static final String buttonTag = "button";
    private static final String buttonText = "text";
    private static final String buttonKeyCode = "key";
    private static final String actionTypeAttr = "actiontype";
    private static final String touchPadTag = "touchpad";

    private View result = null;

    private TouchPad touchPad = null;
    private TableLayout table = null;
    private TableRow row = null;

    public RCXmlParser(XMLReader xmlReader, Context context) {
        super(xmlReader);
        this.context = context;
    }

    public View getResult(){
        return result;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Log.i("start element", localName);

        if (localName.equals(tableTag)) {
            table = new TableLayout(context);
        } else if (localName.equals(rowTag)) {
            if(table != null){
                row = new TableRow(context);
                table.addView(row);
            }
        } else if (localName.equals(buttonTag)) {
            if (row != null) {
                String s = atts.getValue(actionTypeAttr);
                RCAction action = new RCAction();
                action.type = RCAction.Type.valueOf(s);
                action.arguments = new Serializable[1];
                String key = atts.getValue(buttonKeyCode);
                action.arguments[0] = KeyCode.valueOf(key);
                RCButton button = new RCButton(action, context);
                button.setText(atts.getValue(buttonText));
                row.addView(button);
            }
        } else if (localName.equals(touchPadTag)) {
            touchPad = new TouchPad(context);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Log.i("end element", localName);

        if (localName.equals(tableTag)) {
            result = table;
        } else if (localName.equals(touchPadTag)) {
            result = touchPad;
        }
    }
}
