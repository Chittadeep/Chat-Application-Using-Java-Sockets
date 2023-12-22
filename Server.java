import java.io.*;
import java.net.*;

public class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server()
    {
        try{
        server = new ServerSocket(7777);
        System.err.println("Server started");
        System.out.println("Server waiting for accepting connection");
        socket=server.accept();

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        startReading();
        startWriting();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private void startReading()
    {
        Runnable r1 = ()->{
            System.err.println("Reader started");
            try{
            while(!socket.isClosed())
            {
                String msg = br.readLine();
                System.out.println("Client: " + msg);
                if(msg.equals("quit()"))
                {
                    socket.close();
                    System.out.println("Server reading stopped");
                    break;
                }
                
            }}
            catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("Server reading stopped via exception");
            }
            };
        new Thread(r1).start();
    }

    private void startWriting()
    {
        Runnable r2 = ()->{
            System.err.println("Writer started");
            try{
            while(!socket.isClosed()){
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            String msg = br1.readLine();
            out.println(msg);
            out.flush();
            if(msg.equals("quit()"))
                socket.close();
                break;
            }
            System.out.println("Server Writing Stopped");
        }
            catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("Server writing stopped via exception");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.print("This is server");
        Server server = new Server();
    }
}