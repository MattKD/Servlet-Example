package kame;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
  private static java.util.Random random = new java.util.Random();

  // Hash input using sha-256 into a 64 lengh hex string
  public static String hash(String input) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      byte[] hashedBytes = sha.digest(input.getBytes());
      return bytesToHexString(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("kame.Util.hash: SHA-256 unsupported");
    }
  }

  // Create random 16 length string
  public static String genSalt() {
    // random is thread safe
    int n = random.nextInt(); // 0 to 2^32-1
    String s = Integer.toHexString(n);
    s = hash(s); // 64 length hex string
    return s.substring(0, 16);
  }

  // Convert byte array to a string of hex chars (0 to F)
  public static String bytesToHexString(byte[] bytes) {
    // fast lookup of hex chars
    char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                    'A', 'B','C','D','E','F' };
    char[] cstr = new char[bytes.length * 2]; // hex is 2 chars per byte
    for (int i = 0; i < bytes.length; i++) {
      int val = bytes[i] & 0xFF;
      cstr[i * 2] = chars[val >>> 4]; // convert higher 4 bits to char
      cstr[i * 2 + 1] = chars[val & 0x0F]; // convert lower 4 bits
    }
    return new String(cstr);
  }
}
