package net.jwn.mod.util;

public class Functions {
    public static int[] resize(int[] arr, int size) {
        int[] newArray = new int[size];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, size));
        return newArray;
    }
    public static void push(int[] arr) {
        int firstZero = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                firstZero = i;
                break;
            }
        }
        arr[firstZero] = arr[0];
        remove(arr, 0);
    }
    public static void remove(int[] arr, int index) {
        arr[index] = 0;
        for (int i = index + 1; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[i - 1] = arr[i];
                arr[i] = 0;
            }
        }
    }
}
