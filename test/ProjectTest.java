import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ProjectTest {
    @Test
    public void testDecypher() {
        byte[] message = {0,0,1,0};
        byte[] actual = Main.decypher(message);
        byte[] expected = {0,0,0,1};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void dechiffrageTest() {
        byte[] messageCrypted = {0, 0 , 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1};
        int t = 4;
        byte[] iv = {1,0,1,0};

        byte[][] blocks = new byte[messageCrypted.length / t][t];
        for (int i = 0; i < messageCrypted.length / t; i++) {
            for (int j = 0; j < t; j++) {
                blocks[i][j] = messageCrypted[i * t + j];
            }
        }

        byte[][] newBlock = new byte[messageCrypted.length / t][t];

        for (int i = 0; i < blocks.length; i++) {
            if (i == 0) {
                byte[] cypher = Main.decypher(blocks[i]);
                byte[] bXorV = Main.xor(cypher, iv);
                newBlock[i] = bXorV;
            } else {
                byte[] cypher = Main.decypher(blocks[i]);
                byte[] bXorV = Main.xor(cypher, blocks[i-1]);
                newBlock[i] = bXorV;
            }
        }

        byte[] result = new byte[newBlock.length * newBlock[0].length];
        for (int i = 0; i < newBlock.length; i++) {
            for (int j = 0; j < newBlock[0].length; j++) {
                result[i * newBlock[0].length + j] = newBlock[i][j];
            }
        }
        byte[] excepted = {1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0};
        assertArrayEquals(excepted, result);
    }

    @Test
    public void testBinaryToString() {
        byte[] binary = {0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0 };
        String transform = Main.binaryToString(binary);
        Assert.assertEquals("test", transform);
    }
}
