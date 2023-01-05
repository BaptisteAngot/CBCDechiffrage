import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar CBCProject.jar 4 1010 test.txt");
            return;
        }

        int key = Integer.parseInt(args[0]);

        byte[] encryptedFile = readFileToByteArray(args[2]);
        String decrypted = decrypt(key, convertIvToBinary(args[1]), encryptedFile);

        String fileNameDecrypted = args[2] + ".decrypted";
        saveFile(fileNameDecrypted, decrypted.getBytes());
    }

    private static byte[] readFileToByteArray(String fileNameEncrypted) {
        byte[] bytes = null;
        try (FileInputStream fis = new FileInputStream(fileNameEncrypted)) {
            bytes = new byte[fis.available()];
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static void saveFile(String fileNameEncrypted, byte[] result) {
        try {
            FileOutputStream fos = new FileOutputStream(fileNameEncrypted);
            fos.write(result);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] convertIvToBinary(String iv) {
        byte[] bytes = new byte[iv.length()];
        for (int i = 0; i < iv.length(); i++) {
            bytes[i] = (byte) (iv.charAt(i) - '0');
        }
        return bytes;
    }

    public static String decrypt(int key, byte[] iv, byte[] ciphertext) {
        // Divide the ciphertext into blocks
        byte[][] blocks = new byte[ciphertext.length / key][key];
        for (int i = 0; i < ciphertext.length / key; i++) {
            for (int j = 0; j < key; j++) {
                blocks[i][j] = ciphertext[i * key + j];
            }
        }

        for (int i = blocks.length - 1; i >= 0; i--) {
            byte[] decryptedBlock = decypher(blocks[i]);
            if (i == 0) {
                blocks[i] = xor(decryptedBlock, iv);
            } else {
                blocks[i] = xor(decryptedBlock, blocks[i - 1]);
            }
        }

        byte[] result = new byte[blocks.length * blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                result[i * blocks[0].length + j] = blocks[i][j];
            }
        }

        return binaryToString(result).trim();
    }

    static String binaryToString(byte[] bytes) {
        byte[] b = new byte[bytes.length / 8];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < 8; j++) {
                b[i] = (byte) ((bytes[i * 8 + j] << (7 - j)) | b[i]);
            }
        }
        return new String(b);
    }

    public static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static byte[] decypher(byte[] message) {
        if (message.length != 4) {
            throw new IllegalArgumentException("Le tableau a doit avoir une longueur de 4");
        }
        byte[] result = new byte[4];
        result[0] = message[3];
        result[1] = message[0];
        result[2] = message[1];
        result[3] = message[2];

        return result;
    }

}