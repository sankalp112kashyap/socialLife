JOptionPane.showMessageDialog(null, "No Posts to Show");




SET_TEXT_AREA
         ResultSet rs = handler.getComments(postId);
        String s = "";
        try
        {
            do
            {
                s = s + rs.getString("comment_text") + "  @" + rs.getString("username") + "\n\n";
            }
            while (rs.next());
        }
        catch (Exception e)
        {
            System.out.println("Comment display error");
        }

GET_jLIST
        commentList.setText(s);

        int userFrom = Login.getUserId();
        int index = friendList.getSelectedIndex();
        // System.out.println(index);
        if (index >= 0 && userFrom >= 0)
        {
            int userTo = userIds.get(index);
            handler.insertIntoFriendRequests(userFrom, userTo);
        }


HOME

        this.dispose();
        Home h = new Home();
        h.setLocationRelativeTo(null);
        h.setVisible(true);


LOGOUT
        Login r = new Login();
        // to show the register form r
        r.setVisible(true);
        // to center the register form
        r.setLocationRelativeTo(null);
        // to dispose all resources
        this.dispose();

FILE CHOOSER

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc=new JFileChooser();
        int i1=fc.showOpenDialog(this);
     if(i1==JFileChooser.APPROVE_OPTION)
     {
        File f=fc.getSelectedFile();
         filepath=f.getPath();

        System.out.println(filepath);
    }
    }
