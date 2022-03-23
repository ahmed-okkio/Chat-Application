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
    public int lifeScore = 3;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            this.clientUsername = bufferedReader.readLine();
            
            assignClientRole();
            clientHandlers.add(this);
            checkClientAlive();
            
            broadcastMessage("SERVER:" + clientUsername + "has entered the room.");
        } catch (IOException error)  {
            shutdownClientHandler(socket, bufferedReader, bufferedWriter);
        }
    }
    
    @Override
    public void run() {
        String messageFromChat[];
        String messageType;
        String message;

        while(socket.isConnected()) {
            try {
                messageFromChat = bufferedReader.readLine().split(",");
                messageType = messageFromChat[0];
                message = messageFromChat[1];
                if (messageType.equals("PONG")) {
                    lifeScore = 3;
                } else {

                    broadcastMessage(message);
                }
            } catch (IOException error) {
                shutdownClientHandler(socket, bufferedReader, bufferedWriter);
                break;
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
                shutdownClientHandler(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER:" + clientUsername + "has left the room.");
    }

    private void assignClientRole() {
        // ROLES: 0 = Unassigned, 1 = Coordinator, 2 = Member
        try {
            if (clientHandlers.isEmpty()) {
                this.bufferedWriter.write("ROLE," + 1);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } else {
                this.bufferedWriter.write("ROLE," + 2);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }
        } catch (IOException error) {
            shutdownClientHandler(socket, bufferedReader, bufferedWriter);
        }
    }

    private void checkClientAlive() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //Ping client
                    bufferedWriter.write("PING,"+ "Pinging Client");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    lifeScore--;
                    System.out.println("Pinging");
                } catch (IOException error) {
                    shutdownClientHandler(socket, bufferedReader, bufferedWriter);
                }
            }
        }, 0, 3000);
    }

    public void shutdownClientHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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
            if ( timer != null) {
                timer.cancel();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}