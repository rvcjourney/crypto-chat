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


public class S2Server{

    public KeyGenerator keygenerator;
    public SecretKey secretkey;
    public Cipher cipher;

    public void Generation()throws Exception{
        keygenerator = KeyGenerator.getInstance("AES");
        keygenerator.init(128);
        secretkey = keygenerator.generateKey();
        cipher = Cipher.getInstance("AES");
    }
    public String Encrypt(String text)throws Exception{
        byte[] textByte = text.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE,secretkey);
        byte[] encryptedByte = cipher.doFinal(textByte);;
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encryptedByte);
    }
    String msg;
    String msgs;
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter wr;
    private boolean running = true;

    public S2Server()throws Exception{
        Generation();
        try{
            System.out.println("Server is waiting for connection...");
            server = new ServerSocket(8887);
            socket = server.accept();
            System.out.println("Connection done..");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr = new PrintWriter(socket.getOutputStream());
            read();
            write();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendkey(){
        System.out.println("SecretKey Sended");
        String encodedKey = Base64.getEncoder().encodeToString(secretkey.getEncoded());
        System.out.println("SecretKey : "+encodedKey);
        wr.println(encodedKey);
        wr.flush();
    }
    public void read(){
        Runnable r1=()->{
            while(running){
                try{
                    msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client Terminated...");
                        running = false;
                        break;
                    }
                    else{
                        System.out.println("Client : "+msg);
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
                    String encrypt = Encrypt(msgs);
                    if(msgs.equals("exit")){
                        System.out.println("Self Connection terminated..");
                        running = false;
                        break;
                    }
                    else if(msgs.equals("pass")){
                        sendkey();
                    }
                    else{
                        wr.println(encrypt);
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
    public static void main(String args[])throws Exception{
        System.out.println("Server...");
        new S2Server();
    }
}
