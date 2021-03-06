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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import static javax.swing.Spring.height;

/**
 *
 * @author Admin
 */
public class FrChat extends javax.swing.JFrame {

    /**
     * Creates new form FrameChat
     */
    private String username;
    private String name;
    Socket socket;
    private InputStream is;
    private OutputStream os;
    int room;
    String ip;
    int port;
    Thread t;
    int height;
    private int picWidth = 480;
    private int picHeight = 270;
    private int sHeight;
    private int sWidth;
    private boolean Scrolled = false;
    private BufferedImage bufferedImage;
    setIcon Icon = new setIcon();

    public FrChat(final String username, String name, String IP, int p, InputStream ins, OutputStream ous, int ro, BufferedImage buff) {
        initComponents();
        this.username = username;
        this.name = name;
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
        setResizable(false);
        String url = System.getProperty("user.dir", null);
        String urlIcon;
        urlIcon = url + "\\icon\\89.png";
        btFile.setIcon(new javax.swing.ImageIcon(urlIcon));
        btFile.setBackground(new Color(255, 204, 255));
        getContentPane().setBackground(new Color(153, 235, 255));
        Listen();
        setTitle(name);
        sHeight = pnChat.getPreferredSize().height;
        sWidth = pnChat.getPreferredSize().width;
        pnChat.setBackground(Color.WHITE);
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
                    Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        btFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        spChat = new javax.swing.JScrollPane();
        pnChat = new javax.swing.JPanel();
        spAvatar = new javax.swing.JScrollPane();
        pnAvatar = new javax.swing.JPanel();

        jScrollPane2.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btFile.setText("File");
        btFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileActionPerformed(evt);
            }
        });

        taChat.setColumns(20);
        taChat.setLineWrap(true);
        taChat.setRows(5);
        taChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taChatKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(taChat);

        spChat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout pnChatLayout = new javax.swing.GroupLayout(pnChat);
        pnChat.setLayout(pnChatLayout);
        pnChatLayout.setHorizontalGroup(
            pnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 813, Short.MAX_VALUE)
        );
        pnChatLayout.setVerticalGroup(
            pnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );

        spChat.setViewportView(pnChat);

        spAvatar.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spAvatar.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout pnAvatarLayout = new javax.swing.GroupLayout(pnAvatar);
        pnAvatar.setLayout(pnAvatarLayout);
        pnAvatarLayout.setHorizontalGroup(
            pnAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );
        pnAvatarLayout.setVerticalGroup(
            pnAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        spAvatar.setViewportView(pnAvatar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(578, 578, 578)
                        .addComponent(btFile, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spAvatar))
            .addComponent(spChat, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(spChat, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btFile, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class ReadData implements Runnable {

        @Override
        public void run() {
            try {
                byte[] size = new byte[9];
                while (is.read(size) != 0) {
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
                            String[] dt = data.split("[|]");
                            if (cmd.equals("LIS") == false) {
                                String[] imess = dt[0].split("[:]");
                                imess[0] = imess[0].substring(0, imess[0].length() - 1);
                                int par = (int) Math.ceil((double) dt[0].length() / 65);
                                dt[0]=dt[0].substring(imess[0].length() + 1, dt[0].length());
                                dt[0]=Correct(dt[0]);
                                dt[0] = String.format("<html><font color=\"blue\">" + "<b>"+imess[0]+"</b>"+" "+dt[0] + "</font></html>", dt[0]);
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
                                        mess = String.format("<html><b><font color=\"red\">" + mess + "</font></b></html>", mess);
                                    } else {
                                        mess = String.format("<html><b><font color=\"blue\">" + mess + "</font></b></html>", mess);
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
                                        user = String.format("<html><b><font color=\"red\">" + user + "</font></b></html>", user);
                                    } else {
                                        user = user + " : ";
                                        user = String.format("<html><b><font color=\"blue\">" + user + "</font></b></html>", user);
                                    }
                                    user = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 460, user);
                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mybytearray, byteStart, ex);
                                    byteStart += ex;
                                    BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                                    addPhoto(name, username, bufferedImage);
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
                            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                            addPhoto(name, username, bufferedImage);
                            break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Listen() {
        Thread readData = new Thread(new ReadData());
        t = readData;
        readData.start();
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
        lbMsg.setEditable(true);
        lbMsg.setBackground(null);
        lbMsg.setBorder(null);
        lbMsg.setText(chat);
        lbMsg.setLocation(10, height + 2);

        lbMsg.setFont(new java.awt.Font("Tahomo", 0, 14));
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
            strSend = String.format("<html><b><font color=\"red\">" + strSend + "</font></b></html>", strSend);
        } else {
            strSend = String.format("<html><b><font color=\"blue\">" + strSend + "</font></b></html>", strSend);
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


    private void btFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileActionPerformed
        FileChooser fc = new FileChooser("Open file", FileChooser.FILE_OPEN, "png, jpg, jpeg, gif", "Select a photo (*png, *jpg)");
        if (fc.isSuccess()) {
            BufferedInputStream bis = null;
            try {
                ImageProcess ip = new ImageProcess();
                File myFile = fc.getFile();
                if (myFile.length() <= 3000000) {
                    BufferedImage bufferedImage = ip.getIconFromFile(myFile);
                    if (bufferedImage != null) {
                        addPhoto(username, name, bufferedImage);
                    }
                    String STR = "IMGSIN";
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
                Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FrChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btFileActionPerformed

    private void taChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taChatKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (taChat.getText().equals("") == false) {
                try {
                    String str = "TXTCHASING" + String.valueOf(room) + "|" + username + "|" + taChat.getText();
                    byte[] chat = str.getBytes(Charset.forName("UTF-8"));
                    byte[] size = ByteBuffer.allocate(9).putInt(chat.length).array();
                    os.write(size);
                    os.write(chat);
                    os.flush();
                    String ch = username + " : " + taChat.getText();
                    int par = (int) Math.ceil((double) ch.length() / 65);
                    ch = String.format("<html><font color=\"red\"><b>" +  username +"</b>"+ " : " + taChat.getText() + "</font></html>", ch);
                    ch = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 460, ch);
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
    private javax.swing.JButton btFile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel pnAvatar;
    private javax.swing.JPanel pnChat;
    private javax.swing.JScrollPane spAvatar;
    private javax.swing.JScrollPane spChat;
    private javax.swing.JTextArea taChat;
    // End of variables declaration//GEN-END:variables
}
