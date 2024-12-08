package controllers.Document;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class QRCodeWindowController {

    @FXML
    private ImageView qrCodeImageView;

    public void setQRCodeImage(WritableImage qrCodeImage) {
        qrCodeImageView.setImage(qrCodeImage);
    }
}
