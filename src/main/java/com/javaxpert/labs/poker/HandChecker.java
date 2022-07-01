package com.javaxpert.labs.poker;

import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HandChecker {

    private boolean handContainsNCardsWithSameRank(Hand targetHand, int thresholdValue) {
        return targetHand.getCards().get().
                groupBy(card -> card.getRank())
                .filter((value, cards) -> cards.size() == thresholdValue).size() > 0;
    }

    public boolean containsPair(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 2);
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 3);
    }

    public boolean containsFourOfAKind(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 4);
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
        return targetHand.getCards().get().
                groupBy(card ->card.getSuit())
                .filter((suit, cards) ->cards.size()==5)
                .size()==1;
    }

    public boolean handContainsRoyalFlush(Hand targetHand) {
        boolean  handContainsAce = targetHand.getCards().get().filter(c-> c.getRank()==Rank.ACE).size()==1;
        return  handContainsStraightFlush(targetHand) && handContainsAce;
    }

    public boolean contains2Pairs(Hand targetHand) {
        Function3<Integer,Integer,Hand,Boolean> generalFunction =
                Function3.of(this::handContainsAtLeastNOccurencesOfTheSameRankGroup);
        return generalFunction.apply(2,2,targetHand);
    }

    private boolean handContainsAtLeastNOccurencesOfTheSameRankGroup(int numOccurences,int groupSize,Hand targetHand){
        return targetHand.getCards().get()
                .groupBy(card -> card.getRank())
                .filter(((suit, cards) -> cards.size()==groupSize)).size()==numOccurences;
    }

    public boolean genericContains2Pairs(Hand targetHand){
        return  handGroupingBySize(targetHand,(card)-> card.getRank(),(tuple2)-> (tuple2._2.size()==2 ));
    }


    private boolean handGroupingBySize(Hand targetHand, Function<Card,Criteria> groupingFunction,
                                       Predicate<Tuple2<Criteria, List<Card>>> predicate){
        return targetHand.getCards().get()
                .groupBy(groupingFunction)
                .filter((card) ->predicate.test(card))
                .size()==2;

    }
}
