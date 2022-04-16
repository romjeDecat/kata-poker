package com.javaxpert.labs.poker;


import static org.junit.jupiter.api.Assertions.*;


import static io.vavr.API.*;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;




public class HandCheckerTest {
  private static Hand typicalHand;
  private static Hand incompleteHand;
  private static Hand oversizedHand;
  private static Hand dummyHand;
  private static Hand handWithPair;
  private static Hand handWithThreeOfAKind;
  private static Hand handWithFourOfAKind;
  private static Hand handWithFlush;
  private static Hand handWithRoyalFlush;
  private static Hand handWithStraight;
  private HandChecker checker;

  @BeforeAll
  static void setupHands(){
    typicalHand=new Hand();
    typicalHand.addCard(new Card(Suit.SPADE, Rank.KING));
    typicalHand.addCard(new Card(Suit.SPADE, Rank.TEN));
    typicalHand.addCard(new Card(Suit.SPADE, Rank.TWO));
    typicalHand.addCard(new Card(Suit.CLUB, Rank.KING));
    typicalHand.addCard(new Card(Suit.DIAMOND, Rank.EIGHT));

    incompleteHand=new Hand();
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.ACE));
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.TWO));
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.TEN));

    oversizedHand = new Hand();
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.KING));
    oversizedHand.addCard(new Card(Suit.CLUB, Rank.KING));
    oversizedHand.addCard(new Card(Suit.DIAMOND, Rank.KING));
    oversizedHand.addCard(new Card(Suit.HEART, Rank.KING));
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.TEN));
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.JACK));

    dummyHand  = new Hand();
    dummyHand.addCard(new Card(Suit.CLUB,Rank.ACE));
    dummyHand.addCard(new Card(Suit.CLUB,Rank.TWO));
    dummyHand.addCard(new Card(Suit.CLUB,Rank.TEN));
    dummyHand.addCard(new Card(Suit.DIAMOND,Rank.THREE));
    dummyHand.addCard(new Card(Suit.DIAMOND,Rank.FOUR));

    handWithPair = new Hand();
    handWithPair.addCard(new Card(Suit.CLUB, Rank.JACK));
    handWithPair.addCard(new Card(Suit.DIAMOND, Rank.JACK));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.TEN));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.NINE));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.ACE));

    handWithThreeOfAKind = new Hand();
    handWithThreeOfAKind.addCard(new Card(Suit.CLUB,Rank.ACE));
    handWithThreeOfAKind.addCard(new Card(Suit.SPADE,Rank.ACE));
    handWithThreeOfAKind.addCard(new Card(Suit.DIAMOND,Rank.ACE));
    handWithThreeOfAKind.addCard(new Card(Suit.HEART,Rank.TWO));
    handWithThreeOfAKind.addCard(new Card(Suit.HEART,Rank.THREE));

    handWithFourOfAKind = new Hand();
    handWithFourOfAKind.addCard(new Card(Suit.CLUB,Rank.KING));
    handWithFourOfAKind.addCard(new Card(Suit.DIAMOND,Rank.KING));
    handWithFourOfAKind.addCard(new Card(Suit.HEART,Rank.KING));
    handWithFourOfAKind.addCard(new Card(Suit.SPADE,Rank.KING));
    handWithFourOfAKind.addCard(new Card(Suit.CLUB,Rank.TEN));

    handWithFlush =new Hand();
    handWithFlush.addCard(new Card(Suit.CLUB,Rank.NINE));
    handWithFlush.addCard(new Card(Suit.CLUB,Rank.TEN));
    handWithFlush.addCard(new Card(Suit.CLUB,Rank.JACK));
    handWithFlush.addCard(new Card(Suit.CLUB,Rank.QUEEN));
    handWithFlush.addCard(new Card(Suit.CLUB,Rank.KING));

    handWithRoyalFlush=new Hand();
    handWithRoyalFlush.addCard(new Card(Suit.CLUB,Rank.ACE));
    handWithRoyalFlush.addCard(new Card(Suit.CLUB,Rank.KING));
    handWithRoyalFlush.addCard(new Card(Suit.CLUB,Rank.QUEEN));
    handWithRoyalFlush.addCard(new Card(Suit.CLUB,Rank.JACK));
    handWithRoyalFlush.addCard(new Card(Suit.CLUB,Rank.TEN));

    handWithStraight=new Hand();
    handWithStraight.addCard(new Card(Suit.CLUB,Rank.SEVEN));
    handWithStraight.addCard(new Card(Suit.CLUB,Rank.SIX));
    handWithStraight.addCard(new Card(Suit.SPADE,Rank.EIGHT));
    handWithStraight.addCard(new Card(Suit.HEART,Rank.NINE));
    handWithStraight.addCard(new Card(Suit.HEART,Rank.TEN));
  }

  @BeforeEach
  void setupBeforeTest(){
    checker=new HandChecker();
  }


  @Test
  void handShouldContainsExactly5Cards(){
    assertTrue(typicalHand.isComplete());
  assertFalse(incompleteHand.isComplete());
    assertFalse(oversizedHand.isComplete());
  }


  @Test
  void onlyCompleteHandCanBeQueried(){
    assertEquals(incompleteHand.getCards(), Either.left("invalid hand"));
   assertEquals(typicalHand.getCards().get().size(),5);
  }


  @Test
  void shouldDetectOnePair(){
    assertTrue(checker.containsPair(handWithPair));
    assertFalse(checker.containsPair(dummyHand));
  }

  @Test
  void shouldDetectThreeOfAKind(){
    assertTrue(checker.containsThreeOfAKind(handWithThreeOfAKind));
    assertFalse(checker.containsThreeOfAKind(dummyHand));
  }

  @Test
  void shouldDetectFourOfAKind(){
    assertTrue(checker.containsFourOfAKind(handWithFourOfAKind));
    assertFalse(checker.containsFourOfAKind(dummyHand));
  }

  @Test
  void shouldDetectFlush(){
    assertTrue(checker.handContainsFlush(handWithFlush));
    assertTrue(checker.handContainsFlush(handWithRoyalFlush));
    assertFalse(checker.handContainsFlush(dummyHand));
  }

  @Test
  void shouldDetectStraight(){
  assertTrue(checker.handContainsStraight(handWithStraight));
  assertFalse(checker.handContainsStraight(dummyHand));
  }

  @Test
  void shouldDetectStraightFlush(){
    assertTrue(checker.handContainsStraightFlush(handWithRoyalFlush));
    assertFalse(checker.handContainsStraightFlush(dummyHand));
  }

  @Test
  void royalFlushShouldBeDetected(){
    assertTrue(checker.handContainsRoyalFlush(handWithRoyalFlush));
    //assertFalse(ch);
  }



}
