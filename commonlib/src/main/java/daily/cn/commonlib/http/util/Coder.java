package daily.cn.commonlib.http.util;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */

public class Coder {
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final String[] HEX_DIGITS_UPPERCASE = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public Coder() {
    }

    public static final byte[] encodeMD5Bytes(String string) {
        if(TextUtils.isEmpty(string)) {
            return null;
        } else {
            MessageDigest digester = null;

            try {
                digester = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
                return null;
            }

            digester.update(string.getBytes());
            return digester.digest();
        }
    }

    public static final String encodeMD5(String string) {
        byte[] digest = encodeMD5Bytes(string);
        return digest != null?byteArrayToString(digest):null;
    }

    public static final String encodeMD5(File file) {
        byte[] buffer = new byte[1024];
        boolean var3 = false;

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException var19) {
            var19.printStackTrace();
            return null;
        }

        Object var6;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            int numRead;
            while((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }

            return byteArrayToString(md5.digest());
        } catch (NoSuchAlgorithmException var20) {
            var20.printStackTrace();
            var6 = null;
        } catch (IOException var21) {
            var21.printStackTrace();
            var6 = null;
            return (String)var6;
        } finally {
            try {
                fis.close();
            } catch (IOException var18) {
                var18.printStackTrace();
            }

        }

