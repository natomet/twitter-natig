package DB;

import helper.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;




import org.apache.log4j.Logger;

import com.google.gson.Gson;

import twitter4j.User;

public class TwitterTestDao {

    private static Gson         gson         = new Gson();

    private static final Logger logger       = Logger.getLogger(TwitterTestDao.class);

    private static final String INSERT_TWITTER_USER = "INSERT INTO twitter (user_id,friends_count,followers_count) VALUES(?,?,?)";

    public static void insertTwitterUser(User twitter_user ) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

    
        
        try {
    	        con = ConnectionPll.getInstance().getConnection();
    	        
    	        stmt = con.prepareStatement(INSERT_TWITTER_USER);

//            int i = 0;
            
                    stmt.setLong(1, twitter_user.getId());
                    stmt.setLong(2, twitter_user.getFriendsCount());
                    stmt.setLong(3, twitter_user.getFollowersCount());
  //                  stmt.setString(3, "likes");
  //                  stmt.setString(4, twitter_user.get_lastLikesObject()));
 //                   Long d = System.currentTimeMillis();
//                    stmt.setLong(5, d / 1000);
                    // Sadece status message ' da updated_time bulunmuyor.
  //                  stmt.setTimestamp(6, new Timestamp(d));

            stmt.executeUpdate();
            con.commit();
            
        } catch (Exception e) {
            logger.error("Couldn't insert user likes to database : " + e);
        } finally {
            SQLUtil.forceCloseConnection(con, stmt, rs);
        }

    }
}
