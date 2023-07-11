package view;


import controller.Client;
import model.User;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class HomePageFrm extends javax.swing.JFrame {
	  private User user;
    public HomePageFrm() {
        initComponents();
        jTextArea1.setEditable(false);
    }
    private void initComponents() {
    	
    	jTextArea1 = new javax.swing.JTextArea();

         setTitle("Caro Game");
     	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	 setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
     	setBounds(100, 100, 809, 564);
 		setResizable(false);
 		setLocationRelativeTo(null);
 		setVisible(true);
       
 		getContentPane = new JPanel();
		getContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(getContentPane);
        getContentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 302, 527);
        panel.setBackground(new Color(102, 102, 102));
        getContentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("NickName");
        lblNewLabel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(25, 222, 93, 24);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Số ván đã chơi");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(25, 301, 108, 24);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Số ván thắng");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setBounds(25, 263, 108, 24);
        panel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Điểm");
        lblNewLabel_3.setForeground(new Color(255, 255, 255));
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(25, 344, 70, 24);
        panel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Thứ hạng");
        lblNewLabel_4.setForeground(new Color(255, 255, 255));
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_4.setBounds(25, 391, 93, 24);
        panel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Tỉ lệ thắng");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_5.setBounds(25, 436, 108, 24);
        panel.add(lblNewLabel_5);
        
        JLabel lblNewLabel_6 = new JLabel("Số ván hòa");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_6.setForeground(new Color(255, 255, 255));
        lblNewLabel_6.setBounds(25, 480, 93, 24);
        panel.add(lblNewLabel_6);
        
        JLabel lblNewLabel_7 = new JLabel("{day la Nick name}");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_7.setForeground(new Color(255, 255, 255));
        lblNewLabel_7.setBounds(152, 226, 122, 16);
        panel.add(lblNewLabel_7);
        
        JLabel lblNewLabel_8 = new JLabel("{day la so van thang}");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_8.setForeground(new Color(255, 255, 255));
        lblNewLabel_8.setBounds(152, 263, 108, 24);
        panel.add(lblNewLabel_8);
        
        JLabel lblNewLabel_9 = new JLabel("{day la so van da choi}");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_9.setForeground(new Color(255, 255, 255));
        lblNewLabel_9.setBounds(152, 301, 132, 24);
        panel.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("{day la diem}");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_10.setForeground(new Color(255, 255, 255));
        lblNewLabel_10.setBounds(152, 344, 122, 24);
        panel.add(lblNewLabel_10);
        
        JLabel lblNewLabel_11 = new JLabel("{day la thu hang}");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_11.setForeground(new Color(255, 255, 255));
        lblNewLabel_11.setBounds(152, 390, 122, 26);
        panel.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("{day la ti le thang}");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_12.setForeground(new Color(255, 255, 255));
        lblNewLabel_12.setBounds(152, 436, 142, 24);
        panel.add(lblNewLabel_12);
        
        JLabel lblNewLabel_13 = new JLabel("{Đây là số ván hòa}");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_13.setForeground(new Color(255, 255, 255));
        lblNewLabel_13.setBounds(152, 479, 122, 26);
        panel.add(lblNewLabel_13);
        
        JButton btnNewButton = new JButton("Tạo phòng");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(new Color(173, 216, 230));
        btnNewButton.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/299068_add_sign_icon.png")));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		 jButton1ActionPerformed(evt);
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(441, 19, 224, 47);
        getContentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Xem phòng");
        btnNewButton_1.setForeground(new Color(0, 0, 0));
        btnNewButton_1.setBackground(new Color(173, 216, 230));
        btnNewButton_1.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/2303106_eye_opened_public_visible_watch_icon.png")));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		jButton4ActionPerformed(evt);
        	}
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_1.setBounds(441, 84, 224, 53);
        getContentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Tìm phòng");
        btnNewButton_2.setForeground(new Color(0, 0, 0));
        btnNewButton_2.setBackground(new Color(173, 216, 230));
        btnNewButton_2.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/9004732_search_find_magnifier_zoom_icon.png")));
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		  jButton9ActionPerformed(evt);
        	}
        });
        btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_2.setBounds(441, 164, 224, 47);
        getContentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Bảng xếp hạng");
        btnNewButton_3.setForeground(new Color(0, 0, 0));
        btnNewButton_3.setBackground(new Color(173, 216, 230));
        btnNewButton_3.setIcon(new ImageIcon("assets/game/7013432_ranking_business_rank_rate_rating_icon.png"));
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		 jButton3ActionPerformed(evt);
        	}
        });
        btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_3.setBounds(441, 238, 224, 47);
        getContentPane.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("Chơi với máy");
        btnNewButton_4.setForeground(new Color(0, 0, 0));
        btnNewButton_4.setBackground(new Color(173, 216, 230));
        btnNewButton_4.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/2890568_ai_artificial intelligence_automaton_brain_electronics_icon.png")));
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		jButton7ActionPerformed(evt);
        	}
        });
        btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_4.setBounds(441, 309, 224, 47);
        getContentPane.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Hướng dẫn");
        btnNewButton_5.setForeground(new Color(0, 0, 0));
        btnNewButton_5.setBackground(new Color(173, 216, 230));
        btnNewButton_5.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/2191540_book_education_guide_menu_read_icon.png")));
        btnNewButton_5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		btnNewButtonActionPerformed(evt);
        	}
        });
        btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_5.setBounds(441, 382, 224, 47);
        getContentPane.add(btnNewButton_5);
        
        JButton btnNewButton_7 = new JButton("Đăng xuất");
        btnNewButton_7.setForeground(new Color(0, 0, 0));
        btnNewButton_7.setBackground(new Color(173, 216, 230));
        btnNewButton_7.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/1564506_close_exit_logout_power_icon.png")));
        btnNewButton_7.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		 jButton2ActionPerformed(evt);
        	}
        });
        btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton_7.setBounds(441, 451, 224, 47);
        getContentPane.add(btnNewButton_7);
        
        JLabel lblNewLabel_14 = new JLabel("");
        lblNewLabel_14.setIcon(new ImageIcon("assets/istockphoto-1292919739-612x612.jpg"));
        lblNewLabel_14.setBounds(302, 0, 493, 527);
        getContentPane.add(lblNewLabel_14);
        lblNewLabel_7.setText(Client.user.getNickname());
        lblNewLabel_8.setText(Integer.toString(Client.user.getNumberOfwin()));
        lblNewLabel_9.setText(Integer.toString(Client.user.getNumberOfGame()));
        lblNewLabel_13.setText(""+Client.user.getNumberOfDraw());
        lblNewLabel_10.setText(""+(Client.user.getNumberOfwin()*10));
        lblNewLabel_11.setText(""+Client.user.getRank());
        
        JLabel lblNewLabel_15 = new JLabel("");
        lblNewLabel_15.setIcon(new ImageIcon(HomePageFrm.class.getResource("/view/Hinh-Kamui-Chibi-Cute-de-thuong-dang-yeu (1) (2).png")));
        lblNewLabel_15.setBounds(69, 18, 166, 170);
        panel.add(lblNewLabel_15);
        
        if(Client.user.getNumberOfGame()==0){
        	lblNewLabel_12.setText("-");
        }
        else{
        	lblNewLabel_12.setText(String.format("%.2f", (float)Client.user.getNumberOfwin()/Client.user.getNumberOfGame()*100)+"%");
        }
    }
    private void jLabel1AncestorMoved(javax.swing.event.AncestorEvent evt) {
   
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đặt mật khẩu cho phòng không?", "Tạo phòng", JOptionPane.YES_NO_OPTION);
        if(res==JOptionPane.YES_OPTION){
            Client.closeView(Client.View.HOMEPAGE);
            Client.openView(Client.View.CREATEROOMPASSWORD);
        }
        else if(res==JOptionPane.NO_OPTION){
            try {
            Client.socketHandle.write("create-room,");
            Client.closeView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        } 
    }
    private void btnNewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(rootPane, "Luật chơi: luật quốc tế 5 nước chặn 2 đầu\n"
                + "Hai người chơi luân phiên nhau chơi trước\n"
                + "Người chơi trước đánh X, người chơi sau đánh O\n"
                + "Bạn có 20 giây cho mỗi lượt đánh, quá 20 giây bạn sẽ thua\n"
                + "Khi cầu hòa, nếu đối thủ đồng ý thì ván hiện tại được hủy kết quả\n"
                + "Với mỗi ván chơi bạn có thêm 1 điểm, nếu hòa bạn được thêm 5 điểm,\n"
                + "nếu thắng bạn được thêm 10 điểm\n"
                + "Chúc bạn chơi game vui vẻ");
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Client.closeView(Client.View.HOMEPAGE);
            Client.openView(Client.View.ROOMLIST);
            Client.socketHandle.write("view-room-list,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        Client.openView(Client.View.RANK);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Client.socketHandle.write("offline,"+Client.user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeView(Client.View.HOMEPAGE);
        Client.openView(Client.View.LOGIN);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
    	  Client.closeView(Client.View.HOMEPAGE);
        Client.openView(Client.View.GAMEAI);
    }

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        Client.openView(Client.View.ROOMNAMEFRM);
    }
    public void addMessage(String message){
        String temp = jTextArea1.getText();
        temp+=message+"\n";
        jTextArea1.setText(temp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
    private JPanel getContentPane;

  
    private javax.swing.JButton jButton9;
    
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
}
