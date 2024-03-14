module com.example.imagegalleryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.imagegalleryapp to javafx.fxml;
    exports com.example.imagegalleryapp;
}