        return (String)var6;
    }

    public static String byteArrayToString(byte[] b) {
        return byteArrayToString(b, false);
    }

    public static String byteArrayToString(byte[] b, boolean upperCase) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i], upperCase));
        }

        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        return byteToHexString(b, false);
    }

    public static String byteToHexString(byte b, boolean upperCase) {
        int n = b;
        if(b < 0) {
            n = 256 + b;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return upperCase?HEX_DIGITS_UPPERCASE[d1] + HEX_DIGITS_UPPERCASE[d2]:HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static final String encodeSHA(String string) {
        if(TextUtils.isEmpty(string)) {
            return null;
        } else {
            MessageDigest digester = null;

            try {
                digester = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
                return null;
            }

            digester.update(string.getBytes());
            byte[] digest = digester.digest();
            return byteArrayToString(digest);
        }
    }

    public static final byte[] encodeSHABytes(String string) {
        if(TextUtils.isEmpty(string)) {
            return null;
        } else {
            MessageDigest digester = null;

            try {
                digester = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
                return null;
            }

            digester.update(string.getBytes());
            return digester.digest();
        }
    }

    public static final String encodeBase64(String string) {
        return Base64.encodeToString(string.getBytes(), 2);
    }

    public static final String encodeBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, 2);
    }

    public static final byte[] encodeBase64Bytes(String string) {
        return Base64.encode(string.getBytes(), 2);
    }

    public static final String decodeBase64(String string) {
        return new String(Base64.decode(string, 0));
    }

    public static final byte[] decodeBase64Bytes(String string) {
        return Base64.decode(string, 0);
    }

    public static final OutputStream encodeBase64Stream(OutputStream outputStream) {
        return new Base64OutputStream(outputStream, 2);
    }

    public static final InputStream decodeBase64Stream(InputStream inputStream) {
        return new Base64InputStream(inputStream, 0);
    }

    public static final String encodeAES(String data, String key) {
        if(!TextUtils.isEmpty(data) && !TextUtils.isEmpty(key)) {
            byte[] raw = decodeBase64Bytes(key);
            if(raw != null && raw.length == 16) {
                SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
                    cipher.init(1, keySpec, iv);
                    return encodeBase64(cipher.doFinal(data.getBytes()));
                } catch (NoSuchAlgorithmException var6) {
                    return null;
                } catch (NoSuchPaddingException var7) {
                    return null;
                } catch (InvalidKeyException var8) {
                    return null;
                } catch (InvalidAlgorithmParameterException var9) {
                    return null;
                } catch (IllegalBlockSizeException var10) {
                    return null;
                } catch (BadPaddingException var11) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static final String decodeAES(String data, String key) {
        if(!TextUtils.isEmpty(data) && !TextUtils.isEmpty(key)) {
            byte[] raw = decodeBase64Bytes(key);
            if(raw != null && raw.length == 16) {
                SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
                    cipher.init(2, keySpec, iv);
                    byte[] encryptedByte = decodeBase64Bytes(data);
                    if(null == encryptedByte) {
                        return null;
                    } else {
                        byte[] decryptedByte = cipher.doFinal(encryptedByte);
                        return new String(decryptedByte);
                    }
                } catch (NoSuchAlgorithmException var8) {
                    return null;
                } catch (NoSuchPaddingException var9) {
                    return null;
                } catch (InvalidKeyException var10) {
                    return null;
                } catch (InvalidAlgorithmParameterException var11) {
                    return null;
                } catch (IllegalBlockSizeException var12) {
                    return null;
                } catch (BadPaddingException var13) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean encodeFileAES(String srcFile, String dstFile, String key) {
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(srcFile) && !TextUtils.isEmpty(dstFile)) {
            byte[] raw = decodeBase64Bytes(key);
            if(raw != null && raw.length == 16) {
                FileInputStream in = null;
                OutputStream out = null;
                CipherInputStream cin = null;

                boolean var8;
                try {
                    SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
                    cipher.init(1, keySpec, iv);
                    byte[] buffer = new byte[1024];
                    in = new FileInputStream(srcFile);
                    out = new FileOutputStream(dstFile);
                    cin = new CipherInputStream(in, cipher);
                    boolean var11 = false;

                    int count;
                    while((count = cin.read(buffer)) != -1) {
                        out.write(buffer, 0, count);
                        out.flush();
                    }

                    out.flush();
                    return true;
                } catch (InvalidKeyException var30) {
                    var30.printStackTrace();
                    var8 = false;
                    return var8;
                } catch (InvalidAlgorithmParameterException var31) {
                    var31.printStackTrace();
                    var8 = false;
                    return var8;
                } catch (NoSuchAlgorithmException var32) {
                    var32.printStackTrace();
                    var8 = false;
                    return var8;
                } catch (NoSuchPaddingException var33) {
                    var33.printStackTrace();
                    var8 = false;
                } catch (FileNotFoundException var34) {
                    var34.printStackTrace();
                    var8 = false;
                    return var8;
                } catch (IOException var35) {
                    var35.printStackTrace();
                    var8 = false;
                    return var8;
                } finally {
                    try {
                        if(in != null) {
                            in.close();
                        }

                        if(out != null) {
                            out.close();
                        }

                        if(cin != null) {
                            cin.close();
                        }
                    } catch (Exception var29) {
                        return false;
                    }

                }

                return var8;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean decodeFileAES(String srcFile, String dstFile, String key) {
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(srcFile) && !TextUtils.isEmpty(dstFile)) {
            byte[] raw = decodeBase64Bytes(key);
            if(raw != null && raw.length == 16) {
                FileInputStream in = null;
                FileOutputStream out = null;
                CipherOutputStream cout = null;

                boolean var8;
                try {
                    File sourceFile = new File(srcFile);
                    File destFile = new File(dstFile);
                    if(sourceFile.exists() && sourceFile.isFile()) {
                        if(!destFile.getParentFile().exists()) {
                            destFile.getParentFile().mkdirs();
                        }

                        destFile.createNewFile();
                        in = new FileInputStream(sourceFile);
                        out = new FileOutputStream(destFile);
                        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
                        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(2, secretKeySpec, iv);
                        cout = new CipherOutputStream(out, cipher);
                        byte[] cache = new byte[1024];
                        boolean var13 = false;

                        int nRead;
                        while((nRead = in.read(cache)) != -1) {
                            cout.write(cache, 0, nRead);
                            cout.flush();
                        }
                    }

                    return true;
                } catch (Exception var22) {
                    var22.printStackTrace();
                    var8 = false;
                } finally {
                    try {
                        if(cout != null) {
                            cout.close();
                        }

                        if(out != null) {
                            out.close();
                        }

                        if(in != null) {
                            in.close();
                        }
                    } catch (Exception var21) {
                        var21.printStackTrace();
                        return false;
                    }

                }

                return var8;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
