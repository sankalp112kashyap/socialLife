/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sociallife.ui.creator;

import sociallife.ui.profile.*;
import sociallife.ui.home.*;
import sociallife.ui.friends.FriendRequestList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import sociallife.database.DatabaseHandler;
import sociallife.ui.friends.MessageFriends;
import sociallife.ui.friends.SearchFriends;
import sociallife.ui.friends.SearchMoreFriends;
import sociallife.ui.games.Minesweeper;
import sociallife.ui.login.Login; 
import sociallife.ui.login.Register;
import sociallife.ui.notifications.Notifications;
import sociallife.ui.profile.UploadPost;
import sociallife.ui.trends.SearchTrends;

/**
 *
 * @author anonymous
 */
public class DisplayParticularPageGeneral extends javax.swing.JFrame {

    /**
     * Creates new form DisplayParticularPageOfUser
     */
    
    public ResultSet resultSet = null;
    public ResultSet resultSetPosts = null;
    
    public final int POSTS_AT_A_TIME = 2;
    public int nextCount = 0;
    public int totalPosts = 1000000;
    public int postIds[] = {-1, -1};
    
    public static int pageId;
    
    private static DatabaseHandler handler = null;

    public DisplayParticularPageGeneral() 
    {
        handler = DatabaseHandler.getInstance();
        initComponents();
    }
    
    public DisplayParticularPageGeneral(int pageId) 
    {
        handler = DatabaseHandler.getInstance();
        this.pageId = pageId;
        initComponents();
        
        /*
        try 
        {
            
            File directory = new File("./");
            System.out.println(directory.getAbsolutePath());

            //BufferedImage myPicture = ImageIO.read(new File("/home/anonymous/NetBeansProjects/SocialLife/src/default.png"));
            picLabel.setIcon(new ImageIcon(new ImageIcon("default_profile_pic.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));

            //picLabel.setIcon(new javax.swing.ImageIcon("default2.png"));
            //a/dd(picLabel);
        }
        catch (Exception e)
        {
            System.out.println("Error in immage " + e.getMessage());
        }
       */
        
        // setting up the user information
        try {
        
            
            resultSet = handler.getPageInfo(pageId);
            
            // nameLabel set to resultSet
            nameLabel.setText(resultSet.getString("page_name"));
            
            // userLabel set to resultSet
            String username = resultSet.getString("page_name");
            usernameLabel.setText(username);
            
            // picLabel is set to resultSet
            String profile_pic_path = resultSet.getString("page_pic_path");
            picLabel.setIcon(new ImageIcon(new ImageIcon(profile_pic_path).getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));
          
            String statusText = resultSet.getString("page_description");
            statusLabel.setText(statusText);    
            
            
        }
        catch (Exception e)
        {
            System.out.println("Error Initializing Home " + e.getMessage());
        }
        
        
        
        setPosts();
        
        totalPosts = handler.getTotalContentPosts(Login.getUserId());
        
    }
    
