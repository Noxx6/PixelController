package com.neophob.sematrix.output;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.neophob.sematrix.output.misc.MD5;

/**
 * verify the rotate buffer code
 * @author michu
 *
 */
public class MD5SpeedTest {

    private static final Logger LOG = Logger.getLogger(MD5SpeedTest.class.getName());

    private static final int ROUNDS = 100000;

    /**
     * get md5 checksum of an byte array
     * @param input
     * @return
     */
    private static String getMD5(byte[] input) {
            try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] messageDigest = md.digest(input);
                    BigInteger number = new BigInteger(1, messageDigest);
                    String hashtext = number.toString(16);
                    // Now we need to zero pad it if you actually want the full 32 chars.
                    while (hashtext.length() < 32) {
                            hashtext = "0" + hashtext;
                    }
                    return hashtext;
            }
            catch (Exception e) {
                    LOG.log(Level.WARNING, "Failed to calculate MD5 sum: {0}", e);
                    return "";
            }
    }
    
    @Test
    public void speedTestOld() {
        byte[] b = new byte[192];

        long pre = System.currentTimeMillis();

        for (int i=0; i<ROUNDS; i++) {
            MD5SpeedTest.getMD5(b);   
        }               
        long post = System.currentTimeMillis();

        long time = post-pre;
        float avg = (float)time / (float)ROUNDS;
        LOG.log(Level.INFO,"rainbowduino needed {0}ms, avg: {1}", new Object[] {time, avg});
    }
    
    @Test
    public void speedTestNew() {
        byte[] b = new byte[192];

        long pre = System.currentTimeMillis();

        for (int i=0; i<ROUNDS; i++) {
            MD5.asHex(b);   
        }               
        long post = System.currentTimeMillis();

        long time = post-pre;
        float avg = (float)time / (float)ROUNDS;        
        LOG.log(Level.INFO,"MD5.asHex needed {0}ms, avg: {1}", new Object[] {time, avg});
    }


}
