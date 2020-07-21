/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sociallife.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author anonymous
 */
public class DatabaseHandler
{

    Connection conn = null;

    // creating a static var to get only one database connection
    private static DatabaseHandler handler = null;

    private Integer d;

    // private constructor because we do not want create more than one connection
    private  DatabaseHandler()
    {
        createConnection();
    }

    private void createConnection()
    {
        // try to connect to database
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialLifeDB","root","");

        }
        catch (Exception e)
        {
            System.out.println("Error in SQL DB Connectivity");
        }
    }

    public static DatabaseHandler getInstance()
    {
        // if there is no handler
        if (handler == null)
        {
            // then create a new Database Handler
            handler = new DatabaseHandler();
        }

        return handler;
    }

    public java.sql.Date getDate(String dob)
    {
        Date date = new Date();
        try
        {
            String testDate = dob;
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = formatter.parse(testDate);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return new java.sql.Date(date.getTime());
    }



    // public boolean insertUser(all fields OVERLOAD)
    public boolean insertUser(String username, String password, String first_name, String last_name, String sex, String email, String dob)
    {
        //
        PreparedStatement preparedStatement = null;

        try
        {
            ResultSet resultSet;

            String countEmailsQuery = "SELECT COUNT(*) FROM users WHERE email = ? ";

            preparedStatement = conn.prepareStatement(countEmailsQuery); // prepareStatement from query
            preparedStatement.setString(1, email); // email is function parameter

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                // System.out.println(resultSet.getInt(1));
                if (resultSet.getInt(1) > 0) // gets the first column
                {
                    // if email is already there then we must return false
                    return false;
                }
            }

	    //checking for username
            String countUsernameQuery = "SELECT COUNT(*) FROM users WHERE username = ? ";

            preparedStatement = conn.prepareStatement(countUsernameQuery); // prepareStatement from query
            preparedStatement.setString(1, username); // email is function parameter

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                // System.out.println(resultSet.getInt(1));
                if (resultSet.getInt(1) > 0) // gets the first column
                {
                    // if USERNAME is already there then we must return false
                    return false;
                }
            }


            // TODO encrypt password Login AND register

            String insertQuery = "INSERT INTO users (username, password, first_name, last_name, sex, email, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, first_name);

            preparedStatement.setString(4, last_name);
            preparedStatement.setString(5, sex);
            preparedStatement.setString(6, email);
            preparedStatement.setDate(7, getDate(dob));

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println("User already exists " + e.getMessage());
        }

        return false;

    }


    /**
     * @return
     * int - id of the user
     * -1: error
     *
     */
    public int checkCredentialsEmail(String email, String password)
    {
        String query = "SELECT id FROM users WHERE email = ? AND password = ?";

        PreparedStatement preparedStatement = null;
        // TODO encryptPassword
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }

        }
        catch (SQLException e)
        {
            System.out.println("ReCheck creds " + e.getMessage());
        }

        return -1;

    }

    /**
     * @return
     * int: id of the user
     * -1: error
     *
     */
    public int checkCredentialsUsername(String username, String password)
    {
        String query = "SELECT user_id FROM users WHERE username = ? AND password = ?";

        PreparedStatement preparedStatement = null;
        // TODO encryptPassword
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }

        }
        catch (SQLException e)
        {
            System.out.println("ReCheck creds " + e.getMessage());
        }

        return -1;

    }

    /**
     *
     * @param userId
     * @return
     * ResultSet
     * null: if error
     */
    public ResultSet getResultSet(int userId)
    {
        // TODO Maybe Dont Take Password
        String query = "SELECT * FROM users WHERE user_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
            if (resultSet.next())
            {
                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;
    }

        public ResultSet getImage(int userId)
    {
        String query = "SELECT DISTINCT * FROM images,users,posts WHERE images.post_id =posts.post_id AND posts.user_id=?";
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);


            ResultSet resultSet = preparedStatement.executeQuery();
            //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
            if (resultSet.next())
            {
                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;
    }
    public boolean addPost(int user_id,String post_text,String image_path)
    {

        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO posts(user_id,post_text,image_path) VALUES (?,?,?)";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            //preparedStatement.setInt(1,d1);//get from somewhere
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, post_text);
            //preparedStatement.setInt(4, 0);
            //preparedStatement.setInt(5, 0);
            preparedStatement.setString(3, image_path);
            //preparedStatement.setString(7, "2019-02-01");

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        return false;
    }



        /*
    public boolean addPostText(int user_id,String post1)
    {
       // String query = "";
        d=7;//this is hardcoded but will be made dynamic after autoincrement works
        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO posts VALUES (?,?,?,?,?,?)";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1,7);//get from somewhere
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, post1);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setString(6, "2019-02-01");

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        return false;
    }

    public boolean addImage(int user_id,String post11)
    {
       // String query = "";

        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO IMAGES VALUES (?,?,?,?)  ";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, 4);//get from somewhere
            preparedStatement.setInt(2, d);
            preparedStatement.setString(3, post11);
            preparedStatement.setString(4, "2009-02-09");


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }
    */

    public boolean updateStatus(int userId, String statusStr)
    {

        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE users SET status = ? WHERE user_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, statusStr);
            preparedStatement.setInt(2, userId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    public boolean updateContentStatus(int pageId, String statusStr)
    {

        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE pages SET page_description = ? WHERE page_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, statusStr);
            preparedStatement.setInt(2, pageId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }


    /*
    public ResultSet gePost(int postId) {

        String query = "SELECT u.user_id as user_id, username, first_name, last_name, profile_pic_path, post_id, post_text, p.created_at as post_created_at, image_path FROM posts p INNER JOIN users u ON p.user_id = u.user_id WHERE post_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }
*/

    // For Feed
    public ResultSet getTwoPosts(int nextCount) {
        // LIMIT 2 because we need 2 posts
        String query = "SELECT u.user_id as user_id, first_name, last_name, profile_pic_path, post_id, post_text, p.created_at as post_created_at, image_path FROM posts p INNER JOIN users u ON p.user_id = u.user_id ORDER BY p.created_at DESC LIMIT ?, 2;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, nextCount);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }

    // For Timeline of specific User
    public ResultSet getTwoPosts(int nextCount, int userId) {

        String query = "SELECT u.user_id as user_id, first_name, last_name, profile_pic_path, post_id, post_text, p.created_at as post_created_at, image_path FROM posts p INNER JOIN users u ON p.user_id = u.user_id WHERE u.user_id = ? ORDER BY p.created_at DESC LIMIT ?, 2;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, nextCount);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }

    public boolean updateViewCount(int postId)
    {
        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE posts SET view_count = view_count + 1 WHERE post_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setInt(1, postId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    public boolean updateLikeCount(int postId)
    {
        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE posts SET like_count = like_count + 1 WHERE post_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setInt(1, postId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }


    public int getTotalPosts() {

        String query = "SELECT COUNT(*) FROM posts;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return 0;

    }


     public int getTotalPosts(int userId) {
        String query = "SELECT COUNT(*) FROM posts WHERE user_id = ?;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return 0;

    }



    public int getLikeCount(int postId) {

        String query = "SELECT like_count FROM posts WHERE post_id = ? ;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return -1;


    }

    public ResultSet getPost(int postId) {
        String query = "SELECT u.user_id as user_id, username, first_name, last_name, profile_pic_path, post_id, post_text, p.created_at as post_created_at, image_path FROM posts p INNER JOIN users u ON p.user_id = u.user_id WHERE post_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;
    }

    public ResultSet getComments(int postId)
    {

        String query = "SELECT * FROM comments, users WHERE comments.user_id = users.user_id AND post_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;


    }

    public boolean insertComment(int userId, int postId, String commentText)
    {
               // String query = "";

        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO comments (user_id, post_id, comment_text) VALUES (?, ?, ?);  ";
        try{

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userId);//get from somewhere
            preparedStatement.setInt(2, postId);
            preparedStatement.setString(3, commentText);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }

    //// friendships start here
    public ResultSet getExistingFriends(int userId)
    {
        String query = "SELECT * FROM friends INNER JOIN users ON friends.user2 = users.user_id WHERE user1 = ? ;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }
    public ResultSet getExistingFriends(int userId, String str)
    {
        str += "%";
        String query = "SELECT * FROM friends INNER JOIN users ON friends.user2 = users.user_id WHERE user1 = ? "
                + "  AND (first_name LIKE ? OR last_name LIKE ? OR username LIKE ? OR CONCAT(first_name, ' ', last_name) LIKE ? ); ";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, str);
            preparedStatement.setString(3, str);
            preparedStatement.setString(4, str);
            preparedStatement.setString(5, str);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }



    // returns friends that are not already friends
    public ResultSet getMoreFriends(int userId)
    {
        String query = "SELECT * FROM users WHERE user_id !=  ? AND user_id NOT IN (SELECT friends.user2 FROM friends INNER JOIN users ON friends.user1 = users.user_id WHERE user_id = ?) ; " ;

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }
    // overloaded method
    public ResultSet getMoreFriends(int userId, String str)
    {
        str += "%"; // get get names beginning with %
        //"SELECT * FROM users WHERE user_id !=  1 AND (first_name LIKE 'shahrukh khan%' OR last_name LIKE 'shahrukh khan%' OR CONCAT(first_name, ' ', last_name) LIKE 'shahrukh khan%' );";
        String query = "SELECT * FROM users WHERE user_id !=  ? AND (first_name LIKE ? OR last_name LIKE ? OR username LIKE ? OR CONCAT(first_name, ' ', last_name) LIKE ? ) "
                + " AND user_id NOT IN (SELECT friends.user2 FROM friends INNER JOIN users ON friends.user1 = users.user_id WHERE user_id = ?) ; " ;

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, str);
            preparedStatement.setString(3, str);
            preparedStatement.setString(4, str);
                        preparedStatement.setString(5, str);
            preparedStatement.setInt(6, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }

    private boolean insertIntoFriends(int user1, int user2)
    {
       // INSERT INTO `friends` (`user1`, `user2`, ) VALUES ('1', '2');
        // INSERT INTO `friends` (`user1`, `user2`, ) VALUES ('2', '1');
       String insertQuery1 = "INSERT INTO friends (user1, user2) VALUES (?, ?);";
       String insertQuery2 = "INSERT INTO friends (user1, user2) VALUES (?, ?);";

       PreparedStatement preparedStatement = null;

        try
        {

            preparedStatement = conn.prepareStatement(insertQuery1);
            preparedStatement.setInt(1, user1);//get from somewhere
            preparedStatement.setInt(2, user2);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            preparedStatement = conn.prepareStatement(insertQuery2);
            preparedStatement.setInt(1, user2);//get from somewhere
            preparedStatement.setInt(2, user1);

            resultRows += preparedStatement.executeUpdate();

            return (resultRows >= 1); // returns true if resultRows >= 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;


    }

    public boolean acceptFriendRequest(int userAccepted, int userSent)
    {
        return insertIntoFriends(userAccepted, userSent)
                && deleteFromFriendRequests(userSent, userAccepted);

    }


    // working for TEST narendramodi
    public ResultSet dispFriendRequests(int userId)
    {

        String query = "select * from friend_requests inner join users on users.user_id = friend_requests.user_from where user_to = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }


    public int usernameToUserId(String username)
    {
        String query = "SELECT user_id FROM users WHERE username = ?";

        PreparedStatement preparedStatement = null;
        // TODO encryptPassword
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }

        }
        catch (SQLException e)
        {
            System.out.println("ReCheck username " + e.getMessage());
        }

        return -1;

    }

    public String userIdToUsername(int userId)
    {
        String query = "SELECT username FROM users WHERE user_id = ?";

        PreparedStatement preparedStatement = null;
        // TODO encryptPassword
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getString(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("ReCheck username " + e.getMessage());
        }

        return "";

    }


    public boolean insertIntoFriendRequests(int userFrom, int userTo)
    {

        // INSERT INTO `friend_requests` (`user_from`, `user_to`) VALUES ('2', '5');
        String insertQuery = "INSERT INTO `friend_requests` (`user_from`, `user_to`) VALUES (?, ?);";
        PreparedStatement preparedStatement = null;
        try{

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userFrom);//get from somewhere
            preparedStatement.setInt(2, userTo);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;


    }

    // srk->modi so now modi can reject srk so srk->modi: functionCall(srk, LOGINakamodi)
    public boolean deleteFromFriendRequests(int userFrom, int userTo)
    {
        // "DELETE FROM `friend_requests` WHERE `friend_requests`.`user_from` = 2 AND `friend_requests`.`user_to` = 5"
        String deleteQuery = "DELETE FROM `friend_requests` WHERE (`user_from`, `user_to`) = (?, ?);";

        PreparedStatement preparedStatement = null;
        try{

            preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, userFrom);//get from somewhere
            preparedStatement.setInt(2, userTo);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }


    public boolean insertIntoMessages(int user_from, int user_to, String message_text)
    {
       // INSERT INTO `messages` (`user_from`, `user_to`, `message_text`) VALUES ('2', '1', 'You guys played really well that day!' );

        String insertQuery = " INSERT INTO `messages` (`user_from`, `user_to`, `message_text`) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try{

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, user_from);//get from somewhere
            preparedStatement.setInt(2, user_to);
            preparedStatement.setString(3, message_text);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    /*
        returns null
    */
    public ResultSet getMessages(int user_from, int user_to)
    {

       //  SELECT * FROM `messages` WHERE (user_from, user_to) = (1, 2) OR (user_from, user_to) = (2, 1) ORDER BY `messages`.`created_at` DESC    }


        String query = "SELECT * FROM `messages` WHERE (user_from, user_to) = (?, ?) OR (user_from, user_to) = (?, ?) ORDER BY `messages`.`created_at` DESC ; " ;

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_from);
            preparedStatement.setInt(2, user_to);

            preparedStatement.setInt(3, user_to);
            preparedStatement.setInt(4, user_from);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;


    }

    public ResultSet getNotifications(int userId)
    {

       // SELECT * FROM `notifications` ORDER BY `notifications`.`created_at` DESC


        String query = "SELECT * FROM `notifications` WHERE user_id = ? ORDER BY `notifications`.`created_at` DESC; " ;

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;


    }

    // set notifications
    public boolean setNotifications(int userId, String notificationText) {


        // INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_text`, `created_at`) VALUES (NULL, '1', '@iamsrk accepted your Friend Request.', CURRENT_TIMESTAMP);

        String insertQuery = "INSERT INTO `notifications` (user_id, notification_text) VALUES (?, ?);";
        PreparedStatement preparedStatement = null;
        try{

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userId);//get from somewhere
            preparedStatement.setString(2, notificationText);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    public ResultSet getExistingTrends(String str)
    {
        str = "%" + str + "%";
        String query = "SELECT * FROM trends WHERE trend_text LIKE ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, str);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;


    }



    // PAGES BEGIN HERE


    public ResultSet getPageInfo(int pageId)
    {
        String query = "SELECT * FROM pages WHERE page_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, pageId);

            ResultSet resultSet = preparedStatement.executeQuery();
            //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
            if (resultSet.next())
            {
                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }

    public boolean addPage(int user_id,String page_name,String page_description, String page_pic_path)
    {

        PreparedStatement preparedStatement = null;
        String insertQuery =  "INSERT INTO pages(user_id,page_name,page_description, page_pic_path) VALUES (?,?,?,?)";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            //preparedStatement.setInt(1,d1);//get from somewhere
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, page_name);
            //preparedStatement.setInt(4, 0);
            //preparedStatement.setInt(5, 0);
            preparedStatement.setString(3, page_description);
            //preparedStatement.setString(7, "2019-02-01");
            preparedStatement.setString(4, page_pic_path);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        return false;
    }


    public ResultSet getExistingPages(int userId, String str)
    {
        str = "%" + str + "%";
        String query = "SELECT * FROM pages WHERE user_id = ? AND page_name LIKE ? ";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, str);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;
    }


    public ResultSet getExistingPages(String str)
    {
        str = "%" + str + "%";
        String query = "SELECT * FROM pages WHERE page_name LIKE ? ";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, str);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }




    // Content Posts and Pages

        public boolean addContentPost(int page_id, String post_text, String image_path)
    {

        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO content_posts(belongs_to_page_id, post_text, image_path) VALUES (?,?,?)";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            //preparedStatement.setInt(1,d1);//get from somewhere
            preparedStatement.setInt(1, page_id);
            preparedStatement.setString(2, post_text);
            preparedStatement.setString(3, image_path);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {

            System.out.println(e);
        }
        return false;
    }




    // For Timeline of specific Page
    public ResultSet getTwoContentPosts(int nextCount, int pageId) {

        String query = "SELECT * FROM (content_show_page csp INNER JOIN content_posts cp ON csp.content_id = cp.content_id) INNER JOIN pages p ON p.page_id = cp.belongs_to_page_id WHERE csp.show_to_page_id = ? ORDER BY `cp`.`created_at` DESC LIMIT ?, 2";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, pageId);
            preparedStatement.setInt(2, nextCount);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }

    public boolean updateContentViewCount(int postId)
    {
        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE content_posts SET view_count = view_count + 1 WHERE content_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setInt(1, postId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    public boolean updateContentLikeCount(int contentId)
    {
        // UPDATE `users` SET `status` = 'Hello ' WHERE `users`.`user_id` = 1;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE content_posts SET like_count = like_count + 1 WHERE content_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setInt(1, contentId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }


    public int getTotalContentPosts() {

        String query = "SELECT COUNT(*) FROM posts;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return 0;

    }


     public int getTotalContentPosts(int pageId) {
        String query = "SELECT COUNT(*) FROM content_posts WHERE belongs_to_page_id = ?;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, pageId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return 0;

    }



    public int getContentLikeCount(int contentId) {

        String query = "SELECT like_count FROM content_posts WHERE content_id = ? ;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return -1;


    }


    // get comments from a particular content post
    public ResultSet getContentComments(int postId)
    {

        String query = "SELECT * FROM content_comments INNER JOIN users ON content_comments.user_id = users.user_id WHERE content_id = ?";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;


    }

    public boolean insertContentComment(int userId, int postId, String commentText)
    {
               // String query = "";

        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO content_comments (user_id, content_id, comment_text) VALUES (?, ?, ?);  ";
        try
        {

            preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userId);//get from somewhere
            preparedStatement.setInt(2, postId);
            preparedStatement.setString(3, commentText);


            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }



    public boolean updateProfilePic(int userId, String filepath)
    {

        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE users SET profile_pic_path = ? WHERE user_id = ?";
        try{
            preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, filepath);
            preparedStatement.setInt(2, userId);

            int resultRows = preparedStatement.executeUpdate(); // returns num of rows 'updated' used for INSERT UPDATE DELETE

            return (resultRows == 1); // returns true if resultRows == 1 else returns false



        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;

    }

    // info about a particular post
    public ResultSet getContentPost(int contentId) {

       //  "SELECT * FROM `content_posts` INNER JOIN pages ON content_posts.belongs_to_page_id = pages.page_id"
       String query = "SELECT * FROM `content_posts` INNER JOIN pages ON content_posts.belongs_to_page_id = pages.page_id WHERE content_id = ?;";

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, contentId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                return resultSet;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in Retrieving Result Set " + e.getMessage());
        }

        return null;

    }


    // ADVERTISOR STARTS HERE

    public ResultSet getPages(int user_id)
{
    String query = "Select * from pages where user_id = ?";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, user_id);


        ResultSet resultSet = preparedStatement.executeQuery();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        if (resultSet.next())
        {
            System.out.println("wowo");
            return resultSet;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return null;
}

public ResultSet getPagesPosts(int page_id)
{
    String query = "Select post_text from content_posts where belongs_to_page_id = ?";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, page_id);


        ResultSet resultSet = preparedStatement.executeQuery();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        if (resultSet.next())
        {
            System.out.println("wowo");
            return resultSet;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return null;
}

public boolean insertAd(int content_id, int show_to_page_id)
{

    String query  = "Insert into content_show_page values (?,?)";
    PreparedStatement preparedStatement = null;


       try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1,content_id);//get from somewhere
        preparedStatement.setInt(2, show_to_page_id);

        //preparedStatement.setInt(1, page_id);


        int rows = preparedStatement.executeUpdate();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        return rows == 1;
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }
return false;
}



public ResultSet getPagesId(String page_name)
{
    String query = "Select page_id from pages where page_name = ?";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, page_name);


        ResultSet resultSet = preparedStatement.executeQuery();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        if (resultSet.next())
        {
            System.out.println("wowo");
            return resultSet;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return null;
}

public ResultSet getPagesall()
{
    String query = "Select page_name from pages";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        //preparedStatement.setString(1, page_name);


        ResultSet resultSet = preparedStatement.executeQuery();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        if (resultSet.next())
        {
            System.out.println("wowo");
            return resultSet;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return null;
}

public ResultSet getcontentId(String content)
{
    String query = "Select content_id from content_posts where post_text = ?";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, content);


        ResultSet resultSet = preparedStatement.executeQuery();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        if (resultSet.next())
        {
            System.out.println("wowo");
            return resultSet;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return null;
}

public boolean insertBudget(int content_id,int budget)
{
    String query = "INSERT INTO AD_BUDGET VALUES (?,?)";
    PreparedStatement preparedStatement = null;
    try
    {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, content_id);
        preparedStatement.setInt(2, budget);

        int rows = preparedStatement.executeUpdate();
        //THIS WAS CREATING ERROR System.out.println(resultSet.getString(2));
        return rows==1;
    }
    catch (Exception e)
    {
        System.out.println("Error in Retrieving Result Set " + e.getMessage());
    }

    return false;
}




}
