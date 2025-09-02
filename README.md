# 🔐 Java Encrypted Chat (AES + Socket Programming)

This project demonstrates a simple **client-server chat application** in Java that uses **AES encryption** to secure communication over sockets.  

The **server** generates a secret AES key, encrypts outgoing messages, and shares the key with the client (Base64-encoded).  
The **client** receives the key, decrypts encrypted messages, and can also send messages back to the server.  

---

## 🚀 Features

### 🖥️ Server (`S2Server`)
- 🔑 Dynamically generates AES key.  
- 🔒 Encrypts outgoing messages.  
- 📤 Sends secret key to client when typing `pass`.  
- 🧵 Handles concurrent **read & write** using threads.  

### 💻 Client (`C2Client`)
- 🌐 Connects to server via sockets.  
- 🔑 Receives AES key from server.  
- 🔓 Decrypts received messages.  
- 🎛️ Allows user interaction via console.  

### ⚡ General
- Uses **Base64 encoding** for key & message transmission.  
- Supports clean exit with `exit`.  
- Threaded for **simultaneous chat** (non-blocking I/O).  

---

## 📂 Project Structure

.
├── S2Server.java # Server-side implementation <br/>
└── C2Client.java # Client-side implementation


---

## ⚙️ How It Works

### 1️⃣ Start the Server
```bash
javac S2Server.java
java S2Server


Waits for client connection on port 8887.

Generates AES key.

Encrypts outgoing messages.

2️⃣ Start the Client
javac C2Client.java
java C2Client


Connects to server IP (192.168.131.1 in this code — change if needed).

Waits for messages from server.

Receives and decodes AES key when server sends it.

3️⃣ Available Commands
On Server Side

pass → Sends AES key to client.

exit → Terminates server connection.

On Client Side

Check → Enter an encrypted message manually for decryption testing.

exit → Terminates client connection.

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

☕ Java 8 or higher

🖥️ Console/terminal for running server & client

🌐 Both programs must run on the same network (update IP in C2Client.java accordingly)

🔮 Future Improvements

🔐 Implement secure key exchange (e.g., RSA for AES key transfer).

🖼️ Add a GUI interface for chat.

👥 Multi-client support (broadcast messages).

🗂️ Save encrypted chat logs for reference.

📜 License

This project is for educational purposes to demonstrate socket programming & AES encryption in Java.
Feel free to use, modify, and expand. 🚀
