package id.base.app.encryptor;

public class Encrypt {
    public static void main(String[] args) {
        XOREncrypter encrypter = new XOREncrypter();
        if (args != null && args.length == 1) {
            System.out.println(encrypter.encrypt(args[0]));
        } else if (args != null && args.length == 2) {
            if ("decrypt".equalsIgnoreCase(args[0])) {
                System.out.println(encrypter.decrypt(args[1]));
            } else {
                wrongAgurment();
            }
        } else {
            wrongAgurment();
        }
    }

    private static void wrongAgurment() {
        System.out.println("Command error.\nUse: java Encrypt <text-to-ecrypt-without-space>");
    }
}
