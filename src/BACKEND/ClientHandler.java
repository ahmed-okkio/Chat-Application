package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Timer timer = new Timer();
    public int pingAttempts = 3;
    private String clientUsername;
    private boolean isConnected = false;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            this.clientUsername = bufferedReader.readLine();
            socket.getRemoteSocketAddress();
            assignClientRole(2);
            updateCoordinatorState();
            clientHandlers.add(this);
            checkClientAlive();
            
            isConnected = true;
            broadcastMessage("SERVER: " + clientUsername + " has entered the room.");
            System.out.println(clientUsername + " connected.");
        } catch (IOException error)  {
            shutdownClientHandler(socket, bufferedReader, bufferedWriter);
        }
    }
    
    @Override
    public void run() {
        String messageFromChat[];
        String messageType;
        String message;

        while(isConnected) {
            try {
                messageFromChat = bufferedReader.readLine().split(",");
                messageType = messageFromChat[0];
                message = messageFromChat[1];
                if (messageType.equals("PONG")) {
                    pingAttempts = 3;
                    
                } else {
                    broadcastMessage(message);
                }
            } catch (IOException error) {
                
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write("MESSAGE,"+message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException error) {
            }
        }
    }

  

    private void assignClientRole(int role) {
        // ROLES: 0 = Unassigned, 1 = Coordinator, 2 = Member
        try {
            if (clientHandlers.isEmpty()) {
                this.bufferedWriter.write("ROLE," + 1);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } else {
                this.bufferedWriter.write("ROLE," + role);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }
        } catch (IOException error) {
        }
    }

    private void updateCoordinatorState() {

    }

    private void checkClientAlive() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    pingAttempts--;
                    if(pingAttempts > 0) {
                         //Ping client
                        bufferedWriter.write("PING,"+ "Pinging Client");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        
                    } else {
                        shutdownClientHandler(socket, bufferedReader, bufferedWriter);
                    }

                } catch (IOException error) {
                    // shutdownClientHandler(socket, bufferedReader, bufferedWriter);
                }
            }
        }, 0, 3000);
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        System.out.println("Disconnecting "+ clientUsername);
        broadcastMessage("SERVER: " + clientUsername + " has left the room.");
        clientHandlers.get(0).assignClientRole(1);
    }

    public void shutdownClientHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        isConnected = false;
        removeClientHandler();
        try {
            if ( timer != null) {
                timer.cancel();
            }
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
}