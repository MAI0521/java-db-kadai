package kadai.kadai_07;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Posts_Chapter07_2 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;

		
//		データベースに接続する (getConnection)
		try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "MMtt-86240521"
            );

            System.out.println("データベース接続成功：" + con);
            
//		SQLクエリを準備する (createStatement)
            String sql = """
            	INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES 
                ( "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"),
                ( "1002", "2023-02-08", "お疲れ様です！", "12" ),
                ( "1003", "2023-02-09", "今日も頑張ります！", "18" ),
                ( "1004", "2023-02-09", "無理は禁物ですよ！" ,  "17"),
                ( "1002", "2023-02-10", "明日から連休ですね！", "20" );
                """;
            
            statement = con.prepareStatement(sql);  
            
//		投稿データを追加する (executeUpdate)
            System.out.println("レコード追加を実行します" );
            int rowCnt = statement.executeUpdate();
            System.out.println( rowCnt + "件のレコードが追加されました");
//		投稿データを検索する (executeQuery)
            String sql2 = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
            ResultSet result = statement.executeQuery(sql2);
            
            System.out.println("ユーザーIDが1002のレコードを検索しました" );
//		検索結果を抽出・表示する (ResultSet)
            while(result.next()) {
                Date postedAt = result.getDate("posted_at");
                String postContent = result.getString("post_content");
                int postLikes = result.getInt("likes");
                System.out.println(result.getRow() + "件目：投稿日時=" + postedAt
                                   + "／投稿内容=" + postContent + "／いいね数=" + postLikes );
            	}
        } catch(SQLException e) {
        	System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}