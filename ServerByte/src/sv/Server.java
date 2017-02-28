/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

class Client {

    int port;
    int stt;
    OutputStream os;
}

class Room {

    int type;
    Hashtable<String, ClientInfo> htUser;

}

class ClientInfo {

    OutputStream os;
    int stt;
}

public class Server extends javax.swing.JFrame {

    ArrayList<Integer> arrayI = new ArrayList<>();
    public static int defaultPort = 51195;
    Hashtable<Object, Room> htRoom = new Hashtable<>();
    Hashtable<Object, Client> htClient = new Hashtable<>();
    String nameDb = "nguyenhuutien";
    String nameTb = "chat";
    Connection connection;
    Statement statement;
    Get get = new Get();

    public Server() {

        try {
            initComponents();

            String url = System.getProperty("user.dir", null);
            String urlIcon;
            urlIcon = url + "\\icon\\162.png";
            btStart.setIcon(new javax.swing.ImageIcon(urlIcon));
            urlIcon = url + "\\icon\\17.png";
            btStart.setBackground(new Color(255, 204, 255));
            btExit.setIcon(new javax.swing.ImageIcon(urlIcon));
            urlIcon = url + "\\icon\\46.png";
            btExit.setBackground(new Color(255, 204, 255));
            btDel.setIcon(new javax.swing.ImageIcon(urlIcon));
            urlIcon = url + "\\icon\\9.png";
            btDel.setBackground(new Color(255, 204, 255));
            getContentPane().setBackground(new Color(153, 235, 255));
            setLocationRelativeTo(null);
            setResizable(false);
            connection = get.createConnnection(nameDb);
            statement = get.createStatement(connection);
            String userList = get.getList(nameTb);
            String[] data = userList.split("[|]");
            for (int i = 0; i < data.length; i++) {
                Client c = new Client();
                c.stt = 0;
                c.port = 0;
                c.os = null;
                htClient.put(data[i], c);
            }
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent we) {
                }

                @Override
                public void windowClosing(WindowEvent we) {
                    get.delMess();
                    System.exit(0);
                }

                @Override
                public void windowClosed(WindowEvent we) {
                }

                @Override
                public void windowIconified(WindowEvent we) {
                }

                @Override
                public void windowDeiconified(WindowEvent we) {
                }

                @Override
                public void windowActivated(WindowEvent we) {
                }

                @Override
                public void windowDeactivated(WindowEvent we) {
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class readData implements Runnable {

        Socket socket;
        InputStream is;
        OutputStream os;
        int port;

        public readData(Socket s) {
            try {
                socket = s;
                port = socket.getPort();
                os = socket.getOutputStream();
                is = socket.getInputStream();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void run() {
            try {
                byte[] size = new byte[9];
                while (is.read(size) != 0) {
                    String userOnl = "";
                    String userOff = "";
                    String userList = "";
                    String userRoom = "";
                    int bytesRead = 0;
                    int bytesToRead = 0;
                    bytesToRead = ByteBuffer.wrap(size).asIntBuffer().get();
                    byte[] mybytearray = new byte[bytesToRead];
                    while (bytesRead < bytesToRead) {
                        bytesRead += is.read(mybytearray, bytesRead, bytesToRead - bytesRead);
                    }
                    String format = new String(mybytearray, 0, 3, "UTF-8");
                    String cmd = new String(mybytearray, 3, 3, "UTF-8");
                    String extra = new String(mybytearray, 6, 4, "UTF-8");
                    taChat.append(format + " " + cmd + " " + extra + "\n");
                    switch (format) {
                        case "TXT":
                            String data = new String(mybytearray, 10, mybytearray.length - 10, "UTF-8");
                            System.out.println(data);
                            String[] dt = data.split("[|]");
                            switch (cmd) {
                                case "LOG":
                                    if (extra.equals("null")) {
                                        if (get.isTrue(nameTb, dt[0], dt[1])) {
                                            if (get.isConnected(nameTb, dt[0]) == false) {
                                                String str = "TXTSUCnull";
                                                byte[] login = str.getBytes(Charset.forName("UTF-8"));
                                                byte[] sizeLogin = ByteBuffer.allocate(9).putInt(login.length).array();
                                                os.write(sizeLogin);
                                                os.write(login);
                                                os.flush();
                                            } else {
                                                String str = "TXTEXISAME";
                                                byte[] login = str.getBytes(Charset.forName("UTF-8"));
                                                byte[] sizeLogin = ByteBuffer.allocate(9).putInt(login.length).array();
                                                os.write(sizeLogin);
                                                os.write(login);
                                                os.flush();
                                            }
                                        } else {
                                            String str = "TXTEXIFALS";
                                            byte[] login = str.getBytes(Charset.forName("UTF-8"));
                                            byte[] sizeLogin = ByteBuffer.allocate(9).putInt(login.length).array();
                                            os.write(sizeLogin);
                                            os.write(login);
                                            os.flush();
                                        }
                                    } else if (extra.equals("ACCE")) {
                                        get.correctConnect(nameTb, dt[0], 1);
                                        Client cl = new Client();
                                        cl = htClient.get(dt[0]);
                                        cl.stt = 1;
                                        cl.port = port;
                                        cl.os = os;
                                        htClient.put(dt[0], cl);
                                        ArrayList<Integer> array = getRoomPort(dt[0]);
                                        for (int i = 0; i < array.size(); i++) {
                                            if (get.haveMessage(dt[0], array.get(i))) {
                                                String messOff = "";
                                                if (get.testRoom(dt[0], array.get(i)) == 1) {
                                                    messOff = "TXTOFFSING" + String.valueOf(array.get(i)) + "|" + get.getSingleUser(dt[0], array.get(i));
                                                } else {
                                                    messOff = "TXTOFFROOM" + String.valueOf(array.get(i));
                                                }
                                                byte[] mess = messOff.getBytes(Charset.forName("UTF-8"));
                                                sendClient(mess, dt[0]);
                                            }
                                        }
                                        for (int i = 0; i < array.size(); i++) {
                                            if (get.testRoom(dt[0], array.get(i)) == 2) {
                                                String str = "TXTADPnull" + String.valueOf(array.get(i));
                                                byte[] addport = str.getBytes(Charset.forName("UTF-8"));
                                                sendClient(addport, dt[0]);
                                            }
                                            userRoom = getUserRoom(array.get(i));
                                            String str = "TXTLISROOM" + userRoom;
                                            byte[] listroom = str.getBytes(Charset.forName("UTF-8"));
                                            sendRoom(listroom, array.get(i));
                                        }
                                        userOnl = get.getListUser(nameTb, 1);
                                        userOff = get.getListUser(nameTb, 0);
                                        userList = userOnl + userOff;
                                        String str = "TXTLISUSER" + userList;
                                        byte[] listuser = str.getBytes(Charset.forName("UTF-8"));
                                        sendEveryClient(listuser);
                                        sendEveryRoom(listuser);
                                        byte[] avatar = get.getAvatar(dt[0]);
                                        if (avatar != null) {
                                            int i = 0, j = 0;
                                            String ava = "AVATAR";
                                            byte[] AVA = ava.getBytes(Charset.forName("UTF-8"));
                                            byte[] sizeAvatar = ByteBuffer.allocate(4).putInt(avatar.length).array();
                                            byte[] byteImage = new byte[avatar.length + AVA.length + sizeAvatar.length];
                                            for (j = 0; j < AVA.length; j++) {
                                                byteImage[i++] = AVA[j];
                                            }
                                            j = 0;
                                            for (j = 0; j < sizeAvatar.length; j++) {
                                                byteImage[i++] = sizeAvatar[j];
                                            }
                                            j = 0;
                                            for (j = 0; j < avatar.length; j++) {
                                                byteImage[i++] = avatar[j];
                                            }

                                            sendClient(byteImage, dt[0]);
                                        }
                                    }
                                    break;

                                case "CRO":
                                    if (extra.equals("null")) {
                                        if (dt[1].equals("null")) {
                                            Room room = new Room();
                                            room.htUser = new Hashtable<>();
                                            room.type = 2;
                                            ClientInfo ci = new ClientInfo();
                                            ci.os = os;
                                            ci.stt = 1;
                                            room.htUser.put(dt[0], ci);
                                            htRoom.put(port, room);
                                            userOnl = get.getListUser(nameTb, 1);
                                            userOff = get.getListUser(nameTb, 0);
                                            userRoom = getUserRoom(port);
                                            userList = userOnl + userOff;
                                            String str = "TXTSUCROOM" + String.valueOf(port);
                                            byte[] cro = str.getBytes(Charset.forName("UTF-8"));
                                            sendClient(cro, dt[0]);
                                            str = "TXTADPROOM" + String.valueOf(port);
                                            cro = str.getBytes(Charset.forName("UTF-8"));
                                            sendClient(cro, dt[0]);
                                            str = "TXTLISUSER" + userList;
                                            cro = str.getBytes(Charset.forName("UTF-8"));
                                            sendRoom(cro, port);
                                            str = "TXTLISROOM" + userRoom;
                                            cro = str.getBytes(Charset.forName("UTF-8"));
                                            sendRoom(cro, port);
                                            if (get.isUserToOff(dt[0], port) == false) {
                                                get.insertUserToOff(dt[0], port);
                                            }

                                        } else if (dt[1].equals("null") == false) {
                                            Room room = new Room();
                                            ClientInfo ci = new ClientInfo();
                                            if (isConnect(dt[0], dt[1]) == false) {
                                                room.htUser = new Hashtable<>();
                                                room.type = 1;
                                                ci.os = os;
                                                ci.stt = 1;
                                                room.htUser.put(dt[0], ci);
                                                ClientInfo ciii = new ClientInfo();
                                                ciii.os = null;
                                                ciii.stt = 0;
                                                room.htUser.put(dt[1], ciii);
                                                String str = "TXTSUCnull" + String.valueOf(port);
                                                byte[] cro = str.getBytes(Charset.forName("UTF-8"));
                                                sendClient(cro, dt[0]);
                                                str = "TXTADDSING" + String.valueOf(port) + "|" + dt[0] + "|" + dt[1];
                                                cro = str.getBytes(Charset.forName("UTF-8"));
                                                sendClient(cro, dt[1]);
                                                htRoom.put(port, room);
                                                if (get.isUserToOff(dt[0], port) == false) {
                                                    get.insertUserToOff(dt[0], port);
                                                }
                                                if (get.isUserToOff(dt[1], port) == false) {
                                                    get.insertUserToOff(dt[1], port);
                                                }
                                            } else {
                                                if (getStt(dt[0], dt[1]) == 0) {
                                                    port = getP(dt[0], dt[1]);
                                                    room = htRoom.get(port);
                                                    ci = room.htUser.get(dt[0]);
                                                    ci.os = os;
                                                    ci.stt = 1;
                                                    room.htUser.put(dt[0], ci);
                                                    htRoom.put(port, room);
                                                    String str = "TXTSUCnull" + String.valueOf(port) + "|" + dt[0] + "|" + dt[1];
                                                    byte[] cro = str.getBytes(Charset.forName("UTF-8"));
                                                    sendClient(cro, dt[0]);
                                                }
                                            }
                                        }
                                    } else if (extra.equals("ACCE")) {
                                        get.correctConnect(nameTb, dt[0], 1);
                                        int numberRoom = Integer.parseInt(dt[1]);
                                        Room room = new Room();
                                        room = htRoom.get(numberRoom);
                                        ClientInfo ci = new ClientInfo();
                                        ci = room.htUser.get(dt[0]);
                                        ci.os = os;
                                        ci.stt = 1;
                                        room.htUser.put(dt[0], ci);
                                        htRoom.put(numberRoom, room);
                                        userOnl = get.getListUser(nameTb, 1);
                                        userOff = get.getListUser(nameTb, 0);
                                        userList = userOnl + userOff;
                                        userRoom = getUserRoom(numberRoom);
                                        String str = "TXTLISUSER" + userList;
                                        byte[] acce = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeAcce = ByteBuffer.allocate(9).putInt(acce.length).array();
                                        os.write(sizeAcce);
                                        os.write(acce);
                                        os.flush();

                                        str = "TXTLISROOM" + userRoom;
                                        acce = str.getBytes(Charset.forName("UTF-8"));
                                        sendRoom(acce, numberRoom);
                                        if (dt[2].equals("ROOM")) {
                                            str = "TXTPORROOM" + dt[1];
                                            acce = str.getBytes(Charset.forName("UTF-8"));
                                            sendClient(acce, dt[0]);
                                        }
                                    }
                                    break;
                                case "CHA":
                                    String chat = "";
                                    String cmdOff = "";
                                    String off = "";
                                    userRoom = getUserRoom(Integer.parseInt(dt[0]));
                                    String dta[] = userRoom.split("[|]");
                                    String s = "";
                                    String st = "";
                                    chat = CorrectChat(dt);
                                    if (extra.equals("ROOM")) {
                                        s = "TXTCHAROOM" + dt[1] + " : " + chat;
                                        st = "TXTOFFROOM" + dt[0] + "|" + dt[1];
                                    } else if (extra.equals("SING")) {
                                        s = "TXTCHASING" + dt[1] + " : " + chat;
                                        st = "TXTOFFSING" + dt[0] + "|" + dt[1];
                                    }
                                    cmdOff = "OFFTXT";
                                    byte[] CMDOFF = cmdOff.getBytes(Charset.forName("UTF-8"));
                                    off = dt[1] + " : " + chat;
                                    byte[] OFF = off.getBytes(Charset.forName("UTF-8"));
                                    byte[] SIZEOFF = ByteBuffer.allocate(5).putInt(OFF.length).array();
                                    byte[] MESSOFF = new byte[CMDOFF.length + SIZEOFF.length + OFF.length];
                                    int i = 0;
                                    int j = 0;
                                    while (j < CMDOFF.length) {
                                        MESSOFF[i++] = CMDOFF[j++];
                                    }
                                    j = 0;
                                    while (j < SIZEOFF.length) {
                                        MESSOFF[i++] = SIZEOFF[j++];
                                    }
                                    j = 0;
                                    while (j < OFF.length) {
                                        MESSOFF[i++] = OFF[j++];
                                    }
                                    byte[] cha = s.getBytes(Charset.forName("UTF-8"));
                                    sendRoomEx(cha, Integer.parseInt(dt[0]), os);
                                    for (int k = 0; k < dta.length; k += 2) {
                                        Client client = new Client();
                                        client = htClient.get(dta[k]);
                                        int hm;
                                        if (get.isConnected(nameTb, dta[k])) {
                                            hm = 0;
                                        } else {
                                            hm = 1;
                                        }
                                        if (extra.equals("ROOM")) {
                                            get.insertMessage(dta[k], Integer.parseInt(dt[0]), MESSOFF, 2, hm);
                                        } else {
                                            get.insertMessage(dta[k], Integer.parseInt(dt[0]), MESSOFF, 1, hm);
                                        }
                                        if (testStt(Integer.parseInt(dt[0]), dta[k]) == false) {
                                            byte[] chaa = st.getBytes(Charset.forName("UTF-8"));
                                            sendClient(chaa, dta[k]);
                                        }
                                    }
                                    break;
                                case "LIS":
                                    Room roo = new Room();
                                    roo = htRoom.get(Integer.parseInt(dt[1]));
                                    ClientInfo cii = new ClientInfo();
                                    cii = roo.htUser.get(dt[0]);
                                    cii.stt = 1;
                                    cii.os = os;
                                    roo.htUser.put(dt[0], cii);
                                    htRoom.put(Integer.parseInt(dt[1]), roo);
                                    if (get.testRoom(dt[0], Integer.parseInt(dt[1])) == 2) {
                                        userOnl = get.getListUser(nameTb, 1);
                                        userOff = get.getListUser(nameTb, 0);
                                        userRoom = getUserRoom(Integer.parseInt(dt[1]));
                                        userList = userOnl + userOff;
                                        String str = "TXTLISUSER" + userList;
                                        byte[] lis = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeLis = ByteBuffer.allocate(9).putInt(lis.length).array();
                                        os.write(sizeLis);
                                        os.write(lis);
                                        os.flush();
                                        str = "TXTLISROOM" + userRoom;
                                        lis = str.getBytes(Charset.forName("UTF-8"));
                                        sizeLis = ByteBuffer.allocate(9).putInt(lis.length).array();
                                        os.write(sizeLis);
                                        os.write(lis);
                                        os.flush();
                                    }
                                    byte[] messOff = get.getMessage(dt[0], Integer.parseInt(dt[1]));
                                    if (messOff != null) {
                                        byte[] sizeLis = ByteBuffer.allocate(9).putInt(messOff.length).array();
                                        os.write(sizeLis);
                                        os.write(messOff);
                                        os.flush();
                                    }
                                    break;
                                case "ADD":
                                    Room rooo = new Room();
                                    rooo = htRoom.get(Integer.parseInt(dt[0]));
                                    ClientInfo ciii = new ClientInfo();
                                    ciii.stt = 0;
                                    ciii.os = os;
                                    rooo.htUser.put(dt[2], ciii);
                                    htRoom.put(Integer.parseInt(dt[0]), rooo);
                                    String str1 = "TXTADDROOM" + dt[0] + "|" + dt[1];
                                    byte[] add = str1.getBytes(Charset.forName("UTF-8"));
                                    sendClient(add, dt[2]);
                                    str1 = "TXTADPROOM" + String.valueOf(port);
                                    add = str1.getBytes(Charset.forName("UTF-8"));
                                    sendClient(add, dt[2]);
                                    userRoom = getUserRoom(Integer.parseInt(dt[0]));
                                    str1 = "TXTLISROOM" + userRoom;
                                    add = str1.getBytes(Charset.forName("UTF-8"));
                                    sendRoom(add, Integer.parseInt(dt[0]));
                                    if (get.isUserToOff(dt[2], port) == false) {
                                        get.insertUserToOff(dt[2], port);
                                    }

                                    break;
                                case "SIG":
                                    if (get.isTrue(nameTb, dt[0], dt[1])) {
                                        String str = "TXTEXInull";
                                        byte[] sigin = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeSigin = ByteBuffer.allocate(9).putInt(sigin.length).array();
                                        os.write(sizeSigin);
                                        os.write(sigin);
                                        os.flush();
                                    } else {
                                        String str = "TXTSUCnull";
                                        byte[] sigin = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeSigin = ByteBuffer.allocate(9).putInt(sigin.length).array();
                                        os.write(sizeSigin);
                                        os.write(sigin);
                                        os.flush();
                                        get.addUser(nameTb, dt[0], dt[1]);
                                    }
                                    break;
                                case "UPD":
                                    if (get.updatePass(nameTb, dt[0], dt[1]) != 0) {
                                        String str = "TXTSUCnull";
                                        byte[] update = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeUpdate = ByteBuffer.allocate(9).putInt(update.length).array();
                                        os.write(sizeUpdate);
                                        os.write(update);
                                        os.flush();
                                    } else {
                                        String str = "TXTEXInull";
                                        byte[] update = str.getBytes(Charset.forName("UTF-8"));
                                        byte[] sizeUpdate = ByteBuffer.allocate(9).putInt(update.length).array();
                                        os.write(sizeUpdate);
                                        os.write(update);
                                        os.flush();
                                    }
                                    break;
                                case "EXI":
                                    if (extra.equals("ROOM")) {
                                        removeUserRoom(dt[0], Integer.parseInt(dt[1]));
                                        userRoom = getListRoom(Integer.parseInt(dt[1]));
                                        String str = "TXTLISROOM" + userRoom;
                                        byte[] list = str.getBytes(Charset.forName("UTF-8"));
                                        sendRoom(list, Integer.parseInt(dt[1]));
                                        str = "TXTEXPROOM" + dt[1];
                                        list = str.getBytes(Charset.forName("UTF-8"));
                                        sendClient(list, dt[0]);
                                        str = "TXTOUTROOM" + dt[0] + " has out room!";
                                        list = str.getBytes(Charset.forName("UTF-8"));
                                        sendRoom(list, Integer.parseInt(dt[1]));
                                        socket.close();
                                    } else if (extra.equals("TEMP")) {
                                        if (dt[1].equals("null") == false) {
                                            changeStt(dt[0], Integer.parseInt(dt[1]));
                                        } else {
                                            removeUser(dt[0]);
                                            socket.close();
                                            get.correctConnect(nameTb, dt[0], 0);
                                            userOnl = get.getListUser(nameTb, 1);
                                            userOff = get.getListUser(nameTb, 0);
                                            userList = userOnl + userOff;
                                            String str = "TXTLISUSER" + userList;
                                            byte[] list = str.getBytes(Charset.forName("UTF-8"));
                                            sendEveryClient(list);
                                            sendEveryRoom(list);
                                            ArrayList<Integer> array = getRoomPort(dt[0]);
                                            for (i = 0; i < array.size(); i++) {
                                                userRoom = getUserRoom(array.get(i));
                                                str = "TXTLISROOM" + userRoom;
                                                list = str.getBytes(Charset.forName("UTF-8"));
                                                sendRoom(list, array.get(i));
                                                str = "TXTOUTCHAT" + dt[0] + " has offline!";
                                                list = str.getBytes(Charset.forName("UTF-8"));
                                                sendRoom(list, array.get(i));
                                            }
                                        }
                                    }
                                    break;
                            }
                            break;
                        case "IMG":
                            int i = 0;
                            byte[] sizeImg = new byte[4];
                            for (int j = 6; j < 10; j++) {
                                sizeImg[i++] = mybytearray[j];
                            }
                            int ex = ByteBuffer.wrap(sizeImg).asIntBuffer().get();
                            sendRoomEx(mybytearray, ex, os);

                            i = 0;
                            for (int j = 10; j < 14; j++) {
                                sizeImg[i++] = mybytearray[j];
                            }
                            int ex1 = ByteBuffer.wrap(sizeImg).asIntBuffer().get();
                            String user = new String(mybytearray, 14, ex1, "UTF-8");
                            userRoom = getUserRoom(ex);
                            String dta[] = userRoom.split("[|]");
                            String cmdOff = "";
                            cmdOff = "OFFIMG";
                            byte[] CMDOFF = cmdOff.getBytes(Charset.forName("UTF-8"));
                            byte[] SIZEOFF = ByteBuffer.allocate(5).putInt((mybytearray.length - 14 - ex1)).array();
                            byte[] MESSOFF = new byte[CMDOFF.length + SIZEOFF.length + (mybytearray.length - 10)];
                            i = 0;
                            int j = 0;
                            while (j < CMDOFF.length) {
                                MESSOFF[i++] = CMDOFF[j++];
                            }
                            j = 0;
                            while (j < SIZEOFF.length) {
                                MESSOFF[i++] = SIZEOFF[j++];
                            }
                            j = 10;
                            while (j < mybytearray.length) {
                                MESSOFF[i++] = mybytearray[j++];
                            }
                            for (j = 0; j < dta.length; j += 2) {
                                int hm;
                                if (get.isConnected(nameTb, dta[j])) {
                                    hm = 0;
                                } else {
                                    hm = 1;
                                }

                                if (cmd.equals("ROO")) {
                                    get.insertMessage(dta[j], ex, MESSOFF, 2, hm);
                                } else {
                                    get.insertMessage(dta[j], ex, MESSOFF, 1, hm);
                                }
                            }
                            break;
                        case "AVA":
                            if (cmd.equals("TAR")) {
                                i = 0;
                                byte[] sizeAvatar = new byte[4];
                                for (j = 6; j < 10; j++) {
                                    sizeAvatar[i++] = mybytearray[j];
                                }
                                ex = ByteBuffer.wrap(sizeAvatar).asIntBuffer().get();
                                byte[] avatar = new byte[mybytearray.length - 10 - ex];
                                user = new String(mybytearray, 10, ex, "UTF-8");
                                i = 0;
                                for (j = 10 + ex; j < mybytearray.length; j++) {
                                    avatar[i++] = mybytearray[j];
                                }
                                // System.out.println("avatar="+avatar.length);
                                get.setAvatar(user, avatar);
                            }
                            break;

                    }
                }

            } catch (IOException ex) {
                System.out.println("Socket closed!");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        btStart = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        btDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taChat.setColumns(20);
        taChat.setRows(5);
        jScrollPane1.setViewportView(taChat);

        btStart.setText("Khởi động");
        btStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartActionPerformed(evt);
            }
        });

        btExit.setText("Thoát");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        btDel.setText("Xóa");
        btDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btStart)
                .addGap(28, 28, 28)
                .addComponent(btExit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btDel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btDel, btExit, btStart});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btStart)
                    .addComponent(btExit)
                    .addComponent(btDel)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btDel, btExit, btStart});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //lang nghe moi nguoi
    public class createServer implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSock = new ServerSocket(defaultPort);
                while (true) {
                    Socket socket = serverSock.accept();
                    Thread listen = new Thread(new readData(socket));
                    listen.start();
                }
            } catch (Exception ex) {
                taChat.append("Create server fail!\n");
            }
        }

    }

    public void sendClient(byte[] mybyte, String username) {
        Client cl = (Client) htClient.get(username);
        if (cl.os != null) {
            try {
                OutputStream os = cl.os;
                byte[] size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
                os.write(size);
                os.write(mybyte);
                os.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendEveryClient(byte[] mybyte) {
        Collection<Client> clients = htClient.values();
        byte[] size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
        for (Client cl : clients) {
            if (cl.os != null) {
                try {
                    OutputStream os = cl.os;
                    os.write(size);
                    os.write(mybyte);
                    os.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendRoom(byte[] mybyte, int port) {
        Room room = (Room) htRoom.get(port);
        byte[] size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
        Collection<ClientInfo> clientInfos = room.htUser.values();
        for (ClientInfo clientInfo : clientInfos) {
            if (clientInfo.os != null) {
                try {
                    OutputStream os = clientInfo.os;
                    os.write(size);
                    os.write(mybyte);
                    os.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendRoomEx(byte[] mybyte, int port, OutputStream Os) {
        Room room = (Room) htRoom.get(port);
        byte[] size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
        Collection<ClientInfo> clientInfos = room.htUser.values();
        for (ClientInfo clientInfo : clientInfos) {
            if (clientInfo.os != null && clientInfo.os != Os && clientInfo.stt == 1) {
                try {
                    size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
                    OutputStream os = clientInfo.os;
                    os.write(size);
                    os.write(mybyte);
                    os.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendEveryRoom(byte[] mybyte) {
        Collection<Room> r = htRoom.values();
        byte[] size = ByteBuffer.allocate(9).putInt(mybyte.length).array();
        for (Room room : r) {
            Collection<ClientInfo> clientInfos = room.htUser.values();
            for (ClientInfo clientInfo : clientInfos) {
                if (clientInfo.os != null) {
                    try {
                        OutputStream os = clientInfo.os;
                        os.write(size);
                        os.write(mybyte);
                        os.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public String getUserRoom(int port) {
        String user = "";
        Room room = htRoom.get(port);
        Enumeration<String> USER = room.htUser.keys();
        Set<Map.Entry<String, ClientInfo>> entries = room.htUser.entrySet();
        for (Map.Entry<String, ClientInfo> entry : entries) {
            if (get.isConnected(nameTb, entry.getKey())) {
                user += entry.getKey() + "|1|";
            } else {
                user += entry.getKey() + "|0|";
            }
        }
        return user;
    }

    public String getListRoom(int port) {
        String user = "";
        Room room = htRoom.get(port);
        Enumeration<String> USER = room.htUser.keys();
        Set<Map.Entry<String, ClientInfo>> entries = room.htUser.entrySet();
        for (Map.Entry<String, ClientInfo> entry : entries) {
            if (testStt(port, entry.getKey())) {
                user += entry.getKey() + "|1|";
            } else {
                user += entry.getKey() + "|0|";
            }
        }
        return user;
    }

    public ArrayList<Integer> getRoomPort(String user) {
        ArrayList<Integer> arr = new ArrayList<>();
        Set<Map.Entry<Object, Room>> entries = htRoom.entrySet();
        for (Map.Entry<Object, Room> entry : entries) {
            Room room = entry.getValue();
            if (room.htUser.containsKey(user)) {
                arr.add((Integer) entry.getKey());
            }
        }
        return arr;
    }

    public void removeUser(String user) {
        try {
            Set<Map.Entry<Object, Room>> entries = htRoom.entrySet();
            for (Map.Entry<Object, Room> entry : entries) {
                Room room = new Room();
                room = entry.getValue();
                ClientInfo ci = new ClientInfo();
                ci = room.htUser.get(user);
                if (ci != null && ci.stt != 0) {
                    ci.stt = 0;
                    ci.os.close();
                    ci.os = null;
                    room.htUser.put(user, ci);
                    htRoom.put(entry.getKey(), room);
                }
            }
            Client cl = new Client();
            cl = htClient.get(user);
            if (cl != null && cl.stt != 0) {
                cl.stt = 0;
                cl.os.close();
                cl.os = null;
                htClient.put(user, cl);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeUserRoom(String user, int port) {
        Room room = new Room();
        room = htRoom.get(port);
        room.htUser.remove(user);
        htRoom.put(port, room);
    }

    public void changeStt(String user, int port) {
        try {
            Room room = new Room();
            room = htRoom.get(port);
            ClientInfo ci = new ClientInfo();
            ci = room.htUser.get(user);
            if (ci != null && ci.stt != 0) {
                ci.stt = 0;
                ci.os.close();
                ci.os = null;
                room.htUser.put(user, ci);
                htRoom.put(port, room);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String CorrectChat(String data[]) {
        String chat = "";
        for (int j = 2; j < data.length; j++) {
            if (j != data.length - 1) {
                chat += data[j] + "|";
            } else {
                chat += data[j];
            }
        }
        return chat;
    }

    public boolean testStt(int port, String user) {
        Room ro = new Room();
        ro = htRoom.get(port);
        ClientInfo ci = new ClientInfo();
        ci = ro.htUser.get(user);
        if (ci.stt == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConnect(String user1, String user2) {
        Collection<Room> rooms = htRoom.values();
        for (Room room : rooms) {

            if (room.htUser.get(user1) != null && room.htUser.get(user2) != null && room.type == 1) {
                return true;
            }
        }
        return false;
    }

    public int getStt(String user1, String user2) {
        Collection<Room> rooms = htRoom.values();
        for (Room room : rooms) {
            if (room.htUser.get(user1) != null && room.htUser.get(user2) != null && room.type == 1) {
                ClientInfo ci = new ClientInfo();
                ci = room.htUser.get(user1);
                return ci.stt;
            }
        }
        return 0;

    }

    public int getP(String user1, String user2) {
        Set<Map.Entry<Object, Room>> entries = htRoom.entrySet();
        for (Map.Entry<Object, Room> entry : entries) {
            Room room = entry.getValue();
            if (room.htUser.get(user1) != null && room.htUser.get(user2) != null && room.htUser.size() == 2) {
                return (Integer) entry.getKey();
            }
        }
        return 0;

    }

    private void btStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartActionPerformed
        Thread t = new Thread(new createServer());
        t.start();
        taChat.append("Create server successful!\n");
        btStart.setEnabled(false);
    }//GEN-LAST:event_btStartActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        get.delMess();
        System.exit(0);
    }//GEN-LAST:event_btExitActionPerformed

    private void btDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDelActionPerformed
        DelMem delMem = new DelMem(nameTb);
        delMem.setVisible(true);
    }//GEN-LAST:event_btDelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDel;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btStart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taChat;
    // End of variables declaration//GEN-END:variables
}
