module Lang {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

     opens Lang.View to javafx.fxml;
     exports Lang.View;
     exports Lang.Repo;
     exports Lang.Controller;
     exports Lang.Exceptions;
     exports Lang.Model.Expressions;
     exports Lang.Model.Types;
     exports Lang.Model.Structures;
     exports Lang.Model.Values;
     exports Lang.Model.Statements;
}