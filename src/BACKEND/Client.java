package backend;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.ArrayList;
import gui.MainRoom;
import t.p;


public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public MainRoom mainRoom = null;

    public String username;
    public String memberUsernames[];
    private Timer timer = new Timer();
    // ROLES: 0 = Unassigned, 1 = Coordinator, 2 = Member
    public int role = 0;
    public class MemberState {
        public MemberState(String username, String address) {
            this.ID = username;
            this.IP = address.toString();
        }
        public String ID;
        public String IP;
    };
    public ArrayList<MemberState> members = new ArrayList<>();

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

            // Registering username with the server.
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            this.listenforMessage();
 
            while (role == 0){
            }

            System.out.println("Welcome to the chat room.");
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }

    }
    public void EnableCoordinator() {
        // checkClientAlive();
        if (mainRoom != null) {
            mainRoom.updateMessages(MainRoom.Rooms.MAIN_ROOM,"You are now the coordinator.");
            mainRoom.updateCoordinator(true);
            System.out.println("You are now the coordinator.");
        }
        // requestMemberState();
    }

    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);
            while ( socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write("MAINROOM,"+username + ": "+ message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            scanner.close();
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }

    }
    public void sendMessageUI(String message) {
        try {
            if ( socket.isConnected()) {
                bufferedWriter.write("MAINROOM,"+username + ": "+ message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
                mainRoom.updateMessages(MainRoom.Rooms.MAIN_ROOM,"You: "+ message);
            }
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }
        
    }
    public void sendPrivateMessageUI(String recipient, String message) {
        try {
            if ( socket.isConnected()) {
                bufferedWriter.write("PRIVATE,"+recipient+","+username +","+ username +": "+ message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
                mainRoom.updateMessages(recipient,"You: "+ message);

            }
        } catch (IOException error) {
            shutdownClient(socket, bufferedReader, bufferedWriter);
        }
        
    }
    
    public void listenforMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromChat[];
                String rawMessage;
                String messageType;
                String message;
                while(socket.isConnected()) {
                    try {
                        rawMessage = bufferedReader.readLine();
                        // System.out.println("RAW MESSAGE: " + rawMessage);
                        messageFromChat = rawMessage.split(",");
                        messageType = messageFromChat[0];
                        message = messageFromChat[1];

                        if(messageType.equals("ROLE")) {

                            role = Integer.parseInt(message);
                            if(role == 1) {
                                EnableCoordinator();
                            } 

                        } else if(messageType.equals("PING")) {
                            if(messageFromChat[1].equals(username)) {
                                keepAlive();
                            }

                        } else if(messageType.equals("PRIVATE")) {
                            // INDEX 1 = recipient, 2 = sender, 3 = message
                            if(messageFromChat[1].equals(username)) {
                                mainRoom.updateMessages(messageFromChat[2],messageFromChat[3]);
                            }
                        } else {
                            if (mainRoom != null) {
                                mainRoom.updateMessages(messageType,message);
                            }

                        }

                        if (messageType.equals("COMMAND")) {
                            if(messageFromChat[1].equals("CLEANSTATE")) {
                                members.clear();
                            }
                            if(messageFromChat[1].equals("UPDATESTATE")) {
                                if(!messageFromChat[2].equals(username)){
                                    //INDEX 2 = ID, 3 = IP
                                    MemberState updatedMember = new MemberState(messageFromChat[2],messageFromChat[3]);
                                    members.add(updatedMember);
                                }
                                if (mainRoom != null) {
                                    mainRoom.updateMembersList();
                                }
                            }
                            
                        }
                    } catch (IOException error) {
                        shutdownClient(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void keepAlive() {
        try {
            bufferedWriter.write("PONG," +username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException error) {
            // shutdownClient(socket, bufferedReader, bufferedWriter);
            error.printStackTrace();
        }
    }
    public void requestClientHandlerShutdown() {
        try {
            bufferedWriter.write("COMMAND," + "SHUTDOWN,"+username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException error) {
            // shutdownClient(socket, bufferedReader, bufferedWriter);
            error.printStackTrace();
        }  
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
