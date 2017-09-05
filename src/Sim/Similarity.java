package Sim;

import DB.DbClose;
import DB.DbConnect;
import Moxie.KeyWordsTable;
import Moxie.SimMovieList;
import Moxie.SimTable;
import Moxie.WeightTable;
import Seg.SegAlgorithm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

/**
 * Created by jiahuiyu on 2017/9/2.
 */

public class Similarity {
    public static void main(String[] args) {
        Connection conn = DbConnect.getconn();
        String sqlweight = "SELECT v_id,weightlist from weighttable";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        TextSimilarityy sim=new TextSimilarityy();
        try {
            double similarscore=0;
            Map weightmap=new HashMap();
            pstmt = conn.prepareStatement(sqlweight);
            rs = pstmt.executeQuery();
            Integer v_id=0;
            String weightlist=null;
            while (rs.next()) {
                v_id = rs.getInt(1);
                weightlist = rs.getString(2);
                weightmap.put(v_id, weightlist);
            }
                //遍历每一个电影的权重值，进行相似度计算
                Iterator it1 = weightmap.entrySet().iterator();
                Object v_id1=0;
                Object weightlist1=null;
                while (it1.hasNext()) {
                    Map.Entry entry = (Map.Entry) it1.next();
                    v_id1 = entry.getKey();
                    weightlist1 = entry.getValue();
                    }
                Iterator it2 = weightmap.entrySet().iterator();
                Object v_id2=0;
                Object weightlist2=null;
                while (it2.hasNext()) {
                    Map.Entry entry = (Map.Entry) it2.next();
                    v_id2 = entry.getKey();
                    weightlist2 = entry.getValue();
                }
            //计算相似度
                if(v_id1!=v_id2) {
                    similarscore=sim.similarityScore(weightlist1,weightlist2);
                }
            //把计算得到的相似度值保存在相似度表格中
            String v_id11= SegAlgorithm.toString(v_id1);
            String v_id22=SegAlgorithm.toString(v_id2);

            String sql = "insert into SimTable (id1,id2,sim) values(?,?,?)";
            SimTable simtable = new SimTable();
            simtable.setid_1(v_id11);
            simtable.setid_2(v_id22);
            simtable.setsim(similarscore);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null && rs != null) {
                DbClose.queryClose(pstmt, rs, conn);
            }
        }
    }
}
