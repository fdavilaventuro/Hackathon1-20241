package dbp.hackathon.QR;

import java.util.Random;

public class QRHandler {
    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        salt.append("https://api.qrserver.com/v1/create-qr-code/?data=");
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        salt.append("&amp;size=100x100");
        return salt.toString();
    }
}
