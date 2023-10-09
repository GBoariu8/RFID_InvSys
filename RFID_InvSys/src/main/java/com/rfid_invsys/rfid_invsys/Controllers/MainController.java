package com.rfid_invsys.rfid_invsys.Controllers;
import com.rfid_invsys.rfid_invsys.SignalGateway.SignalGateway;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    SignalGateway sg = SignalGateway.getInstance();
    @FXML TextArea FXML_TextArea;

    public MainController()
    {
        sg.adSio.setListener();
        sg.adRcp.setListener();
        sg.adRcp.setMainController(this);
    }

    /**
     * Logs messages with timestamp.
     *
     * @param message The message to log.
     */
    public void updateTextArea(String message) {
        Platform.runLater(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            sg.resmsg = formattedDateTime + " " + message + "\n" + sg.resmsg;
            FXML_TextArea.setText(sg.resmsg + "\n");
        });
    }

    /**
     * Gets the code length from a byte data.
     *
     * @param iData The input byte data.
     * @return The code length.
     */
    private static int GetCodelen(byte iData) {
        return (((iData >> 3) + 1) * 2);
    }
}
