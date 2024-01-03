import java.net.*;
import java.io.*;
public class Client {
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    Client()
    {
        try {
            System.out.println("Sending request to the server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch (ConnectException ce)
        {
            System.out.print("Server has not started yet");
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void startReading()
    {
        Runnable r1 = ()->{
            System.out.println("Reader Started");
            try{
            while(!socket.isClosed())
            {
                String msg = br.readLine();
                System.out.println("Server: " + msg);
                if(msg.equals("quit()"))
                {
                    socket.close();
                    System.out.println("connection is closed");
                    System.exit(0);
                    break;
                }
            }
        }
            catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("Client reading stopped via exception");
            }
            };
        new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writer Started");
            
                try{
                    while(!socket.isClosed())
                {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String msg = br.readLine();
                out.println(msg);
                out.flush();
                if(msg.equals("quit()")){
                    socket.close();
                    break;
                }
                }
                System.out.println("Client writing stopped");
            }
                catch(Exception e)
                {
                    //e.printStackTrace();
                    System.out.println("Client writing stopped via exception");
                }
        };
        new Thread(r2).start();
    }
    
    public static void main(String[] args) {
        System.out.println("this is client");
        Client client = new Client();
    }
}
