package twit_check;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;








import twitter4j.*;
import twitter4j.auth.AccessToken;
import properties_def.Access_property;
import properties_def.Properties_assign;
import properties_def.Property_validate;

import java.io.ByteArrayInputStream;

import DB.ConnectionPll;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import helper.SQLUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import DB.TwitterTestDao;
import DB.ConnectionPll;

import org.apache.log4j.Logger;




public class Fetch_daha {

	




		public static void main(String[] args) throws IOException, TwitterException {
			  
	        //Your Twitter App's Consumer Key
//	        String consumerKey = "nRtKfU5JUXGt6ifniDq2kpstw";
	  
	        //Your Twitter App's Consumer Secret
//	        String consumerSecret = "YnKIR1PcID2bAPg9ms15FwIt7mfWkaLwfUUWoX3kfa2EmbkLGD";
	  
	        //Your Twitter Access Token
 //       String accessToken = "52359692-0ZSKiKRMS5YC7v6mAhObGM2qsTNJsJnUXCj4Tt4jl";
	  
	        //Your Twitter Access Token Secret
//        String accessTokenSecret = "TlUAFu19b0TwN8wMiF5hnPwm9BSdV9csbS9svNo8Htsfw";
	  
	        //Instantiate a re-usable and thread-safe factory
           TwitterFactory twitterFactory = new TwitterFactory();
	  
	        //Instantiate a new Twitter instance
	        Twitter twitter = twitterFactory.getInstance();
	  
	        //setup OAuth Consumer Credentials
//	        twitter.setOAuthConsumer( consumerKey, consumerSecret );
//	        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	        
	        try {	
		        Property_validate.initialize("conf/twitter4j.properties");
		        }
		        catch (Exception e) {
//					logger.error("Couldn't load property file, please check your configuration.");
		        	
		        }
	        
	        
	        twitter.setOAuthConsumer(Access_property
	    			.getProperty(Properties_assign.consumerKey),Access_property
	    			.getProperty(Properties_assign.consumerSecret));
	    	  
	    	        //setup OAuth Access Token
	    	        twitter.setOAuthAccessToken(new AccessToken(Access_property.getProperty(Properties_assign.accessToken), Access_property.getProperty(Properties_assign.accessTokenSecret)));
	    	        
	        
	           long id_me = twitter.getId(); 
		       User me=twitter.showUser(id_me);
		       twitter.showUser(id_me);
	        

//		   final String DATASOURCE_NAME = "jdbc/stage";
		   
	        
           System.out.println(Access_property.getProperty(Properties_assign.consumerKey));
//			
           try {
			ConnectionPll.getInstance().init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	        TwitterTestDao.insertTwitterUser(me);
	        
	        
	        
	       System.out.println("ID :"+id_me); 
	       System.out.print("Location :" +me.getLocation());
	       
	       System.out.println("friends count :"+ me.getFriendsCount());  
	       System.out.println("Followers Count :"+ me.getFollowersCount()); 
	       System.out.println("Favourites Count :"+ me.getFavouritesCount()); 
	       System.out.println("Listed Count :"+ me.getListedCount()); 
	       System.out.println("Statuses Count:"+ me.getStatusesCount()); 
	       
	       System.out.println("name :"+me.getName());
	       System.out.println("screenname :"+me.getScreenName());
	       System.out.println("Description :"+me.getDescription()) ;
	       System.out.println("Url :" +me.getURL()) ;
	       System.out.println("Language :" +me.getLang()) ;
	       System.out.println("TimeZone :" +me.getTimeZone()) ;
	       System.out.println("Utc_offset :"+me.getUtcOffset()) ;
	       System.out.println("isGeoEnabled :"+me.isGeoEnabled()) ;
	       System.out.println("isVerified :"+me.isVerified() ) ;
	       System.out.println("isProtected :"+me.isProtected()) ;
	       System.out.println("Created at :"+me.getStatus().getCreatedAt()) ;
	       System.out.println("isProtected :"+me.isProtected()) ;
	       System.out.println("isProtected :"+me.isProtected()) ;
	       
	       
	       
	       
	////user info about tweets       
	       ResponseList<Status> statuses = twitter.getUserTimeline();
	        System.out.println("Showing user tweets and ids");
	        for (Status status : statuses) {
	            System.out.println(status.getId() + ":" +
	                               status.getText()+ "     "+ status.getSource()+"  "+
	                               status.getInReplyToStatusId()+" "+
	                               status.getInReplyToScreenName()+" "+
	                               
	                               status.getInReplyToUserId());
	       
	        
       }
	       
	        
	  ////user info about retweets
	        ResponseList<Status>  statuses_retweeted = twitter.getRetweetsOfMe();
	                   System.out.println("Showing @" + me.getScreenName() + "'s tweets that have been retweeted by others.");
	                    for (Status status1 : statuses_retweeted) {
	                        System.out.println(status1.getId() + ":" +
	                                status1.getText());
	                    }
	        
	       
	       
	       
	        try {
	            
	            long cursor = -1;
	            IDs ids;
	            System.out.println("Listing following ids.");
	            do {
	               
	                    ids = twitter.getFriendsIDs(cursor);
	                
	                for (long id : ids.getIDs()) {
	                	User user=twitter.showUser(id);
	                	
	               
	                	System.out.println(id+"  "+user.getName()+"  "+user.getScreenName());
	                
	                
	                }
	             
	            } while ((cursor = ids.getNextCursor()) != 0);
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to get friends' ids: " + te.getMessage());
	            System.exit(-1);
	        }

	        
	      
	        
	        
	        
	        
	    }
}
	  



	
	
	
	
	
	
	
	
	