    /**
     * To set Posts on DisplayParticularPageOfUser Screen
     */
        
    
    public void setPosts()
    {
        // setting up the post information
        try
        {
            resultSetPosts = handler.getTwoContentPosts(nextCount, pageId);
            
            if (resultSetPosts != null)
            {
                
                for (int i = 0; i < POSTS_AT_A_TIME; i++)
                {
                    // set global variable postIds
                    postIds[i] = resultSetPosts.getInt("content_id");
                    
                    if (i == 0)
                    {
                        posterNameLabel1.setText(resultSetPosts.getString("page_name"));
                        postTextLabel1.setText(resultSetPosts.getString("post_text"));
                        picLabelPost1.setIcon(new ImageIcon(resultSetPosts.getString("image_path")));
                        photoLabel1.setIcon(new ImageIcon(new ImageIcon(resultSetPosts.getString("page_pic_path")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                        
                    }
                    else
                    {
                        posterNameLabel2.setText(resultSetPosts.getString("page_name"));
                        postTextLabel2.setText(resultSetPosts.getString("post_text"));
                        picLabelPost2.setIcon(new ImageIcon(resultSetPosts.getString("image_path")));
                        photoLabel2.setIcon(new ImageIcon(new ImageIcon(resultSetPosts.getString("page_pic_path")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    }
                    // update view count
                    handler.updateContentViewCount(postIds[i]);
                    
                    if (!resultSetPosts.next()) 
                        break;
                    
                    
                }
                System.out.println(posterNameLabel1);
                if (posterNameLabel1 == null || posterNameLabel1.getText().equals(""))
                    likeButton1.hide();
                
                if (posterNameLabel1 == null || posterNameLabel2.getText().equals(""))
                    likeButton2.hide();
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No Posts to Show"); 
            }
        }
        
        catch (Exception e)
        {
            System.out.println("Error Initializing Posts in Home " + e.getMessage());
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

        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        usernameLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        picLabel = new javax.swing.JLabel();
        posterNameLabel1 = new javax.swing.JLabel();
        postTextLabel1 = new javax.swing.JLabel();
        picLabelPost1 = new javax.swing.JLabel();
        postTextLabel2 = new javax.swing.JLabel();
        posterNameLabel2 = new javax.swing.JLabel();
        picLabelPost2 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        photoLabel2 = new javax.swing.JLabel();
        photoLabel1 = new javax.swing.JLabel();
        likeButton2 = new javax.swing.JButton();
        likeButton1 = new javax.swing.JButton();
        prevButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        viewTrendsButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Home = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usernameLabel.setForeground(new java.awt.Color(156, 156, 156));
        usernameLabel.setText("@username");

        nameLabel.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        nameLabel.setText("Name");

        picLabel.setIcon(new javax.swing.ImageIcon("/home/anonymous/NetBeansProjects/SocialLife/src/default.png")); // NOI18N

        posterNameLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        posterNameLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        posterNameLabel1.setName(""); // NOI18N

        postTextLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                postTextLabel1MousePressed(evt);
            }
        });

        picLabelPost1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                picLabelPost1MousePressed(evt);
            }
        });

        postTextLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                postTextLabel2MousePressed(evt);
            }
        });

        posterNameLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        posterNameLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        posterNameLabel2.setName(""); // NOI18N

        picLabelPost2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                picLabelPost2MousePressed(evt);
            }
        });

        statusLabel.setText("Status");

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        likeButton2.setText("Like");
        likeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeButton2ActionPerformed(evt);
            }
        });

        likeButton1.setText("Like");
        likeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeButton1ActionPerformed(evt);
            }
        });

        prevButton.setText("Previous");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        viewTrendsButton.setText("View Trends");
        viewTrendsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTrendsButtonActionPerformed(evt);
            }
        });

        Home.setText("Home");
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HomeMousePressed(evt);
            }
        });
        jMenuBar1.add(Home);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(usernameLabel)
                                        .addComponent(nameLabel))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(viewTrendsButton)
                                .addGap(74, 74, 74))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(prevButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextButton)
                        .addGap(3, 3, 3))
                    .addComponent(picLabelPost1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(picLabelPost2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(posterNameLabel2)
                            .addComponent(postTextLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(photoLabel2)
                            .addComponent(likeButton2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postTextLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(posterNameLabel1))
                        .addGap(0, 61, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(likeButton1))
                            .addComponent(photoLabel1))
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(photoLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(nameLabel))
                            .addComponent(likeButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(posterNameLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(postTextLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(picLabelPost1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameLabel)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(photoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(posterNameLabel2)))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(postTextLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(likeButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(picLabelPost2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nextButton)
                            .addComponent(prevButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(viewTrendsButton)))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        // TODO add your handling code here:
        if (nextCount != 0)
        {
            nextCount--;
            setPosts();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No Posts to Show"); 
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        if (nextCount < totalPosts - 2)
        {
            nextCount++;
            setPosts();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No Posts to Show"); 
        }
        
    }//GEN-LAST:event_nextButtonActionPerformed

    private void likeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButton2ActionPerformed
        // updating Like Count:
        
        handler.updateContentLikeCount(postIds[1]);
        
        JOptionPane.showMessageDialog(null, "Total Likes: " + handler.getContentLikeCount(postIds[1]));
        
    }//GEN-LAST:event_likeButton2ActionPerformed

    
    
    private void likeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeButton1ActionPerformed
        // TODO add your handling code here:
        handler.updateContentLikeCount(postIds[0]);
        
        JOptionPane.showMessageDialog(null, "Total Likes: " + handler.getContentLikeCount(postIds[0]));
    }//GEN-LAST:event_likeButton1ActionPerformed

    private void postTextLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_postTextLabel1MousePressed
        // TODO add your handling code here:
        try{
        // Add Comment
        this.dispose();
        Comment c = new Comment(postIds[0]);
        c.setVisible(true);
        c.setLocationRelativeTo(null);
        }
        catch (Exception e)
        {
            System.out.println("error");
        }
        
    }//GEN-LAST:event_postTextLabel1MousePressed

    private void picLabelPost1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_picLabelPost1MousePressed
                try{
        // Add Comment
        this.dispose();
        Comment c = new Comment(postIds[0]);
        c.setVisible(true);
        c.setLocationRelativeTo(null);
        }
        catch (Exception e)
        {
            System.out.println("error");
        }
        
    }//GEN-LAST:event_picLabelPost1MousePressed

    private void postTextLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_postTextLabel2MousePressed
        // TODO add your handling code here:
                try{
        // Add Comment
        this.dispose();
        Comment c = new Comment(postIds[1]);
        c.setVisible(true);
        c.setLocationRelativeTo(null);
        }
        catch (Exception e)
        {
            System.out.println("error");
        }
        
    }//GEN-LAST:event_postTextLabel2MousePressed

    private void picLabelPost2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_picLabelPost2MousePressed
        // TODO add your handling code here:
                        try{
        // Add Comment
        this.dispose();
        Comment c = new Comment(postIds[1]);
        c.setVisible(true);
        c.setLocationRelativeTo(null);
        }
        catch (Exception e)
        {
            System.out.println("error");
        }

    }//GEN-LAST:event_picLabelPost2MousePressed

    private void HomeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMousePressed
        // TODO add your handling code here:
        this.dispose();
        Home h = new Home();
        h.setLocationRelativeTo(null);
        h.setVisible(true);


    }//GEN-LAST:event_HomeMousePressed

    private void viewTrendsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTrendsButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        SearchTrends s = new SearchTrends();
        s.setVisible(true);
        s.setLocationRelativeTo(null);
    }//GEN-LAST:event_viewTrendsButtonActionPerformed

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
            java.util.logging.Logger.getLogger(DisplayParticularPageGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayParticularPageGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayParticularPageGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayParticularPageGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new DisplayParticularPageGeneral().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Home;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton likeButton1;
    private javax.swing.JButton likeButton2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel photoLabel1;
    private javax.swing.JLabel photoLabel2;
    private javax.swing.JLabel picLabel;
    private javax.swing.JLabel picLabelPost1;
    private javax.swing.JLabel picLabelPost2;
    private javax.swing.JLabel postTextLabel1;
    private javax.swing.JLabel postTextLabel2;
    private javax.swing.JLabel posterNameLabel1;
    private javax.swing.JLabel posterNameLabel2;
    private javax.swing.JButton prevButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JButton viewTrendsButton;
    // End of variables declaration//GEN-END:variables
}
