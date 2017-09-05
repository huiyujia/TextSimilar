package Moxie;

import java.util.List;

/**
 * Created by jiahuiyu on 2017/9/2.
 */
public class SimMovieList {
    private static Integer movie_id;
    public static List<Integer> simmovieslist;
    public void setMovie_id(Integer movie_id) {this.movie_id = movie_id;}
    public static Integer getMovie_id() {return movie_id;}
    public void setsimmovieslist(List<Integer> simmovieslist) {this.simmovieslist= simmovieslist;}
    public static List<Integer> getsimmovieslist() {return simmovieslist;}
}
