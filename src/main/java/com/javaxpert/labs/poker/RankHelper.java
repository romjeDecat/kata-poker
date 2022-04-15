package com.javaxpert.labs.poker;


import io.vavr.API;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static io.vavr.API.*;

/**
 * Helper class used to manage a side case : how ACE card is valuated with MAX points or MIN points
 * we can have straight using ACE rank as 1 or as KING+1 !!
 * so this class makes it easy to manage these 2 edge cases...
 * Using the ordinal() method in enums in not sufficient sadly!!
 */
public class RankHelper {
    private RankHelper(){

    }

    public static Tuple2 valueFromRank(Rank targetRank){
        //BiFunction<Integer, List<Integer>,Boolean> contains = (t,u) -> u.contains(t);

        Tuple2<Integer,Integer> value = Match(targetRank.ordinal()).of
                (
                        Case($(0), Tuple.of(14,1)),
                        Case($(),Tuple.of(targetRank.ordinal()+1,targetRank.ordinal()+1))

                );
        return  value;

    }

}
