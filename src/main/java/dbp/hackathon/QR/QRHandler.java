package dbp.hackathon.QR;

import java.util.Random;

public class QRHandler {
    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        salt.append("https://api.qrserver.com/v1/create-qr-code/?data=");
        salt.append("HelloWorld!");
        salt.append("&size=100x100");
        return salt.toString();
    }
}
