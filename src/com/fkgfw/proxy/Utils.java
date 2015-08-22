package com.fkgfw.proxy;

import java.io.UnsupportedEncodingException;


public class Utils {


    public static String getIpAddrFromByteArray(byte[] b, int offset) {
        StringBuilder addr = new StringBuilder();
        for (int i = offset; i < 4; i++) {
            addr.append((char) b[i]);
            if (i < 3) {
                addr.append('.');
            }
        }
        return addr.toString();
    }


    public static int getPortFromByteArrayAtIPtype(byte[] b, int Offset) {
        int result = 0;

        for (int i = Offset; i < Offset + 2; i++) {
            if (b[i] == (byte) 0) {
                continue;
            }
            result <<= 8;
            result |= 0xff;
            result &= b[i];//byte不能直接加减，可能会碰到首位为1的就被判定为负数了
        }
        return result;
    }

    public static String byteArray2ASCStr(byte[] b, int length) {
        String s = null;
        try {
            s = new String(b, 0, length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String byteArray2DecimalStr(byte[] b) {
        return byteArray2DecimalStr(b, 0, b.length);
    }

    public static String byteArray2DecimalStr(byte[] b, int start, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            result.append((char) (b[i]));
        }
        return result.toString();
    }

    public static String byteArray2HexString(byte[] b, int start, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static String byteArray2HexString(byte[] b, int length) {
        return byteArray2HexString(b, 0, length);
    }

    public static String byteArray2HexString(byte[] b) {
        return byteArray2HexString(b, 0, b.length);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
