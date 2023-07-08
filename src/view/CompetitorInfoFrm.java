package view;

import controller.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.User;

public class CompetitorInfoFrm extends javax.swing.JFrame {
    private boolean isFriend;
    private User user;
    public CompetitorInfoFrm(User user) {
        try {
            
            this.user = user;
            this.setTitle("Caro Game ");
            this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
            this.setResizable(false);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            jLabel6.setIcon(new ImageIcon("assets/avatar/"+user.getAvatar()+".jpg"));
            jLabel7.setText(user.getNickname());
            jLabel8.setText(""+user.getNumberOfGame());
            jLabel9.setText(""+user.getNumberOfwin());
            jLabel17.setText(""+user.getNumberOfDraw());
            jLabel13.setText(""+user.getRank());
            if(user.getNumberOfGame()==0){
                jLabel15.setText("-");
            }
            else{
                jLabel15.setText(String.format("%.2f",(float)user.getNumberOfwin()/user.getNumberOfGame()*100)+"%");
            }
            jLabel12.setText(""+(user.getNumberOfwin()*10+user.getNumberOfGame()));
            Client.socketHandle.write("check-friend,"+user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
    public void checkFriend(boolean isFriend){
        this.isFriend = isFriend;
        if(isFriend){
            jButton1.setIcon(new ImageIcon("assets/icon/friendship.png"));
            jButton1.setToolTipText("Bạn bè");
            jLabel5.setText("Bạn bè");
            
        }
        else{
            jButton1.setIcon(new ImageIcon("assets/icon/add-friend.png"));
            jButton1.setToolTipText("Click để gửi yêu cầu kết bạn");
            jLabel5.setText("Kết bạn để chơi cùng nhau dễ dàng hơn");
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(isFriend){
            JOptionPane.showMessageDialog(rootPane, "Bạn và đối thủ đang là bạn bè");
        }
        else{
            int res = JOptionPane.showConfirmDialog(rootPane, "Bạn đồng ý gửi lời mời kết bạn tới đối thủ chứ", "Xác nhận yêu cầu kết bạn", JOptionPane.YES_NO_OPTION);
            if(res==JOptionPane.YES_OPTION){
                try {
                    Client.socketHandle.write("make-friend,"+user.getID());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
        }
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
