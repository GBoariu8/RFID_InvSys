package com.rfid_invsys.rfid_invsys.SignalGateway;

import com.ad.comm.SioBase;
import com.ad.comm.usb.SioUsb2;
import com.ad.rcp.RcpBase;
import com.rfid_invsys.rfid_invsys.Controllers.MainController;
import com.rfid_invsys.rfid_invsys.rfid.ADRcp;
import com.rfid_invsys.rfid_invsys.rfid.ADSio;

public class SignalGateway {
    private static final SignalGateway INSTANCE = new SignalGateway();
    public boolean isConnected;
    public ADSio adSio;
    public SioUsb2 sio;
    public ADRcp adRcp;
    public RcpBase rcp;
    public String resmsg;
    public MainController mainController;
    public SignalGateway(){
        isConnected = false;
        adRcp = ADRcp.getInstance();
        rcp = adRcp.getRcp();
        adSio = ADSio.getInstance();
        sio = adSio.getSio();
        resmsg = "";
    }
    public static SignalGateway getInstance(){return INSTANCE;}
}
