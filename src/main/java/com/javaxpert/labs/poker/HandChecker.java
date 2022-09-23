package com.javaxpert.labs.poker;

import io.vavr.*;
import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Check poker hands (5 cards)
 * Use most of the VAVR functional programming features:
 *  - HoF
 *  + Functions as first class citizens
 *  * function application
 *  Code code be improved while using partial funnctions like this
 *
 * Beware that function application is from left to right so check your parameters order!!
 */
public class HandChecker {

    private final Function4<Function<Card,Criteria>,Predicate<Tuple2<Criteria, List<Card>>>,Function0<Integer>,Hand,Boolean> genericFunction=Function4.of(HandChecker::handConformsToCriterias);
    private final Function3<Predicate<Tuple2<Criteria, List<Card>>>,Function0<Integer>,Hand,Boolean> groupByRank = genericFunction.apply(Card::getRank);
    private final Function2<Function0<Integer>,Hand,Boolean> findPairs = groupByRank.apply((tuple2)-> (tuple2._2.size()==2));

    public boolean containsPair(Hand targetHand) {
        return findPairs.apply(()->1,targetHand);
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
       return groupByRank.apply((tuple2)-> (tuple2._2.size()==3 ),
               ()-> 1,
               targetHand);
    }

    public boolean containsFourOfAKind(Hand targetHand) {
        return groupByRank.apply((tuple2)-> (tuple2._2.size()==4 ),
                ()-> 1,
                targetHand);
    }

    public boolean handContainsStraightFlush(Hand targetHand) {
        return handContainsFlush(targetHand) && handContainsStraight(targetHand);
    }

    /**
     * use a double storage for ranks list because ACE  rank has 2 values
     * MIN value (1) & MAX VALUE GREATER THAN KING
     *
     * @param targetHand
     * @return
     */
    public boolean handContainsStraight(Hand targetHand) {
        java.util.List<Tuple2<Integer, Integer>> rawRanksList = targetHand.getCards().get().map(card -> RankHelper.valueFromRank(card.getRank())).collect(Collectors.toList());
        java.util.List<Tuple2<Integer, Integer>> copyRanksList = new ArrayList<>(rawRanksList);
        Comparator<Tuple2<Integer, Integer>> compareUsingAceMaxValue = (o, t1) -> o._1().compareTo(t1._1());
        Comparator<Tuple2<Integer, Integer>> compareUsingAceMinValue = (o, t1) -> o._2().compareTo(t1._2());

        Collections.sort(rawRanksList, compareUsingAceMaxValue);
        Collections.sort(copyRanksList, compareUsingAceMinValue);
        return (rawRanksList.get(4)._1 - rawRanksList.get(0)._1 == 4 || copyRanksList.get(4)._2 - copyRanksList.get(0)._2 == 4);
    }


    public boolean handContainsFlush(Hand targetHand) {
        return handConformsToCriterias(Card::getSuit,
                (tuple2)-> (tuple2._2.size()==5 ),
                ()-> 1,
                targetHand
        );
    }

    public boolean handContainsRoyalFlush(Hand targetHand) {
        boolean  handContainsAce = targetHand.getCards().get().filter(c-> c.getRank()==Rank.ACE).size()==1;
        return  handContainsStraightFlush(targetHand) && handContainsAce;
    }

    public boolean contains2Pairs(Hand targetHand) {
        return findPairs.apply( ()-> 2,
                targetHand);
    }




    private static boolean handConformsToCriterias(
                                            Function<Card,Criteria> groupingFunction,
                                            Predicate<Tuple2<Criteria, List<Card>>> predicate,
                                            Function0<Integer> sizeCriteria,
                                            Hand targetHand

                                            ) {
        return targetHand.getCards().get()
                .groupBy(groupingFunction)
                .filter(predicate)
                .size()==sizeCriteria.apply()
                ;

    }
}
