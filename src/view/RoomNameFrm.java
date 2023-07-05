/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Font;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Admin
 */
public class RoomNameFrm extends javax.swing.JFrame {
    public RoomNameFrm() {
        initComponents();
    }
    private void initComponents() {
    	setTitle("Caro Game");
    	setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 552, 311);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		getContentPane = new JPanel();
		getContentPane.setBackground(new Color(236, 236, 236));
		getContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(getContentPane);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Vào phòng");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
        lblNewLabel.setBounds(196, 8, 129, 35);
        getContentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Nhập mã phòng");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setBounds(66, 71, 102, 23);
        getContentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Mật khẩu phòng");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(66, 122, 117, 23);
        getContentPane.add(lblNewLabel_2);
        
        textField = new JTextField();
        textField.setBounds(195, 69, 193, 29);
        getContentPane.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(196, 120, 192, 29);
        getContentPane.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Nếu phòng không có mật khẩu hãy để trống");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lblNewLabel_3.setBounds(153, 168, 266, 23);
        getContentPane.add(lblNewLabel_3);
        
        JButton btnNewButton = new JButton("Vào phòng");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		 jButton1ActionPerformed(evt);
        	}
        });
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(97, 180, 207));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnNewButton.setBounds(196, 199, 139, 36);
        getContentPane.add(btnNewButton);
        
        JLabel lblNewLabel_4 = new JLabel("Đang tìm kiếm phòng");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblNewLabel_4.setBackground(new Color(0, 74, 149));
        lblNewLabel_4.setForeground(new Color(0, 128, 255));
        lblNewLabel_4.setBounds(208, 254, 117, 19);
        getContentPane.add(lblNewLabel_4);
        
        
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String roomName = textField.getText();
        if(roomName.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mã phòng");
            return;
        }
        try {
            String password = " ";
            if(textField_1.getText().length()>0){
                password = textField_1.getText();
            }
            Client.socketHandle.write("go-to-room,"+roomName+","+password);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    
 
    private JPanel getContentPane;
    private JTextField textField;
    private JTextField textField_1;
}
