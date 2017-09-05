package Sim;

import org.apdplat.word.segmentation.Word;

import java.util.List;

/**
 * Created by jiahuiyu on 2017/9/5.
 */
public class TextSimilarityy{
    public String toString(Object object){
        return object.toString();
    }
    //todo 重写相似度分数计算方法
    org.apdplat.word.analysis.TextSimilarity textscore=new org.apdplat.word.analysis.TextSimilarity() {
        @Override
        protected double scoreImpl(List<Word> list, List<Word> list1) {
            return 0;
        }
    };
    public double similarityScore(Object weightlist1,Object weightlist2) {
        double similarscore=0;
        String weightstring1=toString(weightlist1);
        String weightstring2=toString(weightlist2);
        similarscore=textscore.similarScore(weightstring1,weightstring2);
        return similarscore;
    }
}

