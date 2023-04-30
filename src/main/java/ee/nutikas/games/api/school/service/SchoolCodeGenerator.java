package ee.nutikas.games.api.school.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class SchoolCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Set<String> usedCodes = new HashSet<>();

    private SchoolCodeGenerator() {}

    public static synchronized String generateCode() {
        String code = generateRandomCode();
        while (usedCodes.contains(code)) {
            code = generateRandomCode();
        }
        usedCodes.add(code);
        return code;
    }

    private static String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static synchronized void clearUsedCodes() {
        usedCodes.clear();
    }

    public static synchronized int getNumUsedCodes() {
        return usedCodes.size();
    }

}
