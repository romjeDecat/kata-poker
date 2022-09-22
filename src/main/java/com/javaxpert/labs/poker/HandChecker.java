package com.javaxpert.labs.poker;

import io.vavr.Function0;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HandChecker {


    public boolean containsPair(Hand targetHand) {
        return handConformsToCriterias(targetHand,
                Card::getRank,
                (tuple2)-> (tuple2._2.size()==2 ),
                ()-> 1
                );
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
        return handConformsToCriterias(targetHand,
                Card::getRank,
                (tuple2)-> (tuple2._2.size()==3 ),
                ()-> 1
        );


    }

    public boolean containsFourOfAKind(Hand targetHand) {
        return handConformsToCriterias(targetHand,
                Card::getRank,
                (tuple2)-> (tuple2._2.size()==4 ),
                ()-> 1)
        ;
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
        //Array<Integer> ranksWithAceMax = ranksList.sortBy(compareUsingAceMaxValue,integerIntegerTuple2 -> integerIntegerTuple2._1);
        // Array<Integer> ranksWithAceMin = ranksList.sortBy((o, t1) -> o._2.compareTo(t1._2()));
        //return (ranksWithAceMax.get(4)-ranksWithAceMax.get(0)==4 || ranksWithAceMin.get(4)-ranksWithAceMin.get(0)==4 );
        return (rawRanksList.get(4)._1 - rawRanksList.get(0)._1 == 4 || copyRanksList.get(4)._2 - copyRanksList.get(0)._2 == 4);
    }


    public boolean handContainsFlush(Hand targetHand) {
        return handConformsToCriterias(targetHand,
                Card::getSuit,
                (tuple2)-> (tuple2._2.size()==5 ),
                ()-> 1
        );
    }

    public boolean handContainsRoyalFlush(Hand targetHand) {
        boolean  handContainsAce = targetHand.getCards().get().filter(c-> c.getRank()==Rank.ACE).size()==1;
        return  handContainsStraightFlush(targetHand) && handContainsAce;
    }

    public boolean contains2Pairs(Hand targetHand) {
        return handConformsToCriterias(targetHand,
                Card::getRank,
                (tuple2)-> (tuple2._2.size()==2 ),
                ()-> 2);
    }



    /**
     * most generic way to solve the problem using a functional style
     * @param targetHand, cards to check
     * @param groupingFunction, should we use rank or suit to group cards
     * @param predicate condition for the filtering
     * @param sizeCriteria size of packets of cards
     * @return boolean, true if handss contains cards passing thy filtering
     */
    private boolean handConformsToCriterias(Hand targetHand,
                                            Function<Card,Criteria> groupingFunction,
                                            Predicate<Tuple2<Criteria, List<Card>>> predicate,
                                            Function0<Integer> sizeCriteria){
        return targetHand.getCards().get()
                .groupBy(groupingFunction)
                .filter(predicate)
                .size()==sizeCriteria.apply()
                ;

    }
}
