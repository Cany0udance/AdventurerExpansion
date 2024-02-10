package adventurerexpansion.boards.dailies;

import theFishing.FishingMod;
import theFishing.boards.AbstractBoard;
import theFishing.cards.fish.AbstractFishCard;

import static theFishing.util.Wiz.makeInHand;
import static theFishing.util.Wiz.makeInHandTop;

public class FeedingFrenzy extends AbstractBoard {
    public static final String ID = FishingMod.makeID(FeedingFrenzy.class.getSimpleName());

    public FeedingFrenzy() {
        super(ID);
    }


    @Override
    public void effect() {
        makeInHand(AbstractFishCard.returnRandomFish());
    }

    public static void initializeBoard() {
        AbstractBoard.ids.put(FeedingFrenzy.ID, FeedingFrenzy.class);
        AbstractBoard.idsList.add(FeedingFrenzy.ID);
    }

}
