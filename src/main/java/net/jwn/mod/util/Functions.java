package net.jwn.mod.util;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class Functions {
    public static int[] resize(int[] arr, int size) {
        int[] newArray = new int[size];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, size));
        return newArray;
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

    public static List<String> splitText(Font font, String text, float maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (font.width(currentLine + word) > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word + " ");
            } else {
                currentLine.append(word).append(" ");
            }
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
