
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class C2Client{

    public SecretKey secretkey;
    public Cipher cipher;

     public String decrypt(String encryptedtext, SecretKey secretkey)throws Exception{
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedtext);
        cipher.init(Cipher.DECRYPT_MODE,secretkey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedtext = new String(decryptedByte);
        return decryptedtext;
    }

    String msg;
    String msgs;
    Socket socket;
    BufferedReader br;
    PrintWriter wr;
    private boolean running = true;
    public C2Client(){
        try{
            System.out.println("Sending Connection...");
            socket = new Socket("192.168.131.1",8887);
            System.out.println("Connection Done...");
            cipher = Cipher.getInstance("AES");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr = new PrintWriter(socket.getOutputStream());
            read();
            write();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void show_data() {
        try {
            System.out.println("Open");
            Scanner obj = new Scanner(System.in);
            String encryptedText = obj.nextLine(); 
            String decryptedText = decrypt(encryptedText, secretkey); 
            System.out.println("Decrypted Text: " + decryptedText);
            obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void read(){
        Runnable r1=()->{
            while(running){
                try{
                    msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server Terminated...");
                        running = false;
                        break;
                    }
                    else if (msg.equals("pass")) { 
                        String encodedKey = msg;
                        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                        secretkey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                        System.out.println("Pass equals"+secretkey);
                     }
                    else{
                        System.out.println("Server : "+msg);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }
    public void write(){
        
        Runnable r2=()->{
            while(running){
                try{
                    Scanner sc = new Scanner(System.in);
                    msgs = sc.nextLine();
                    if(msgs.equals("exit")){
                        System.out.println("Self Connection terminated..");
                        running = false;
                        break;
                    }
                    else if(msgs.equals("Check")){
                        show_data();
                    }
                    else{
                        wr.println(msgs);
                        wr.flush();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String args[]){
        System.out.println("Client...");
        new C2Client();
    }
}
