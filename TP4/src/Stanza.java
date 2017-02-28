import java.io.Serializable;

/**
 * Created by saradion on 10/10/16.
 */
public class Stanza implements Serializable {
    private String[] elements;

    Stanza(String header, String destination, String sender, String body) {
        elements = new String[5];
        elements[0] = header;
        elements[1] = String.valueOf(System.nanoTime());
        elements[2] = destination;
        elements[3] = sender;
        elements[4] = body;
    }

    public String header() {
        return elements[0];
    }

    public String timestamp() {
        return elements[1];
    }

    public String destination() {
        return elements[2];
    }

    public String sender() {
        return elements[3];
    }

    public String body() {
        return elements[4];
    }
}
