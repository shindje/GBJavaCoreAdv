package hw6;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class WriteThread extends Thread{
    DataOutputStream out;
    Scanner sc = new Scanner(System.in);
    boolean stopable;

    WriteThread (DataOutputStream out) {
        super();
        this.out = out;
        stopable = true;
    }

    WriteThread () {
        super();
        stopable = false;
    }

    void setOutputSream(DataOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String str = sc.nextLine();
                if (out != null) {
                    out.writeUTF(str);
                    if (str.equals("/end") && stopable) {
                        break;
                    }
                } else {
                    System.out.println("Нет подключения");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
