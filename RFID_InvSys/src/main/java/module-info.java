module com.rfid_invsys.rfid_invsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires adrcplib;
    requires adsiolib;
    requires usb.api;


    opens com.rfid_invsys.rfid_invsys to javafx.fxml;
    opens com.rfid_invsys.rfid_invsys.Controllers to javafx.fxml;
    exports com.rfid_invsys.rfid_invsys;
    exports com.rfid_invsys.rfid_invsys.Controllers;
//    exports com.rfid_invsys.rfid_invsys.Views;
//    exports com.rfid_invsys.rfid_invsys.Models;

}