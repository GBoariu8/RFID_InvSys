package com.rfid_invsys.rfid_invsys.rfid;

import com.ad.rcp.OnProtocolListener;
import com.ad.rcp.ProtocolPacket;
import com.ad.rcp.RcpBase;
import com.ad.rcp.RcpMM;
import com.rfid_invsys.rfid_invsys.Controllers.MainController;
import com.rfid_invsys.rfid_invsys.SignalGateway.MACROS;

public class ADRcp {
    private static final ADRcp INSTANCE = new ADRcp();
    private static final RcpBase rcp = new RcpBase();
    private MainController mainController;
    private ADRcp() {}
    public static ADRcp getInstance(){
        return INSTANCE;
    }
    public RcpBase getRcp(){
        return rcp;
    }
    public void setListener(){
        rcp.setOnProtocolListener(this.OnProtocolListener());
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    private OnProtocolListener OnProtocolListener() {
        return (object, protocolEventArg) -> {
            String response;
            ProtocolPacket protocolPacket = protocolEventArg.getProtocolPacket();
            switch (protocolPacket.Code) {
                case RcpMM.RCP_MM_READ_C_DT:
                case RcpMM.RCP_MM_READ_C_UII:
                case RcpMM.RCP_MM_SET_ACCESS_EPC_MATCH:
                case RcpMM.RCP_MM_WRITE_C_DT:
                    break;
                case RcpMM.RCP_MM_GET_TX_PWR:{
                    response = "The current TX Power Level is: " + protocolPacket.Payload[0] + "dBm";
                    mainController.updateTextArea(response);
                    break;
                }
                case RcpMM.RCP_MM_SET_TX_PWR:{
                    response = "The TX power level has been updated.";
                    mainController.updateTextArea(response);
                    break;
                }
                case RcpMM.RCP_MM_GET_REGION:{
                    String region = "";
                    switch (protocolPacket.Payload[0]) {
                        case MACROS.REGION_CHINA_900M -> region = " CHINA - 900M";
                        case MACROS.REGION_CHINA_800M -> region = " CHINA - 800M";
                        case MACROS.REGION_US -> region = " USA";
                        case MACROS.REGION_EUROPE -> region = " EUROPE";
                        case MACROS.REGION_KOREA -> region = " KOREA";
                        default -> {}
                    }
                    response = "The current region is:" + region;
                    mainController.updateTextArea(response);
                    break;
                }
                case RcpMM.RCP_MM_SET_REGION:{
                    response = "The region has been updated";
                    mainController.updateTextArea(response);
                    break;
                }
                default:
                    break;
            }
        };
    }
}
