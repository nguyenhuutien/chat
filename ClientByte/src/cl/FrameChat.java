/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;

/**
 *
 * @author Admin
 */
public class FrameChat extends javax.swing.JFrame {

    /**
     * Creates new form FrameChat
     */
    private String username;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    int room;
    String ip;
    int port;
    ArrayList<String> userC = new ArrayList<>();
    Thread t;
    int height;
    private int picWidth = 480;
    private int picHeight = 270;
    private int sHeight;
    private int sWidth;
    private boolean Scrolled = false;
    private BufferedImage bufferedImage;
    setIcon Icon = new setIcon();

    public FrameChat(final String username, String IP, int p, InputStream ins, OutputStream ous, int ro, BufferedImage buff) {
        initComponents();
        this.username = username;
        this.bufferedImage = buff;
        pnAvatar = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, pnAvatar.getWidth(), pnAvatar.getHeight(), null);
                repaint();
            }
        };
        spAvatar.setViewportView(pnAvatar);
        ip = IP;
        port = p;
        is = ins;
        os = ous;
        room = ro;
        taChat.setText("");
        taChat.requestFocus();
        sHeight = pnChat.getPreferredSize().height;
        sWidth = pnChat.getPreferredSize().width;
        pnChat.setBackground(Color.WHITE);
        setResizable(false);
        String url = System.getProperty("user.dir", null);
        String urlIcon;
        urlIcon = url + "\\icon\\89.png";
        btFile.setIcon(new javax.swing.ImageIcon(urlIcon));
        btFile.setBackground(new Color(255, 204, 255));
        urlIcon = url + "\\icon\\176.png";
        btExit.setIcon(new javax.swing.ImageIcon(urlIcon));
        btExit.setBackground(new Color(255, 204, 255));
        urlIcon = url + "\\icon\\115.png";
        btAdd.setIcon(new javax.swing.ImageIcon(urlIcon));
        btAdd.setBackground(new Color(255, 204, 255));
        getContentPane().setBackground(new Color(153, 235, 255));
        Listen();
        setTitle(username);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    String str = "TXTEXITEMP" + username + "|" + String.valueOf(room);
                    byte[] exit = str.getBytes(Charset.forName("UTF-8"));
                    byte[] size = ByteBuffer.allocate(9).putInt(exit.length).array();
                    os.write(size);
                    os.write(exit);
                    os.flush();
                    t.stop();
                } catch (IOException ex) {
                    Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lRoom = new javax.swing.JList();
        btAdd = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lChat = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        btFile = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        spChat = new javax.swing.JScrollPane();
        pnChat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        spAvatar = new javax.swing.JScrollPane();
        pnAvatar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Danh sách trong phòng");

        jScrollPane2.setViewportView(lRoom);

        btAdd.setText("Thêm");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(lChat);

        jLabel2.setText("Danh sách bạn bè");

        btFile.setText("File");
        btFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileActionPerformed(evt);
            }
        });

        btExit.setText("Thoát");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        spChat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout pnChatLayout = new javax.swing.GroupLayout(pnChat);
        pnChat.setLayout(pnChatLayout);
        pnChatLayout.setHorizontalGroup(
            pnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 735, Short.MAX_VALUE)
        );
        pnChatLayout.setVerticalGroup(
            pnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        spChat.setViewportView(pnChat);

        taChat.setColumns(20);
        taChat.setRows(5);
        taChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taChatKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(taChat);

        spAvatar.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spAvatar.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout pnAvatarLayout = new javax.swing.GroupLayout(pnAvatar);
        pnAvatar.setLayout(pnAvatarLayout);
        pnAvatarLayout.setHorizontalGroup(
            pnAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
        );
        pnAvatarLayout.setVerticalGroup(
            pnAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );

        spAvatar.setViewportView(pnAvatar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btExit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spChat, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAdd, jLabel2, jScrollPane3});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btExit, jLabel1, jScrollPane2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spChat, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(btFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(spAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class ReadData implements Runnable {

        @Override
        public void run() {
            try {
                byte[] size = new byte[9];
                while (is.read(size) != 0) {
                    String userList = "", userRoom = "";
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
                    System.out.println(format + " " + cmd + " " + extra);
                    switch (format) {
                        case "TXT":
                            String data = new String(mybytearray, 10, mybytearray.length - 10, "UTF-8");
                            System.out.println(data);
                            String[] dt = data.split("[|]");
                            if (cmd.equals("LIS")) {
                                if (extra.equals("USER")) {
                                    DefaultListModel<String> model = new DefaultListModel<>();
                                    for (int i = 0; i < dt.length - 1; i += 2) {
                                        if (dt[i + 1].equals("1")) {
                                            dt[i] = String.format("<html><font color=\"red\">" + dt[i] + "</font></html>", dt[i]);
                                            model.addElement(dt[i]);
                                        } else if (dt[i + 1].equals("0")) {
                                            dt[i] = String.format("<html><font color=\"black\">" + dt[i] + "</font></html>", dt[i]);
                                            model.addElement(dt[i]);

                                        }
                                    }
                                    model = arrangement(model);
                                    lChat.setModel(model);
                                } else if (extra.equals("ROOM")) {
                                    DefaultListModel<String> model = new DefaultListModel<>();
                                    userC.removeAll(userC);
                                    for (int i = 0; i < dt.length - 1; i += 2) {
                                        userC.add(dt[i]);
                                        if (dt[i + 1].equals("1")) {
                                            dt[i] = String.format("<html><font color=\"red\">" + dt[i] + "</font></html>", dt[i]);
                                            model.addElement(dt[i]);
                                        } else if (dt[i + 1].equals("0")) {
                                            dt[i] = String.format("<html><font color=\"black\">" + dt[i] + "</font></html>", dt[i]);
                                            model.addElement(dt[i]);

                                        }
                                    }
                                    model = arrangement(model);
                                    lRoom.setModel(model);
                                }

                            } else if (cmd.equals("LIS") == false) {
                                String[] imess = dt[0].split("[:]");
                                imess[0] = imess[0].substring(0, imess[0].length() - 1);
                               int par = (int) Math.ceil((double) dt[0].length() / 65);
                                    dt[0]=Correct(dt[0]);
                                dt[0] = String.format("<html><font color=\"blue\">" + dt[0] + "</font></html>", dt[0]);
                                dt[0] = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 460, dt[0]);
                                dt[0] = Icon.getIcon(dt[0]);
                                setMessage(dt[0], par);
                            }
                            break;
                        case "OFF":
                            int byteStart = 0;
                            while (byteStart < mybytearray.length) {
                                byteStart += 3;
                                cmd = new String(mybytearray, byteStart, 3, "UTF-8");
                                byteStart += 3;
                                if (cmd.equals("TXT")) {
                                    int i = 0;
                                    for (int j = byteStart; j < byteStart + 5; j++) {
                                        size[i++] = mybytearray[j];
                                    }
                                    byteStart += 5;
                                    int ex = ByteBuffer.wrap(size).asIntBuffer().get();
                                    String mess = new String(mybytearray, byteStart, ex, "UTF-8");
                                    byteStart += ex;
                                    //Tach dong cho phu hop
                                    String[] imess = mess.split("[:]");
                                    imess[0] = imess[0].substring(0, imess[0].length() - 1);
                                   int par = (int) Math.ceil((double) mess.length() / 65);
                                    mess=Correct(mess);
                                    if (imess[0].equals(username)) {
                                        mess = String.format("<html><font color=\"red\">" + mess + "</font></html>", mess);
                                    } else {
                                        mess = String.format("<html><font color=\"blue\">" + mess + "</font></html>", mess);
                                    }
                                    mess = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 460, mess);
                                    //setText
                                    mess = Icon.getIcon(mess);
                                    setMessage(mess, par);

                                } else if (cmd.equals("IMG")) {
                                    int i = 0;
                                    for (int j = byteStart; j < byteStart + 5; j++) {
                                        size[i++] = mybytearray[j];
                                    }
                                    int ex = ByteBuffer.wrap(size).asIntBuffer().get();
                                    byteStart += 5;
                                    i = 0;
                                    for (int j = byteStart; j < byteStart + 4; j++) {
                                        size[i++] = mybytearray[j];
                                    }

                                    int ext = ByteBuffer.wrap(size).asIntBuffer().get();
                                    byteStart += 4;
                                    String user = new String(mybytearray, byteStart, ext, "UTF-8");
                                    byteStart += ext;
                                    if (user.equals(username)) {
                                        user = user + " : ";
                                        user = String.format("<html><font color=\"red\">" + user + "</font></html>", user);
                                    } else {
                                        user = user + " : ";
                                        user = String.format("<html><font color=\"blue\">" + user + "</font></html>", user);
                                    }
                                    user = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 460, user);
                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mybytearray, byteStart, ex);
                                    byteStart += ex;
                                    bufferedImage = ImageIO.read(byteArrayInputStream);
                                    addPhoto(user, username, bufferedImage);
                                }
                            }
                            break;
                        case "IMG":
                            int j = 0;
                            for (int i = 10; i < 14; i++) {
                                size[j++] = mybytearray[i];
                            }
                            int ex = ByteBuffer.wrap(size).asIntBuffer().get();
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mybytearray, 10 + 4 + ex, mybytearray.length - 10 - 4 - ex);
                            bufferedImage = ImageIO.read(byteArrayInputStream);
                            addPhoto(username, String.valueOf(room), bufferedImage);
                            break;

                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Listen() {
        Thread readData = new Thread(new ReadData());
        t = readData;
        readData.start();
    }

    public DefaultListModel<String> arrangement(DefaultListModel<String> model) {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = i + 1; j < model.getSize(); j++) {
                if (correctList(model.get(i)).compareTo(correctList(model.get(j))) > 0) {
                    String tem;
                    tem = model.get(i);
                    model.setElementAt(model.get(j), i);
                    model.setElementAt(tem, j);

                }
            }
        }
        return model;
    }

      private String Correct(String cmt) {
        int limit=65;
        int start = 0;
        int end = limit;
        int par = (int) Math.ceil((double) cmt.length() / limit);
        String[] strCut = new String[100];
        char[] str = new char[cmt.length()];
        str = cmt.toCharArray();
        int k = 0;
        for (k = 0; k < par; k++) {
            if (end < cmt.length()) {
                while (str[end] != ' ') {
                    end--;
                }
                strCut[k] = cmt.substring(start, end);
            } else if ((end - limit) < cmt.length()) {
                strCut[k] = cmt.substring(end - limit, cmt.length());
            }
            start = end;
            end += limit;

        }
        cmt = "";
        for (int j = 0; j < k; j++) {
            cmt += strCut[j] + "\n";
        }
        return cmt;

    }
    public void setMessage(String chat, int width) {
        JTextPane lbMsg = new JTextPane();
        lbMsg.setContentType("text/html");
        lbMsg.setEditable(false);
        lbMsg.setBackground(null);
        lbMsg.setBorder(null);
        lbMsg.setText(chat + "\n");
        lbMsg.setLocation(10, height + 2);
        lbMsg.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbMsg.setPreferredSize(new Dimension(pnChat.getWidth() - 5, lbMsg.getPreferredSize().height + width * 2));
        lbMsg.setSize(lbMsg.getPreferredSize());
        pnChat.add(lbMsg);
        height += lbMsg.getPreferredSize().height;
        if (Scrolled || height > sHeight) {
            pnChat.setPreferredSize(new Dimension(pnChat.getPreferredSize().width,
                    height));
            Scrolled = true;
        }
        spChat.setViewportView(pnChat);
        JScrollBar vertical = spChat.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private void addPhoto(String userSend, String userReceive, final BufferedImage bufferedImage) {
        JTextPane lbMsg = new JTextPane();
        lbMsg.setContentType("text/html");
        lbMsg.setEditable(false);
        lbMsg.setBackground(null);
        lbMsg.setBorder(null);
        String strSend = userSend + " :";
        if (userSend.equals(username)) {
            strSend = String.format("<html><font color=\"red\">" + strSend + "</font></html>", strSend);
        } else {
            strSend = String.format("<html><font color=\"blue\">" + strSend + "</font></html>", strSend);
        }
        lbMsg.setText(strSend);
        lbMsg.setLocation(10, height + 2);
        lbMsg.setFont(new java.awt.Font("Tahoma", 0, 12));
        lbMsg.setPreferredSize(new Dimension(pnChat.getWidth() - 5, lbMsg.getPreferredSize().height + 2));
        lbMsg.setSize(lbMsg.getPreferredSize());
        pnChat.setPreferredSize(new Dimension(pnChat.getPreferredSize().width,
                height));
        pnChat.add(lbMsg);

        height += lbMsg.getPreferredSize().height;
        if (Scrolled || height > sHeight) {
            pnChat.setPreferredSize(new Dimension(pnChat.getPreferredSize().width,
                    height));
            Scrolled = true;
        }
        jPanel1 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), null);
                repaint();
            }
        };
        jPanel1.setPreferredSize(new Dimension(picWidth, picHeight));
        jPanel1.setSize(picWidth, picHeight);
        jPanel1.setLocation(10, height + 2);
        pnChat.add(jPanel1);
        height += jPanel1.getPreferredSize().height;

        JTextPane lb = new JTextPane();
        lb.setLocation(10, height + 2);
        pnChat.add(lb);
        height += lb.getPreferredSize().height;

        JButton jButton = new JButton("Save");
        jButton.setSize(70, 40);
        jButton.setLocation(10 + jPanel1.getWidth() + 10, (height + 2) - jPanel1.getHeight() / 2);
        jButton.setBackground(new Color(255, 204, 255));
        pnChat.add(jButton);
        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    FileChooser fc = new FileChooser("Open file", FileChooser.FILE_SAVE, "png, jpg, jpeg, gif", "Select a photo (*png, *jpg)");
                    if (fc.isSuccess()) {
                        String fileName = fc.getFileName();
                        File file = new File(fileName + ".jpg");
                        ImageIO.write(bufferedImage, "jpg", file);
                        JOptionPane.showMessageDialog(null, "Đã lưu!");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        if (Scrolled || height > sHeight) {
            pnChat.setPreferredSize(new Dimension(pnChat.getPreferredSize().width,
                    height));
            Scrolled = true;
        }
        spChat.setViewportView(pnChat);

        //Scroll down
        JScrollBar vertical = spChat.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public String correctList(String user) {
        user = user.replaceAll("<html><font color=\"red\">", "");
        user = user.replaceAll("<html><font color=\"black\">", "");
        user = user.replaceAll("</font></html>", "");
        return user;
    }

    public boolean isConnect(String name) {
        for (int i = 0; i < userC.size(); i++) {
            if (userC.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String CorrectChat(String data[]) {
        String chat = "";
        for (int j = 1; j < data.length; j++) {
            if (j != data.length - 1) {
                chat += data[j] + "|";
            } else {
                chat += data[j];
            }
        }
        return chat;
    }

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        try {
            String select = (String) lChat.getSelectedValue();
            select = correctList(select);
            if (isConnect(select) == false) {
                String str = "TXTADDnull" + String.valueOf(room) + "|" + username + "|" + select;
                byte[] sendlist = str.getBytes(Charset.forName("UTF-8"));
                byte[] size = ByteBuffer.allocate(9).putInt(sendlist.length).array();
                os.write(size);
                os.write(sendlist);
                os.flush();
            } else {
                JOptionPane.showMessageDialog(null, select + " đã có trong nhóm!");
                taChat.requestFocus();
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAddActionPerformed

    private void btFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileActionPerformed
        FileChooser fc = new FileChooser("Open file", FileChooser.FILE_OPEN, "png, jpg, jpeg, gif", "Select a photo (*png, *jpg)");
        if (fc.isSuccess()) {
            try {
                BufferedInputStream bis = null;
                ImageProcess ip = new ImageProcess();
                File myFile = fc.getFile();
                if (myFile.length() <= 3000000) {
                    BufferedImage bufferedImage = ip.getIconFromFile(myFile);
                    if (bufferedImage != null) {
                        addPhoto(username, String.valueOf(room), bufferedImage);
                    }
                    String STR = "IMGROO";
                    byte[] str = STR.getBytes(Charset.forName("UTF-8"));
                    byte[] num = ByteBuffer.allocate(4).putInt(room).array();
                    byte[] sizeName = ByteBuffer.allocate(4).putInt(username.length()).array();
                    byte[] name = username.getBytes(Charset.forName("UTF-8"));
                    byte[] byteImage = new byte[(int) myFile.length() + str.length + num.length + sizeName.length + name.length];
                    int i = 0;
                    for (i = 0; i < str.length; i++) {
                        byteImage[i] = str[i];
                    }
                    for (int j = 0; j < num.length; j++) {
                        byteImage[i++] = num[j];
                    }
                    for (int j = 0; j < sizeName.length; j++) {
                        byteImage[i++] = sizeName[j];
                    }
                    for (int j = 0; j < name.length; j++) {
                        byteImage[i++] = name[j];
                    }
                    bis = new BufferedInputStream(new FileInputStream(myFile));
                    bis.read(byteImage, str.length + num.length + sizeName.length + name.length, byteImage.length - str.length - num.length - sizeName.length - name.length);
                    byte[] size = ByteBuffer.allocate(9).putInt(byteImage.length).array();
                    os.write(size);
                    os.write(byteImage);
                    os.flush();
                } else {
                    JOptionPane.showMessageDialog(null, "Kích thước tối đa là 3MB");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btFileActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        try {
            String str = "TXTEXIROOM" + username + "|" + String.valueOf(room);
            byte[] exit = str.getBytes(Charset.forName("UTF-8"));
            byte[] size = ByteBuffer.allocate(9).putInt(exit.length).array();
            os.write(size);
            os.write(exit);
            os.flush();
            Thread.sleep(50);
            dispose();
        } catch (InterruptedException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrameChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btExitActionPerformed

    private void taChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taChatKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (taChat.getText().equals("") == false) {
                try {
                    String str = "TXTCHAROOM" + String.valueOf(room) + "|" + username + "|" + taChat.getText();
                    byte[] chat = str.getBytes(Charset.forName("UTF-8"));
                    byte[] size = ByteBuffer.allocate(9).putInt(chat.length).array();
                    os.write(size);
                    os.write(chat);
                    os.flush();
                    String ch = username + " : " + taChat.getText();
                    int par = (int) Math.ceil((double) ch.length() / 65);
                    ch = String.format("<html><font color=\"red\">" + ch + "</font></html>", ch);
                    ch = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 460, ch);
                    ch = Icon.getIcon(ch);
                    setMessage(ch, par);
                    taChat.setText("");
                    taChat.requestFocus();
                    return;
                } catch (IOException ex) {
                    Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (taChat.getText().equals("") == true) {
                taChat.requestFocus();
            }
        }
    }//GEN-LAST:event_taChatKeyPressed

    /**
     * @param args the command line arguments
     */
    private javax.swing.JPanel jPanel1;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList lChat;
    private javax.swing.JList lRoom;
    private javax.swing.JPanel pnAvatar;
    private javax.swing.JPanel pnChat;
    private javax.swing.JScrollPane spAvatar;
    private javax.swing.JScrollPane spChat;
    private javax.swing.JTextArea taChat;
    // End of variables declaration//GEN-END:variables
}