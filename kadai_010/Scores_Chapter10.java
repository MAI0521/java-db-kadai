package kadai.kadai_10;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Scores_Chapter10 {
    public static void main(String[] args) {

        Connection con = null;
        Statement statement = null;
        
//	データベースに接続する(getConnection)
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "MMtt-86240521"
            );
            
            System.out.println("データベース接続成功:" + con);
        
//	sqlクエリを準備する (createStatement)
            statement = con.createStatement();
            String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
            
//  テーブル内の点数データを更新する (executeUpdate)
            System.out.println("レコード更新を実行します");
            int rowCnt = statement.executeUpdate(sql);
            System.out.println( rowCnt + "件のレコードが更新されました");
            
//	テーブルのレコードを取得し、点数準に並べ替える (executeQuery)
            String sql2 = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            
//  並べ替え結果を表示する (ResultSet)
            ResultSet result = statement.executeQuery(sql2);
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int math = result.getInt("score_math");
                int english = result.getInt("score_english");
                System.out.println(result.getRow() + "件目：生徒id=" + id
                                   + "／氏名=" + name + "／数学=" + math + "／英語=" + english );
            }
        } catch(InputMismatchException e) {
            System.out.println("入力が正しくありません");
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
