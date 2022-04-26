module com.example.projetlego {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens fr.antoromeochrist.projetlego to javafx.fxml;
    exports fr.antoromeochrist.projetlego;
    exports fr.antoromeochrist.projetlego.utils;
    opens fr.antoromeochrist.projetlego.utils to javafx.fxml;
    exports fr.antoromeochrist.projetlego.utils.images;
    opens fr.antoromeochrist.projetlego.utils.images to javafx.fxml;
    exports fr.antoromeochrist.projetlego.utils.print;
    opens fr.antoromeochrist.projetlego.utils.print to javafx.fxml;
}