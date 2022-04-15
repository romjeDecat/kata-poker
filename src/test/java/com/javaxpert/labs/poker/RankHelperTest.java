package com.javaxpert.labs.poker;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RankHelperTest {

    @Test
    void aceRankShouldReturnTupleWith2DistinctValues(){
        Rank r = Rank.ACE;
        Tuple2<Integer,Integer> tuple = RankHelper.valueFromRank(r);
        assertEquals(Tuple.of(14,1),tuple);
    }

    @Test
    void otherRankShouldReturnTupleWithIdenticalValues(){
        Rank r1=Rank.TEN;
        Tuple2<Integer,Integer> tuple1 = RankHelper.valueFromRank(r1);
        assertEquals(Tuple.of(10,10),tuple1);
    }
}
