module com.bishal.invoicegenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires kernel;
    requires layout;
    requires itextpdf;

    opens com.bishal.invoicegenerator.Controller to javafx.fxml;
    exports com.bishal.invoicegenerator.Controller;
    exports com.bishal.invoicegenerator;
    opens com.bishal.invoicegenerator to javafx.fxml;
}