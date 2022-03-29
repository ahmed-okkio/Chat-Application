package backend;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;


public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                Socket connectionSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(connectionSocket);

                Thread thread =  new Thread(clientHandler);
                thread.start();
            }
            
        } catch (IOException error) {

        }
    }

    public void shutdownServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Server Started");

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the server port: ");
            int serverPort = Integer.parseInt(scanner.nextLine());
            System.out.println("Running on PORT: "+ serverPort);
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Server server = new Server(serverSocket);
            server.startServer();

            scanner.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
        
    }
        
}
