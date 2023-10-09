package com.rfid_invsys.rfid_invsys.Controllers;

import com.ad.rcp.ProtocolPacket;
import com.ad.rcp.RcpBase;
import com.ad.rcp.RcpMM;
import com.rfid_invsys.rfid_invsys.SignalGateway.MACROS;
import com.rfid_invsys.rfid_invsys.SignalGateway.SignalGateway;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import java.util.ArrayList;
import java.util.List;

public class MenuController {
    SignalGateway sg = SignalGateway.getInstance();
    @FXML private MenuItem FXML_Connect;
    @FXML private Label FXML_ConnectionStatus;
    public void Hndl_Exit(){
        Platform.exit();
    }
    public void Hndl_Connect(){
        if(sg.isConnected){
            sg.sio.disConnect();
            sg.isConnected = false;
            FXML_Connect.setText("Connect");
            FXML_ConnectionStatus.setText("Status: NOT CONNECTED");
        }
        else{
            List<UsbDevice> rfidReader = this.findDevices();
            if(rfidReader.size() != 0){
                if(sg.sio.connect(rfidReader.get(0))){
                    System.out.println("OK");
                    sg.isConnected = true;
                    FXML_Connect.setText("Disconnect");
                    FXML_ConnectionStatus.setText("Status: CONNECTED");
                }
            }
        }
    }
    public void Hndl_GetPwrLvl(){
        sg.sio.send(new ProtocolPacket(65535, RcpMM.RCP_MM_GET_TX_PWR, RcpBase.RCP_MSG_CMD, null).ToArray());
    }
    public void Hndl_GetRegion(){
        sg.sio.send(new ProtocolPacket(65535, RcpMM.RCP_MM_GET_REGION, RcpBase.RCP_MSG_CMD, null).ToArray());
    }

    public void Hndl_SetPwrLvl5(){Hndl_SetPwrLvl(5);}
    public void Hndl_SetPwrLvl10(){Hndl_SetPwrLvl(10);}
    public void Hndl_SetPwrLvl15(){Hndl_SetPwrLvl(15);}
    public void Hndl_SetPwrLvl20(){Hndl_SetPwrLvl(20);}
    public void Hndl_SetPwrLvl25(){Hndl_SetPwrLvl(25);}
    public void Hndl_SetPwrLvl30(){Hndl_SetPwrLvl(30);}
    private void Hndl_SetPwrLvl(int PwrLvl){
        byte[] powerLvlBytes = new byte[] { (byte) PwrLvl };
        sg.sio.send(new ProtocolPacket(65535, RcpMM.RCP_MM_SET_TX_PWR, RcpBase.RCP_MSG_CMD, powerLvlBytes).ToArray());
    }

    public void Hndl_SetRegionChina900(){Hndl_SetRegion(MACROS.REGION_CHINA_900M);}
    public void Hndl_SetRegionChina800(){Hndl_SetRegion(MACROS.REGION_CHINA_800M);}
    public void Hndl_SetRegionUS(){Hndl_SetRegion(MACROS.REGION_US);}
    public void Hndl_SetRegionEurope(){Hndl_SetRegion(MACROS.REGION_EUROPE);}
    public void Hndl_SetRegionKorea(){Hndl_SetRegion(MACROS.REGION_KOREA);}
    private void Hndl_SetRegion(byte Region){
        byte[] regionBytes = new byte[] {Region};
        sg.sio.send(new ProtocolPacket(65535, RcpMM.RCP_MM_SET_REGION, RcpBase.RCP_MSG_CMD, regionBytes).ToArray());
    }
    private List<UsbDevice> findDevices() {
        List<UsbDevice> devices = new ArrayList<>();
        try {
            for (Object object : UsbHostManager.getUsbServices().getRootUsbHub().getAttachedUsbDevices()) {
                UsbDevice device = (UsbDevice) object;
                UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
                if (desc.idVendor() == 0x04d8 && desc.idProduct() == 0x033f) {
                    devices.add(device);
                }
            }
        } catch (UsbException e) {
            throw new RuntimeException(e);
        }
        return devices;
    }
}
