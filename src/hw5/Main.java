package hw5;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    static void calc(float[] a, int idx) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (float)(a[i] * Math.sin(0.2f + (i + idx) / 5) * Math.cos(0.2f + (i + idx) / 5) * Math.cos(0.4f + (i + idx) / 2));
        }
    }
    
    static void notParallel() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        calc(arr, 0);
        long stop = System.currentTimeMillis();
        System.out.println("not parallel: " + (stop - start) + " millis");
    }

    static void parallel() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> {
            calc(a1, 0);
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            calc(a2, h);
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long stop = System.currentTimeMillis();
        System.out.println("parallel: " + (stop - start) + " millis");
    }

    
    
    public static void main(String[] args) {
        notParallel();
        System.out.println("arr[5000000] = " + arr[5000000]);
        parallel();
        System.out.println("arr[5000000] = " + arr[5000000]);
    }
}
