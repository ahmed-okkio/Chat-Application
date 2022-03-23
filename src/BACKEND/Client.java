package backend;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import gui.Window_1;



public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
    public String username;
    public String memberUsernames[];
    // ROLES: 0 = Unassigned, 1 = Coordinator, 2 = Member
    private int role = 0;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            this.username = username;
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }

    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while ( socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write("MESSAGE,"+username + ": "+ message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            scanner.close();
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }

    }

    public void listenforMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromChat[];

                while(socket.isConnected()) {
                    try {
                        messageFromChat = bufferedReader.readLine().split(",");
                        String messageType = messageFromChat[0];
                        String message = messageFromChat[1];

                        if(messageType.equals("ROLE")) {
                            role = Integer.parseInt(message);
                            welcomeMessage();
                        } else if(messageType.equals("PING")) {
                            keepAplive();
                        } else {
                            System.out.println(message);
                        }
                    } catch (IOException error) {
                        shutdownClient(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    private void welcomeMessage() {
        switch(role) {
            case 1:
                System.out.println("Welcome to the chat room, you are the coordinator.");
                break;
            case 2:
                System.out.println("Welcome to the chat room.");
        }
    }

    public void keepAplive() {

    }

    public void shutdownClient(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if ( bufferedReader != null) {
                bufferedReader.close();
            }
            if ( bufferedWriter != null) {
                bufferedWriter.close();
            }
            if ( socket != null) {
                socket.close();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Enter the server IP address: ");
        String ip_address = scanner.nextLine();
        // System.out.println(ip_address);

        System.out.println("Enter the server port: ");
        String port = "25565";
        System.out.println(port);

        Socket socket = new Socket(ip_address,Integer.parseInt(port));
        Client client = new Client(socket, username);

        client.listenforMessage();
        client.sendMessage();

        scanner.close();
    }
}
