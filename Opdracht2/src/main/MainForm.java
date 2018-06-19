package main;

import filemanaging.StreamReaderWriter;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class MainForm
{
    private JPanel mainPanel;
    private JTextArea textMessageInput;
    private JButton encryptButton;
    private JButton decryptButton;
    private final JFileChooser fc = new JFileChooser();
    private final StreamReaderWriter srw = new StreamReaderWriter();

    private static final String KEY_DERIVATION_FUNCTION = "PBKDF2WithHmacSHA256";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String GENERATOR_ALGORITHM = "SHA1PRNG";

    private static final int KEY_SIZE_IN_BITS = 128;
    private static final int SALT_SIZE_IN_BITS = 128;
    private static final int TAG_SIZE_IN_BITS = 128;
    private static final int ITERATION_COUNT = 200000;

    public JPanel getMainPanel()
    {
        return mainPanel;
    }


    /**
     * we were constantly getting different types of errors,
     * after searching online for a bit i found this implementation on the following url
     * https://coderanch.com/t/663164/engineering/AES-Encryption-Decryption-IV
     * this has been used as a primary source for information and implementation methods.
     */

    public MainForm()
    {
        setupButtons();
    }

    private void setupButtons()
    {
        encryptButton.setActionCommand("Encrypt");
        decryptButton.setActionCommand("Decrypt");
        encryptButton.addActionListener(this::actionPerformed);
        decryptButton.addActionListener(this::actionPerformed);
    }

    private void actionPerformed(ActionEvent e)
    {
        if ("Encrypt".equals(e.getActionCommand()))
        {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this.mainPanel) == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                // popup box for password
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter a password:");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(mainPanel, panel, "The title",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if (option == 0) // pressing OK button
                {
                    char[] password = pass.getPassword();
                    // call decrypt
                    if (password.length > 0) encrypt(file, password);
                    // clear array
                    password = new char[0];
                }
            }
        }

        if ("Decrypt".equals(e.getActionCommand()))
        {
            if (fc.showOpenDialog(this.mainPanel) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();

                // popup box for password
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter a password:");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(mainPanel, panel, "The title",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if (option == 0) // pressing OK button
                {
                    char[] password = pass.getPassword();
                    // call decrypt
                    if (password.length > 0) decrypt(file, password);
                    // clear array
                    password = new char[0];
                }
            }
        }
    }

    /**
     * Derives a secret key from a password.
     * This operation needs to take long enough to avoid brute forcing by an attacker.
     * Salts are used to limit the use of rainbow tables.
     * If you need to store multiple passwords in a database, each should be derived using a different salt.
     * Salts are not secret data, and can be stored plainly.
     */
    private SecretKey generateKey(char[] password, byte[] salt)
    {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_SIZE_IN_BITS);
        SecretKey pbeKey;
        byte[] keyBytes = null;

        try
        {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_DERIVATION_FUNCTION);
            pbeKey = factory.generateSecret(pbeKeySpec);

            keyBytes = pbeKey.getEncoded();
            return new SecretKeySpec(keyBytes, ENCRYPTION_ALGORITHM);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        finally // clean up
        {
            pbeKeySpec.clearPassword();
            if (keyBytes != null) Arrays.fill(keyBytes, (byte) 0);
        }
        return null;
    }

    /**
     * Generates an Initialization Vector.
     * Salts need to be cryptographically secure and completely random.
     * You must always treat Salts as secret until you've used them in your encryption step.
     * After the encryption step, you can store them plainly.
     * Salts must be used only once per encryption step. After that, they must only be used to decrypt.
     */
    private byte[] generateSalt()
    {
        try
        {
            SecureRandom random = SecureRandom.getInstance(GENERATOR_ALGORITHM);
            byte[] salt = new byte[SALT_SIZE_IN_BITS / 8];
            random.nextBytes(salt);
            return salt;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates and initializes a new cipher.
     * A key is generated from the password, using a salt.
     * The Salt is also used for encryption.
     */
    private Cipher initCipher(int mode, char[] password, byte[] salt)
    {
        // We're using AES in Galois Counter Mode, here we're preparing the spec.
        // The tag size is the size of the tag that AES-GCM uses to sign and authenticate the encrypted data with.
        // Authentication is important, because it helps us detect that a message wasn't forged by an attacker.
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_SIZE_IN_BITS, salt);
        SecretKey key = generateKey(password, salt);

        try
        {
            // We're using AES in Galois Counter Mode.
            // This transformation is a form of AEAD and performs authentication as well as encryption.
            // Data should never be encrypted without being authenticated.
            // Since GCM is a stream cipher mode and not a block cipher mode, it doesn't require padding.
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, key, gcmSpec);
            return cipher;
        }

        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert key != null;
                key.destroy();
            }
            catch (DestroyFailedException e) {}
        }
        return null;
    }

    /**
     * Encrypts a message with a password.
     * The result is a concatenation of the Salt and the ciphertext.
     * The message and the password should be treated as secret, obviously.
     * The result can be stored plainly.
     * A different Salt is used each time, so encrypting the same message twice will (and SHOULD) have completely different results.
     */
    private void encrypt(File file, char[] password)
    {
        try
        {
            byte[] salt = generateSalt();

            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, password, salt);
            byte[] ciphertext = cipher.doFinal(textMessageInput.getText().getBytes());

            // The Salt needs to be stored with the ciphertext, otherwise we can't decrypt the message later.
            byte[] result = new byte[salt.length + ciphertext.length];
            System.arraycopy(salt, 0, result, 0, salt.length);
            System.arraycopy(ciphertext, 0, result, salt.length, ciphertext.length);

            textMessageInput.setText("");

            srw.WriteToFile(file, result);
        }
        catch (IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Decrypts a message that was encrypted with the given password.
     * The encrypted message must be a concatenation of Salt and ciphertext.
     *
     * @throws BadPaddingException If the password and encrypted message don't match.
     */
    private void decrypt(File file, char[] password)
    {
        try
        {

            byte[] encrypted = srw.ReadFromFile(file);

            byte[] salt = Arrays.copyOfRange(encrypted, 0, SALT_SIZE_IN_BITS / 8);
            byte[] ciphertext = Arrays.copyOfRange(encrypted, salt.length, encrypted.length);

            Cipher cipher = initCipher(Cipher.DECRYPT_MODE, password, salt);
            byte[] message = cipher.doFinal(ciphertext);

            textMessageInput.setText(new String(message));
        }
        catch (IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
        }
    }
}
