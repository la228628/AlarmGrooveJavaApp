module alarmgroove.alarmgrooveapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires com.fazecast.jSerialComm;
    requires javafx.web;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;

    opens alarmgroove.alarmgrooveapp.View to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports alarmgroove.alarmgrooveapp.View;

    opens alarmgroove.alarmgrooveapp.Controllers to javafx.graphics, javafx.fxml, javafx.controls, javafx.base;
    exports alarmgroove.alarmgrooveapp.Controllers;

    opens alarmgroove.alarmgrooveapp.Models to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports alarmgroove.alarmgrooveapp.Models;





}