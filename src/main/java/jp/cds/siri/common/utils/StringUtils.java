package jp.cds.siri.common.utils;

import java.security.SecureRandom;

import org.apache.commons.lang.RandomStringUtils;

public class StringUtils {
    public static final String NG_WORD = "��";

    private static String USE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * �J�^�J�i�𕽉����ɕϊ�
     *
     * @param s
     * @return
     */
    public static String toHiragana(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (0x30A1 <= c && c <= 0x30F3) {
                c -= 0x0060;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * �Ђ炪�ȈȊO���܂܂��ꍇ�͖�������B�Ō�̕������u��v�̏ꍇ����������
     *
     * @return
     */
    public static boolean checkHiragana(String s) {
        if (s.matches(".*[^\\u3040-\\u309F\\u30A0-\\u30FF].*")) {
            return false;
        }
        if (s.substring(s.length() - 1).equals(NG_WORD)) {
            return false;
        }
        return true;
    }

    public static String createRandomString(Integer length) {
        return RandomStringUtils.random(length, 0, USE_CHARS.length(), false, false, USE_CHARS.toCharArray(), new SecureRandom());

    }
}
