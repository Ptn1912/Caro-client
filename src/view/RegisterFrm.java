package view;


import controller.Client;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegisterFrm extends javax.swing.JFrame {
    public RegisterFrm() {
    	initComponents();
    	comboBox = new JComboBox();
    	comboBox.setMaximumRowCount(5);
        for (int i=0; i<=5; i++) {
        	comboBox.addItem(new ImageIcon("assets/avatar/"+i+".jpg"));
        }
        comboBox.setBounds(287, 291, 141, 109);
    	getContentPane().add(comboBox);
    }
    private void initComponents() {
    	setTitle("Caro Game");
    	setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 769, 546);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		getContentPane = new JPanel();
		getContentPane.setBackground(new Color(51, 189, 178));
		getContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(getContentPane);
        getContentPane().setLayout(null);
        
    	JLabel lblNewLabel = new JLabel("Đăng Kí");
    	lblNewLabel.setForeground(new Color(255, 255, 255));
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
    	lblNewLabel.setBounds(318, 8, 183, 55);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblNewLabel_1 = new JLabel("Tài khoản");
    	lblNewLabel_1.setForeground(new Color(255, 255, 255));
    	lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
    	lblNewLabel_1.setBounds(148, 85, 116, 33);
    	getContentPane().add(lblNewLabel_1);
    	
    	textField = new JTextField();
    	textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	textField.setBounds(287, 82, 296, 47);
    	getContentPane().add(textField);
    	textField.setColumns(10);
    	
    	JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
    	lblNewLabel_2.setForeground(new Color(255, 255, 255));
    	lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
    	lblNewLabel_2.setBounds(148, 145, 116, 36);
    	getContentPane().add(lblNewLabel_2);
    	
    	JLabel lblNewLabel_3 = new JLabel("NickName");
    	lblNewLabel_3.setForeground(new Color(255, 255, 255));
    	lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 19));
    	lblNewLabel_3.setBounds(148, 219, 102, 36);
    	getContentPane().add(lblNewLabel_3);
    	
    	textField_2 = new JTextField();
    	textField_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	textField_2.setBounds(287, 219, 296, 47);
    	getContentPane().add(textField_2);
    	textField_2.setColumns(10);
    	
    	JLabel lblNewLabel_4 = new JLabel("Avatar");
    	lblNewLabel_4.setForeground(new Color(255, 255, 255));
    	lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 19));
    	lblNewLabel_4.setBounds(148, 293, 102, 47);
    	getContentPane().add(lblNewLabel_4);
    	
    	
    	
    	JButton btnNewButton = new JButton("Đã có tài khoản");
    	btnNewButton.setForeground(new Color(255, 255, 255));
    	btnNewButton.setBackground(new Color(0, 128, 189));
    	btnNewButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent evt) {
    			Client.closeView(Client.View.REGISTER);
    	        Client.openView(Client.View.LOGIN);
    		}
    	});
    	
    	btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	btnNewButton.setBounds(153, 434, 191, 42);
    	getContentPane().add(btnNewButton);
    	
    	JButton btnNewButton_1 = new JButton("Đăng kí");
    	btnNewButton_1.setBackground(new Color(255, 57, 57));
    	btnNewButton_1.setForeground(new Color(255, 255, 255));
    	btnNewButton_1.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent evt) {
    			jButton1ActionPerformed(evt);
    		}
    	});
    	btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	btnNewButton_1.setBounds(458, 434, 170, 42);
    	getContentPane().add(btnNewButton_1);
    	
    	passwordField = new JPasswordField();
    	passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	passwordField.setBounds(287, 147, 296, 47);
    	getContentPane.add(passwordField);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClickedd(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Client.closeView(Client.View.REGISTER);
        Client.openView(Client.View.LOGIN);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String username = textField.getText();
            if(username.isEmpty())
                throw new Exception("Vui lòng nhập tên tài khoản");
            String password = String.copyValueOf(passwordField.getPassword());
            if(password.isEmpty())
                throw new Exception("Vui lòng nhập mật khẩu");
            String nickName = textField_2.getText();
            int avatar = comboBox.getSelectedIndex();
            if(avatar==-1){
                throw new Exception("Vui lòng chọn avatar");
            }
            if(nickName.isEmpty())
                throw new Exception("Vui lòng nhập nickname");
            Client.closeAllViews();
            Client.openView(Client.View.GAMENOTICE, "Đăng kí tài khoản", "Đang chờ phản hồi");
            Client.socketHandle.write("register,"+username+","+password+","+nickName+","+avatar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ImageIcon> comboBox;

	private JPanel getContentPane;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
 
    private JTextField textField;
    private JTextField textField_2;
    private JPasswordField passwordField;
}
