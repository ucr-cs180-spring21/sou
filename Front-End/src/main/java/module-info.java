module group1.cs180_proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    opens group1.cs180_proj to javafx.fxml;
    exports group1.cs180_proj;
    requires org.apache.commons.lang3;
    requires java.base;
 
}
