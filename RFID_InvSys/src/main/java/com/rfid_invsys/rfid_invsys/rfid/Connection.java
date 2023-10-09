package com.rfid_invsys.rfid_invsys.rfid;

import com.ad.comm.usb.SioUsb2;

public class Connection {
    private final SioUsb2 ADSio;
    public boolean isConnected = false;
    public Connection(SioUsb2 ADSio)
    {
        this.ADSio = ADSio;
    }
    public boolean connect(){
        return this.ADSio.connect();
    }
}
