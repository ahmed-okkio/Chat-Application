package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;




public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Timer timer = new Timer();
    
    private String clientUsername;
    private String IP;
    private int pingAttempts = 3;
    private boolean isConnected = false;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            this.IP = socket.getRemoteSocketAddress().toString().substring(1);

            // Checking if the new connection contains a duplicate username.
            for (ClientHandler clientHandler : clientHandlers) {
                if( clientHandler.clientUsername.equals(this.clientUsername)) {
                    // Refuse connection and inform client of the reason.
                    assignClientRole(-1);
                    shutdownClientHandler(socket, bufferedReader, bufferedWriter);
                    return;
                }
            }

            // Assign role.
            assignClientRole(2);
            clientHandlers.add(this);
            // Initiate client pinging.
            checkClientAlive();
            // Push member state to client.
            updateState();
    
            isConnected = true;
            broadcastMessage("MAINROOM," + clientUsername + " has entered the room.");
            System.out.println(clientUsername + " connected.");
        } catch (IOException error)  {
            shutdownClientHandler(socket, bufferedReader, bufferedWriter);
        }
    }
    
    @Override
    public void run() {
        String rawMessage;
        String messageFromChat[];
        String messageType;
        String message;

        while(isConnected) {
            try {
                rawMessage = bufferedReader.readLine();
                messageFromChat = rawMessage.split(",");
                messageType = messageFromChat[0];
                message = messageFromChat[1];
                
                // On receiving client KeepAlive reset the client ping attempts back to 3.
                if(messageType.equals("PONG")) {
                    pingAttempts = 3;
                } else {
                    // All other messages are broadcasted to all members.
                    broadcastMessage(rawMessage);
                }
            } catch (IOException error) {
                
            }
        }
    }

    // Responsible for broadcasting messages to all connected clients.
    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException error) {
            }
        }
    }

    private void assignClientRole(int role) {
        try {
            // ROLES: 0 = Unassigned, 1 = Coordinator, 2 = Member
            if(role == -1) {
                this.bufferedWriter.write("ROLE," + role);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } else {

                if (clientHandlers.isEmpty()) {
                    this.bufferedWriter.write("ROLE," + 1);
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                } else {
                    this.bufferedWriter.write("ROLE," + role);
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }
            }
        } catch (IOException error) {
        }
    }

    // Updates state for all clients.
    private void updateState() {
        if(!clientHandlers.isEmpty()) {

            for(ClientHandler clientHandler : clientHandlers) {
                try {
                    clientHandler.bufferedWriter.write("COMMAND,"+"CLEANSTATE,");
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                } catch (IOException e) {

                }
            }
            
            for(ClientHandler clientHandler : clientHandlers) {
                for(ClientHandler clientHandler2 : clientHandlers) {
                    try {
                        clientHandler.bufferedWriter.write("COMMAND,"+"UPDATESTATE,"+clientHandler2.clientUsername+","+clientHandler2.IP);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    } catch (IOException e) {
    
                    }
                }

            }
        }
    }

    // Initiates client pinging every 3000ms.
    private void checkClientAlive() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    pingAttempts--;
                    if(pingAttempts > 0) {
                         //Ping client
                        bufferedWriter.write("PING,"+ clientUsername);
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

    // Disconnects the client.
    public void removeClientHandler() {
        broadcastMessage("MAINROOM,"+clientUsername + " has left the room.");
        if(clientHandlers.size() > 1 && clientHandlers.get(0) == this) {
            clientHandlers.get(1).assignClientRole(1);
        }
        System.out.println("Disconnecting "+ clientUsername);
        clientHandlers.remove(this);
        updateState();
    }

    // Cleans up readers,writers,timers and socket on shutdown.
    public void shutdownClientHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        isConnected = false;
        removeClientHandler();
        try {
            if ( timer != null) {
                timer.cancel();
                timer = null;
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