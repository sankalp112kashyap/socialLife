

// update
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
