package hw6;

import java.io.DataInputStream;
import java.io.IOException;

public class ReadThread extends Thread {
    DataInputStream in;
    String streamName;

    ReadThread(DataInputStream in, String streamName) {
        super();
        this.in = in;
        this.streamName = streamName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                    break;
                } else {
                    System.out.println(streamName + ": " + str);
                }
            }
        } catch (IOException e) {
        }
    }
}
