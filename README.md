# 🔐 Java Encrypted Chat (AES + Socket Programming)

This project demonstrates a simple **client-server chat application** in Java that uses **AES encryption** to secure communication over sockets.  

The server generates a secret AES key, encrypts outgoing messages, and shares the secret key with the client (Base64-encoded).  
The client receives the secret key, decrypts encrypted messages, and can also send plain text back to the server.

---

## 🚀 Features

- Server (`S2Server`)  
  - Generates AES key dynamically.  
  - Encrypts all outgoing messages with AES.  
  - Can send the secret key to the client (on typing `pass`).  
  - Handles concurrent read & write with threads.  

- Client (`C2Client`)  
  - Connects to the server via sockets.  
  - Receives and stores AES key from the server.  
  - Decrypts received encrypted messages.  
  - Supports user interaction via console.  

- General  
  - Uses **Base64** encoding to transmit keys & encrypted data.  
  - Handles clean exit with `"exit"`.  
  - Threaded for simultaneous read/write.  

---

## 📂 Project Structure

.
├── S2Server.java # Server-side implementation
└── C2Client.java # Client-side implementation


---

## ⚙️ How It Works

1. **Start the Server**  
   ```bash
   javac S2Server.java
   java S2Server
Waits for client connection on port 8887.

Generates AES key.

Allows sending encrypted messages.

Start the Client

javac C2Client.java
java C2Client


Connects to server IP (192.168.131.1 in this code — change if needed).

Waits for messages from server.

Receives and decodes AES key when server sends it.

Commands

On Server Side

pass → Sends AES key to client.

exit → Terminates connection.

On Client Side

Check → Prompt to enter an encrypted message (manual testing for decryption).

exit → Terminates connection.

🖥️ Example Usage

Server Console

Server...
Server is waiting for connection...
Connection done..
SecretKey Sended
SecretKey : wN4kfjS6u5X6QEt5nB/1sQ==
Client : Hello


Client Console

Client...
Sending Connection...
Connection Done...
Server : wN4kfjS6u5X6QEt5nB/1sQ==
Pass equals javax.crypto.spec.SecretKeySpec@1234abcd
Server : HJ3kdjS8ss==
Decrypted Text: Hello

🛠️ Requirements

Java 8 or higher

Console/terminal for running server & client

Both programs must run on the same network (update IP in C2Client.java accordingly)

🔮 Improvements (Future Work)

Implement secure key exchange (e.g., RSA for AES key transfer).

Add GUI for chat instead of console.

Multi-client support (broadcast messages).

Save encrypted chat logs.

📜 License

This project is for educational purposes to demonstrate socket programming & AES encryption in Java.
Feel free to use, modify, and expand.
