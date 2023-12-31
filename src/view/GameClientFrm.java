package view;
import controller.Client;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;
import model.User;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GameClientFrm extends javax.swing.JFrame{

    private User competitor;
    private JButton[][] button;
    private int[][] competitorMatrix;
    private int[][] matrix;
    private int[][] userMatrix;
    private final int size = 15;
    private Timer timer;
    private Integer second, minute;
    private int numberOfMatch;
    private String normalItem[];
    private String winItem[];
    private String iconItem[];
    private String preItem[];
    private JButton preButton;
    private int userWin;
    private int competitorWin;
    private Thread sendThread;
    private boolean isSending;
    private Thread listenThread;
    private boolean isListening;
    private String competitorIP;
    
    public GameClientFrm(User competitor, int room_ID, int isStart, String competitorIP) {
        initComponents();
        numberOfMatch = isStart;
        this.competitor = competitor;
        this.competitorIP = competitorIP;
        isSending = false;
        isListening = false;
        userWin = 0;
        competitorWin = 0;
        this.setTitle("Caro Game");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);
        //Set layout dạng lưới cho panel chứa button
        jPanel1.setLayout(new GridLayout(size, size));
        //Setup play button
        button = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border2.jpg"));
                jPanel1.add(button[i][j]);
            }
        }
        //SetUp play Matrix
        competitorMatrix = new int[size][size];
        matrix = new int[size][size];
        userMatrix = new int[size][size];
        jLabel1.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel6.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel18.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel18.setAlignmentX(JLabel.CENTER);
        jButton1.setBackground(Color.white);
        jButton1.setIcon(new ImageIcon("assets/image/send2.png"));
       
        
        jLabel12.setText(Client.user.getNickname());
        jLabel13.setText(Integer.toString(Client.user.getNumberOfGame()));
        jLabel14.setText(Integer.toString(Client.user.getNumberOfwin()));
        jLabel18.setText("Phòng: " + room_ID);
        jLabel15.setText(competitor.getNickname());
        jLabel16.setText(Integer.toString(competitor.getNumberOfGame()));
        jLabel17.setText(Integer.toString(competitor.getNumberOfwin()));
        jLabel3.setVisible(false);
        jLabel5.setVisible(false);
        yourTurnJLabel3.setVisible(false);
        compretitorTurnJLabel.setVisible(false);
        timerjLabel19.setVisible(false);
        jTextArea1.setEditable(false);
        jLabel20.setText("Tỉ số: 0-0");       
        second = 60;
        minute = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    timerjLabel19.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 60;
                    minute = 0;
                    try {
                        Client.openView(Client.View.GAMECLIENT, "Bạn đã thua do quá thời gian", "Đang thiết lập ván chơi mới");
                        increaseWinMatchToCompetitor();
                        Client.socketHandle.write("lose,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }                   
                } else {
                    timerjLabel19.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }
            }
        });
       
        //Setup icon
        normalItem = new String[2];
        normalItem[1] = "assets/image/o2.jpg";
        normalItem[0] = "assets/image/x2.jpg";
        winItem = new String[2];
        winItem[1] = "assets/image/owin.jpg";
        winItem[0] = "assets/image/xwin.jpg";
        iconItem = new String[2];
        iconItem[1] = "assets/image/o3.jpg";
        iconItem[0] = "assets/image/x3.jpg";
        preItem = new String[2];
        preItem[1] = "assets/image/o2_pre.jpg";
        preItem[0] = "assets/image/x2_pre.jpg";
        setupButton();

        setEnableButton(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGame();
            }
        });       
    }
    public void exitGame() {
        try {
            timer.stop();            
            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }    
    public void stopAllThread(){
        timer.stop();       
    }
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        yourTurnJLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        timerjLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        compretitorTurnJLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();              

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);

        jLabel2.setText("Số ván thắng");

        yourTurnJLabel3.setForeground(new java.awt.Color(255, 0, 0));
        yourTurnJLabel3.setText("Đến lượt bạn");

        jLabel8.setText("Nickname");
        jLabel4.setText("Số ván chơi");
        jLabel9.setText("Số ván thắng");
        jLabel10.setText("Nickname");
        jLabel11.setText("Số ván chơi");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel12.setText("{nickname}");
        jLabel13.setText("{sovanchoi}");
        jLabel14.setText("{sovanthang}");
        jLabel15.setText("{nickname}");
        jLabel16.setText("{sovanchoi}");
        jLabel17.setText("{sovanthang}");

        timerjLabel19.setForeground(new java.awt.Color(255, 0, 0));
        timerjLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerjLabel19.setText("Thời gian:00:20");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Đối thủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("{Tên Phòng}");
  javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
  jPanel4Layout.setHorizontalGroup(
  	jPanel4Layout.createParallelGroup(Alignment.LEADING)
  		.addGroup(jPanel4Layout.createSequentialGroup()
  			.addContainerGap()
  			.addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
  			.addContainerGap(153, Short.MAX_VALUE))
  );
  jPanel4Layout.setVerticalGroup(
  	jPanel4Layout.createParallelGroup(Alignment.LEADING)
  		.addComponent(jLabel18, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
  );
        jPanel4.setLayout(jPanel4Layout);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Tỉ số:  0-0");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        compretitorTurnJLabel.setForeground(new java.awt.Color(0, 0, 204));
        compretitorTurnJLabel.setText("Đến lượt đối thủ");

        jLabel3.setText("x/o");

        jLabel5.setText("x/o");

        jMenu1.setText("Menu");
        jMenu1.setToolTipText("");

       
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem2.setText("Thoát");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
        							.addGap(26)
        							.addComponent(jLabel14, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
        							.addGap(39)
        							.addComponent(jLabel15, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel9, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        							.addGap(27)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel17, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))))
        					.addGap(145))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(yourTurnJLabel3, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
        					.addGap(28)
        					.addComponent(timerjLabel19, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        					.addGap(28)
        					.addComponent(compretitorTurnJLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(29)
        							.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        							.addGap(39)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(jLabel20, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
        									.addGap(41)
        									.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))))
        						.addGroup(layout.createSequentialGroup()
        							.addContainerGap()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
        							.addGap(26)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel13))))
        					.addGap(47))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jPanel3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        						.addComponent(jPanel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        							.addContainerGap()
        							.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
        						.addComponent(jPanel2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
        					.addGap(24)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel8)
        						.addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel4)
        						.addComponent(jLabel13))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel2)
        						.addComponent(jLabel14))
        					.addGap(18)
        					.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel10)
        						.addComponent(jLabel15))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel11)
        						.addComponent(jLabel16))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel9)
        						.addComponent(jLabel17))
        					.addGap(44)
        					.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(20)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel7)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(5)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel20)
        								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(timerjLabel19)
        						.addComponent(compretitorTurnJLabel)
        						.addComponent(yourTurnJLabel3))
        					.addGap(6)
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
        					.addGap(59))
        				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);
        pack();
    }

  
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        exitGame();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            jTextArea1.setText("");
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (jTextField1.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String temp = jTextArea1.getText();
            temp += "Tôi: " + jTextField1.getText() + "\n";
            jTextArea1.setText(temp);
            Client.socketHandle.write("chat," + jTextField1.getText());
            jTextField1.setText("");
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
  
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            try {
                if (jTextField1.getText().isEmpty()) {
                    return;
                }
                String temp = jTextArea1.getText();
                temp += "Tôi: " + jTextField1.getText() + "\n";
                jTextArea1.setText(temp);
                Client.socketHandle.write("chat," + jTextField1.getText());
                jTextField1.setText("");
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }
    public void showMessage(String message){
        JOptionPane.showMessageDialog(rootPane, message);
    }
    
    public void stopTimer(){
        timer.stop();
    }
    int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }

    void setupButton() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int a = i, b = j;

                button[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                            button[a][b].setEnabled(false);
                          
                            second = 60;
                            minute = 0;
                            matrix[a][b] = 1;
                            userMatrix[a][b] = 1;
                            button[a][b].setEnabled(false);
                            try {
                                if (checkRowWin() == 1 || checkColumnWin() == 1 || checkRightCrossWin() == 1 || checkLeftCrossWin() == 1) {
                                    //Xử lý khi người chơi này thắng
                                    setEnableButton(false);
                                    increaseWinMatchToUser();
                                    Client.openView(Client.View.GAMENOTICE,"Bạn đã thắng","Đang thiết lập ván chơi mới");
                                    Client.socketHandle.write("win,"+a+","+b);
                                }
                                else{
                                    Client.socketHandle.write("caro," + a + "," + b);
                                    displayCompetitorTurn();
                                    
                                }
                                setEnableButton(false);
                                timer.stop();
                            } catch (Exception ie) {
                                ie.printStackTrace();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                        }
                    }
                });
                button[a][b].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if(button[a][b].isEnabled()) {
                            button[a][b].setBackground(Color.GREEN);
                            button[a][b].setIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                        }
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if(button[a][b].isEnabled()){
                            button[a][b].setBackground(null);
                            button[a][b].setIcon(new ImageIcon("assets/image/blank.jpg"));
                        }
                    }
                });
            }
        }
    }
    public void displayCompetitorTurn() {
        timerjLabel19.setVisible(false);
        compretitorTurnJLabel.setVisible(true);
        jLabel5.setVisible(true);
        yourTurnJLabel3.setVisible(false);
      
        jLabel3.setVisible(false);
    }
    public void displayUserTurn(){
        timerjLabel19.setVisible(false);
        compretitorTurnJLabel.setVisible(false);
        jLabel5.setVisible(false);
        yourTurnJLabel3.setVisible(true);     
        jLabel3.setVisible(true);
    }
    
    public void startTimer(){
        timerjLabel19.setVisible(true);
        second = 60;
        minute = 0;
        timer.start();
    }
    public void addMessage(String message){
        String temp = jTextArea1.getText();
        temp += competitor.getNickname() + ": " + message+"\n";
        jTextArea1.setText(temp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
    
    public void addCompetitorMove(String x, String y){
        displayUserTurn();
        startTimer();
        setEnableButton(true);
        caro(x, y);
    }
    
    public void setLose(String xx, String yy){
        caro(xx, yy);
    }
    
    public void increaseWinMatchToUser(){
        Client.user.setNumberOfwin(Client.user.getNumberOfwin()+1);
        jLabel14.setText(""+Client.user.getNumberOfwin());
        userWin++;
        jLabel20.setText("Tỉ số: "+userWin+"-"+competitorWin);
        String tmp = jTextArea1.getText();
        tmp += "--Bạn đã thắng, tỉ số hiện tại là "+userWin+"-"+competitorWin+"--\n";
        jTextArea1.setText(tmp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
    public void increaseWinMatchToCompetitor(){
        competitor.setNumberOfwin(competitor.getNumberOfwin()+1);
        jLabel17.setText(""+competitor.getNumberOfwin());
        competitorWin++;
        jLabel20.setText("Tỉ số: "+userWin+"-"+competitorWin);
        String tmp = jTextArea1.getText();
        tmp += "--Bạn đã thua, tỉ số hiện tại là "+userWin+"-"+competitorWin+"--\n";
        jTextArea1.setText(tmp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
    public void displayDrawGame(){
        String tmp = jTextArea1.getText();
        tmp += "--Ván chơi hòa--\n";
        jTextArea1.setText(tmp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }    
    public void newgame() {        
        if (numberOfMatch % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
            startTimer();
            displayUserTurn();
            timerjLabel19.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
            displayCompetitorTurn();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setIcon(new ImageIcon("assets/image/blank.jpg"));
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
                userMatrix[i][j] = 0;
            }
        }
        setEnableButton(true);
        if(numberOfMatch % 2 != 0){
            blockgame();
        }
        
        jLabel3.setIcon(new ImageIcon(iconItem[numberOfMatch % 2]));
        jLabel5.setIcon(new ImageIcon(iconItem[not(numberOfMatch % 2)]));
        preButton = null;
        numberOfMatch++;
    }
    public void updateNumberOfGame(){
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
        jLabel16.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.user.setNumberOfGame(Client.user.getNumberOfGame() + 1);
        jLabel13.setText(Integer.toString(Client.user.getNumberOfGame()));
    }
    
    public void blockgame() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
            }
        }
        timer.stop();
        setEnableButton(false);
    }

    public void setEnableButton(boolean b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    button[i][j].setEnabled(b);
                }
            }
        }
    }
    public int checkRow() {
        int win = 0, hang = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (competitorMatrix[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                button[i][j].setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }
    public int checkColumn() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (check) {
                    if (competitorMatrix[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkRightCross() {
        int win = 0, cheop = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (n - j>=0 && competitorMatrix[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkLeftCross() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = size-1; j >= 0; j--) {
                if (check) {
                    if (n - j - 2 * cheot>=0 && competitorMatrix[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public int checkRowWin() {
        int win = 0, hang = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkColumnWin() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkRightCrossWin() {
        int win = 0, cheop = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (n>=j && userMatrix[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkLeftCrossWin() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = size-1; j >= 0; j--) {
                if (check) {
                    if (n - j - 2 * cheot>=0 && userMatrix[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public void caro(String x, String y) {
        int xx, yy;
        xx = Integer.parseInt(x);
        yy = Integer.parseInt(y);
        // danh dau vi tri danh
        competitorMatrix[xx][yy] = 1;
        matrix[xx][yy] = 1;
        button[xx][yy].setEnabled(false);
      
        if(preButton!=null){
            preButton.setDisabledIcon(new ImageIcon(normalItem[numberOfMatch % 2]));
        }
        preButton = button[xx][yy];
        button[xx][yy].setDisabledIcon(new ImageIcon(preItem[numberOfMatch % 2]));
        if(checkRow()==1||checkColumn()==1||checkLeftCross()==1||checkRightCross()==1){
            timer.stop();
            setEnableButton(false);
            increaseWinMatchToCompetitor();
            Client.openView(Client.View.GAMENOTICE,"Bạn đã thua","Đang thiết lập ván chơi mới");
        }
    }
    private javax.swing.JLabel compretitorTurnJLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel timerjLabel19;
    private javax.swing.JLabel yourTurnJLabel3;
  
}
