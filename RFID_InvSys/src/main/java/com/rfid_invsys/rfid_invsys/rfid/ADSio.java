package com.rfid_invsys.rfid_invsys.rfid;

import com.ad.comm.*;
import com.ad.comm.usb.SioUsb2;
import com.ad.rcp.ProtocolPacket;
import com.rfid_invsys.rfid_invsys.SignalGateway.SignalGateway;

public class ADSio {
    private static final ADSio INSTANCE = new ADSio();
    private static final SioBase sio = new SioUsb2();
    SignalGateway sg = SignalGateway.getInstance();
    private ADSio() {}
    public static ADSio getInstance(){
        return INSTANCE;
    }
    public SioUsb2 getSio() {
        return (SioUsb2)sio;
    }
    public void setListener(){
        sio.setOnCommListener(this.OnCommListener());
    }
    private OnCommListener OnCommListener() {
        return new OnCommListener() {
            @Override
            public void onStatus(Object arg0, StatusEventArg arg1) {
                System.out.println("Object: " + arg0.toString() + " status: " + CommStatus.format(arg1.getStatus()) + "  "
                        + arg1.getMsg());
            }

            @Override
            public void onReceived(Object arg0, ByteEventArg arg1) {
                sg = SignalGateway.getInstance();
                byte[] receiveBytes = arg1.getData();
                System.out.println("recv data Len: "+receiveBytes.length+" Recv	data:"+ ProtocolPacket.ByteArrayToHexString(receiveBytes,0,receiveBytes.length));
                sg.rcp.dealPacketByte(receiveBytes);
            }
        };
    }
}
