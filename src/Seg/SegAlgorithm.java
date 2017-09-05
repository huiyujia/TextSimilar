package Seg;

import DB.DbClose;
import DB.DbConnect;
import Moxie.KeyWordsTable;
import Moxie.MovieClass;
import Moxie.WeightTable;
import org.apdplat.word.WordFrequencyStatistics;
import org.apdplat.word.segmentation.SegmentationAlgorithm;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.hash;

/**
 * Created by jiahuiyu on 2017/9/2.
 */
public class SegAlgorithm {
    public static String toString(Object object){
        return toString(object);
    }

    /**
     * 正则表达式返回匹配字符串
     * @input 待匹配字符串
     * @output 匹配得到的字符串
     *
     * @param
     */
    public static String getSensitive(String globWords){
       //String keywords = regex.toString();
        String sensitive ="";
        String regx="([1-9][0-9]+){1,}";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(globWords);
        if (matcher.find()) {
            sensitive = matcher.group(1);
        }

        return sensitive;
    }

    public static void main(String[] args) {
        Map moviemap = new HashMap();
        Connection conn = DbConnect.getconn();
        String sqlGnum = "SELECT v_id,focus,description iqiyi_movies";
        PreparedStatement pstmt =null;
        ResultSet rs=null;
        try {
            pstmt = conn.prepareStatement(sqlGnum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer v_id = rs.getInt(1);
                String focus = rs.getString(2);
                String desc = rs.getString(3);
                String movieText = focus + desc;
                moviemap.put(v_id, movieText);
            }


            WordFrequencyStatistics wordFre = new WordFrequencyStatistics();
            Set<String> textFiles = new HashSet<>();
            for (String arg : args) {
                if (arg.equals("-removeStopWord")) {
                    wordFre.setRemoveStopWord(true);
                }
                if (arg.startsWith("-textFile=")) {
                    textFiles.add(arg.replace("-textFile=", ""));
                }
                if (arg.startsWith("-statisticsResultFile=")) {
                    wordFre.setResultPath(arg.replace("-statisticsResultFile=", ""));
                }
                if (arg.startsWith("-segmentationAlgorithm=")) {
                    wordFre.setSegmentationAlgorithm(SegmentationAlgorithm.valueOf(arg.replace("-segmentationAlgorithm=", "")));
                }
            }


            for (String textFile : textFiles) {
                wordFre.seg(new File(textFile), (new File(textFile + ".seg.txt")));
            }
            //wordFre.dump();
            //return;

            //开始分词
                Map wordmap=new HashMap();
                Map weightmap=new HashMap();
                Set keys = moviemap.keySet( );
                if(keys != null) {
                    Iterator iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        Object key = iterator.next();
                        Object value = moviemap.get(key);
                        wordFre.seg(toString(value));
                        wordmap.put(key, value);
                        //输出分词结果到数据库
                        //分词结果输出到KeyWordsTable
                        String keyyy=toString(key);
                        String valuee=toString(value);
                        String sql = "insert into KeyWordsTable (v_id,wordlist) values(?,?)";
                        KeyWordsTable keywordstable = new KeyWordsTable();
                        keywordstable.setV_id(keyyy);
                        keywordstable.setWordlist(valuee);
                    }
                        //提取keywords权重，把v_id和权重保存
                        if (keys != null) {
                            Iterator iterator2 = keys.iterator();
                            while (iterator.hasNext()) {
                                Object key = iterator2.next();
                                String weighword = toString(wordmap.get(key));
                                //正则表达式匹配筛选出权重值
                                String weightvalue=getSensitive(weighword);
                                String keyy=toString(key);
                                weightmap.put(key, weightvalue);
                                String insertweightsql = "insert into WeightTable (v_id,weightlist)values(?,?)";
                                WeightTable weighttable = new WeightTable();
                                weighttable.setV_id(keyy);
                                weighttable.setWeightlist(weightvalue);
                            }
                        }
                    }
           /**
            WeightTable weighttable=new WeightTable();

                String keywordslist;
                String sql="select "
                keywordslist=pstmt.execute(sql)
            */

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception E) {
            System.out.println("error,error,error!");
        }
        finally {
            if(pstmt!=null&&rs!=null) {
                DbClose.queryClose(pstmt, rs, conn);
            }
        }
    }
}
