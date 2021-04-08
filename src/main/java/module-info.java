module group1.cs180_proj {
    requires javafx.controls;
    requires javafx.fxml;

    opens group1.cs180_proj to javafx.fxml;
    exports group1.cs180_proj;
}
