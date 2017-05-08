//Michael Ly, CS380

import java.util.*;
import java.io.*;
import java.net.*;

public class Ipv6Client {

    public static void main(String[] args)
    {
        try{
            Socket socket = new Socket("codebank.xyz", 38004);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            byte[] Ipv6Packet = new byte[40];

            byte[] destaddr = {52, 37, 88, (byte) 154};

            //fills the packet array with 0's beforehand
            Arrays.fill(Ipv6Packet, (byte) 0);

            for(int i = 0; i < 12; i++)
            {
                int length = (int) Math.pow(2,(i+1));

                System.out.println("data length: " + length);

                //version = 6 and don't implement traffic class
                Ipv6Packet[0] = (byte) 0b01100000;

                //packet[1] to packet[3] are traffic class and flow label; don't implement, which equals 0

                //payload length
                int plength = (int) Math.pow(2,(i+1));
                Ipv6Packet[4] = (byte) ((plength >> 8) & 0xFF);
                Ipv6Packet[5] = (byte) (plength & 0xFF);

                //Next header, UDP
                Ipv6Packet[6] = (byte) 17;
                //Hop limit, 20
                Ipv6Packet[7] = (byte) 20;

                //Ipv4 to Ipv6 of localhost (127.0.0.1)
                // 8 to 17 is 0

                Ipv6Packet[18] = (byte) 255;
                Ipv6Packet[19] = (byte) 255;
                Ipv6Packet[20] = (byte) 127;

                //21 and 22 is 0
                Ipv6Packet[23] = (byte) 1;

                //Ipv4 to Ipv6 of socket address 52.37.88.154
                // 24 to 33 is 0

                Ipv6Packet[34] = (byte) 255;
                Ipv6Packet[35] = (byte) 255;
                Ipv6Packet[36] = (byte) 52;
                Ipv6Packet[37] = (byte) 37;
                Ipv6Packet[38] = (byte) 88;
                Ipv6Packet[39] = (byte) 154;

                os.write(Ipv6Packet);

                //data
                for(int j = 0; j < length; j++)
                {
                    os.write(0);
                }

                System.out.print("Response: 0x");
                for(int j = 0; j < 4; j++)
                {
                    int temp = is.read();
                    System.out.printf("%02X", temp);
                }

                System.out.println("\n");

            }
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
