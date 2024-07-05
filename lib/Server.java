import java.net.*;

import javax.naming.ldap.SortKey;

import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception{
    System.out.println("WELCOME TO SERVER ! ");       
    ServerSocket ss = new ServerSocket(8080);

    while(true){
        System.out.println("WAITING FOR CLIENT......");
        new ClientHandler(ss.accept()).start();
    }





    }





    class ClientHandler extends Thread{

        Socket socket;
        DataInputStream DIS ;
        DataOutputStream DOS ;

        public ClientHandler(Socket socket) throws Exception{
            this.socket = socket;
            DIS = new DataInputStream(socket.getInputStream());
            DOS = new DataOutputStream(socket.getInputStream());
            System.out.println("CONNECTED TO THE SERVER  !");
        }

        public String Listener() throws Exception {
            System.out.println("LISTENER IS ACTIVATE ");
            StringBuilder SB = new StringBuilder();
            int index = DIS.read();
            while(index != 0 ){
                SB.append((char)index);
                index = DIS.read();
            }
            System.out.println("LISTENER DONE !");

            return SB.toString();
        }

        public void Write() throws Exception{

        }

        public void run(){
            super.run();
            Stirng command ; //command~keyworld~value
            try{
                command = Listener();
                System.out.println("COMMAND THAT RECIEVED : "+command);
            }catch(Exception e){}
            String[] split = command.split("~"); 
            for(String s : split){
                System.out.println(s);
            }
            switch (split[0]) {
                case "command":
                    
                    break;
            
                default:
                    break;
            }

        }

    }
}