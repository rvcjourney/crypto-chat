import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SServer extends Thread {
    String msg,msgs;
    BufferedReader br;
    PrintWriter wr;
    ServerSocket server;
    Socket socket;
    String plaintext;
    String encryptedtext;
    static Cipher cipher;
    SecretKey secretkey;
    KeyGenerator keygenerator;
    // String [] data;
    String data;
    String decryptedtext;

    
    public String encrypt(String plaintext,SecretKey secretkey) throws Exception{

        byte [] plainTextByte = plaintext.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE,secretkey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);;

        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public String decrypt(String encryptedtext, SecretKey secretkey)throws Exception{
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedtext);
        cipher.init(Cipher.DECRYPT_MODE,secretkey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedtext = new String(decryptedByte);
        return decryptedtext;
    }

    public SServer(){
        try{

            keygenerator = KeyGenerator.getInstance("AES");
            keygenerator.init(128);
            secretkey = keygenerator.generateKey();
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            System.out.println("Waiting for Connection..");
            server = new ServerSocket(9999);
            socket = server.accept();
            System.out.println("Connection Done..");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr =new PrintWriter(socket.getOutputStream(),true);
            read();
            write();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void decryption() throws Exception{
        try {
            decryptedtext = decrypt(data, secretkey);
            System.out.println("Decrypted Text: " + decryptedtext);
        } catch (Exception e) {
            System.out.println("Decryption failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void read(){
        Runnable r1=()->{
            while(true){
                try{
                    msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client : Connection Terminated by Client");
                        break;
                    }
                    else{
                        System.out.println("Client : "+msg);
                        // data = new String [] {msg};
                        data = msg;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }
    
    public void write(){
        Runnable w1=()->{
            while(true){
                try{
                    Scanner sc = new Scanner(System.in);
                    msgs = sc.nextLine();
                    if(msgs.equals("exit")){
                        wr.println(msgs);
                        wr.flush();
                    }
                    else if(msgs.equals("decrypt")){
                        decryption();
                    }
                    else{
                        plaintext = msgs;
                        encryptedtext = encrypt(plaintext,secretkey);
                        wr.println(encryptedtext);
                        wr.flush();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(w1).start();
    }
    public static void main(String args[]) throws Exception{
        System.out.println("Server Section....");
        new SServer();
    }
}
