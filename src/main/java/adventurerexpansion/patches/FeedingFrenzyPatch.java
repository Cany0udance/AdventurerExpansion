package adventurerexpansion.patches;

import adventurerexpansion.boards.dailies.FeedingFrenzy;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.FishingMod;
import theFishing.cards.fish.AbstractFishCard;

@SpirePatch(
        clz = AbstractFishCard.class, // Target class
        method = "use" // Target method
)
public class FeedingFrenzyPatch {

    @SpireInsertPatch(
            loc = 0 // Insert at the start of the method
    )
    public static void Insert(AbstractFishCard _instance, AbstractPlayer p, AbstractMonster m) {
        // Check if the Feeding Frenzy Delve is active
        if (FishingMod.activeBoard != null && AbstractDungeon.isPlayerInDungeon() && FishingMod.activeBoard.id.equals(FeedingFrenzy.ID)) {
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, 1));
        }
    }
}