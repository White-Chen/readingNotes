package net.mindview.test.chapter13;

import net.mindview.util.BinaryFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by ChenZhePC on 2017/1/23.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */
public class Hex {
    public static String format(byte[] data){
        StringBuilder result =
                new StringBuilder();
        int n = 0;
        for (byte b : data) {
            if (n % 16 == 0)
                result.append(String.format("%05X: ", n));
            result.append(String.format("%02X ", b));
            n ++;
            if (n % 16 ==0)
                result.append("\n");
        }
        result.append("\n");
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println(
                    format(BinaryFile.read("Thinking-In-Java\\mindview\\target\\classes\\net\\mindview\\test\\chapter13\\Hex.class")));
        }
        else {
            System.out.println(
                    format(BinaryFile.read(new File(args[0]))));
        }
    }
}

/*
* 00000: CA FE BA BE 00 00 00 34 00 62 0A 00 05 00 32 07
00010: 00 33 0A 00 02 00 32 08 00 34 07 00 35 0A 00 36
00020: 00 37 0A 00 38 00 39 0A 00 02 00 3A 08 00 3B 0A
00030: 00 3C 00 3D 08 00 3E 0A 00 02 00 3F 09 00 40 00
00040: 41 08 00 42 0A 00 43 00 44 0A 00 15 00 45 0A 00
00050: 46 00 47 07 00 48 0A 00 12 00 49 0A 00 43 00 4A
00060: 07 00 4B 01 00 06 3C 69 6E 69 74 3E 01 00 03 28
00070: 29 56 01 00 04 43 6F 64 65 01 00 0F 4C 69 6E 65
00080: 4E 75 6D 62 65 72 54 61 62 6C 65 01 00 12 4C 6F
00090: 63 61 6C 56 61 72 69 61 62 6C 65 54 61 62 6C 65
000A0: 01 00 04 74 68 69 73 01 00 21 4C 6E 65 74 2F 6D
000B0: 69 6E 64 76 69 65 77 2F 74 65 73 74 2F 63 68 61
000C0: 70 74 65 72 31 33 2F 48 65 78 3B 01 00 06 66 6F
000D0: 72 6D 61 74 01 00 16 28 5B 42 29 4C 6A 61 76 61
000E0: 2F 6C 61 6E 67 2F 53 74 72 69 6E 67 3B 01 00 01
000F0: 62 01 00 01 42 01 00 04 64 61 74 61 01 00 02 5B
00100: 42 01 00 06 72 65 73 75 6C 74 01 00 19 4C 6A 61
.....
00680: 00 04 00 02 16 15 00 2E 00 00 00 04 00 01 00 2F
00690: 00 01 00 30 00 00 00 02 00 31
*/