package id.base.app.encryptor;

public class XOREncrypter {

    byte[] secret = "Lid/co/base/app/aes/encryptor/XOREncrypter".getBytes();

    public String encrypt(String password) {

        byte[] encrypted = xor(password.getBytes(), secret);

        return Base64Converter.encode(encrypted);
    }

    public String decrypt(String encPassword) {
        return new String(xor(Base64Converter.decode(encPassword), secret));
    }

    public static byte[] xor(byte[] password, byte[] secret) {
        byte[] result = new byte[password.length];
        for (int i=0; i<password.length; i++) {
            result[i] = (byte)(password[i] ^ secret[i]);
        }
        return result;
    }

}
