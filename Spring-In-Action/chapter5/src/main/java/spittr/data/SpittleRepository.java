package spittr.data;

import spittr.entity.Spittle;

import java.util.List;

/**
 * Created by ChenZhePC on 2017/1/16.
 */
public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
}
