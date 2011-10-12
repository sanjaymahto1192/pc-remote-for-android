package hu.za.pc_remote.ui.RCBuilder;

import android.view.View;
import hu.za.pc_remote.common.RCAction;
import hu.za.pc_remote.transport.ConnectionHandlingService;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/6/11
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCClickHandler implements View.OnClickListener {

    private ConnectionHandlingService service;
    private RCAction prototype;

    public RCClickHandler(ConnectionHandlingService service, RCAction prototype){
        this.service = service;
        this.prototype = prototype;
    }

    public void onClick(View view) {
        service.sendAction(prototype);
    }
}
