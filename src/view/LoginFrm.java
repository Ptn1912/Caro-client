package view;

import controller.Client;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;

public class LoginFrm extends javax.swing.JFrame {
	 public LoginFrm() {
	        initComponents();
	    }
	 public LoginFrm(String taiKhoan, String matKhau) {
	        initComponents();
	      
	    }
	 public void initComponents() {
	    	setTitle("Caro Game");
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 852, 557);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
			
			getContentPane = new JPanel();
			getContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(getContentPane);
	        getContentPane().setLayout(null);
	        
	        JLabel lblNewLabel_1 = new JLabel("Đăng Nhập");
	        lblNewLabel_1.setForeground(new Color(255, 255, 255));
	        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
	        lblNewLabel_1.setBounds(293, 33, 337, 101);
	        getContentPane().add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_2 = new JLabel("Tên đăng nhập");
	        lblNewLabel_2.setForeground(new Color(255, 255, 255));
	        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 22));
	        lblNewLabel_2.setBounds(112, 160, 174, 55);
	        getContentPane().add(lblNewLabel_2);
	        
	        textField = new JTextField();
	        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        textField.setBackground(new Color(251, 251, 251));
	        textField.setBounds(326, 166, 314, 55);
	        getContentPane().add(textField);
	        textField.setColumns(10);
	        
	        JLabel lblNewLabel_3 = new JLabel("Mật khẩu");
	        lblNewLabel_3.setForeground(new Color(255, 255, 255));
	        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 22));
	        lblNewLabel_3.setBounds(112, 291, 143, 51);
	        getContentPane().add(lblNewLabel_3);
	        
	        JButton btnNewButton = new JButton("Đăng kí");
	        btnNewButton.setBackground(new Color(255, 66, 66));
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent evt) {
	        		jButton2ActionPerformed(evt);
	        	}
	        });
	        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        btnNewButton.setBounds(156, 424, 174, 51);
	        getContentPane().add(btnNewButton);
	        
	        passwordField = new JPasswordField();
	        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        passwordField.setBackground(new Color(251, 251, 251));
	        passwordField.setBounds(326, 293, 314, 55);
	        getContentPane().add(passwordField);
	        
	        JButton btnNewButton_1 = new JButton("Đăng nhập");
	        btnNewButton_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent evt) {
	        		jButton1ActionPerformed(evt);
	        	}
	        });
	        btnNewButton_1.setBackground(new Color(105, 148, 235));
	        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        btnNewButton_1.setBounds(489, 424, 165, 51);
	        getContentPane().add(btnNewButton_1);
	        
	        JLabel lblNewLabel = new JLabel("");
	        lblNewLabel.setIcon(new ImageIcon("assets/free-figma-game-tic-tac-toe_1635143619.jpg"));
	       
	        lblNewLabel.setBounds(0, 0, 838, 520);
	        getContentPane().add(lblNewLabel);
	    }
    
    public void showError(String message){
        JOptionPane.showMessageDialog(rootPane, message);
    }
    public void log(String message){
        JOptionPane.showMessageDialog(rootPane,"ID của server thread là:"+ message);
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String taiKhoan = textField.getText();
            if(taiKhoan.isEmpty())
                throw new Exception("Vui lòng nhập tên tài khoản");
            String matKhau = String.copyValueOf(passwordField.getPassword());
            if(matKhau.isEmpty())
                throw new Exception("Vui lòng nhập mật khẩu");
            Client.closeAllViews();
            Client.openView(Client.View.GAMENOTICE, "Đăng nhập", "Đang xác thực thông tin đăng nhập");
            Client.socketHandle.write("client-verify,"+taiKhoan+","+matKhau);            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Client.closeView(Client.View.LOGIN);
        Client.openView(Client.View.REGISTER);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
	private JPanel getContentPane;
    private JTextField textField;
    private JPasswordField passwordField;
}
