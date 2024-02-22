package adventurerexpansion.patches;

import adventurerexpansion.cards.AmogusEgg;
import adventurerexpansion.relics.SuspiciousIce;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFishing.cards.GlogusEgg;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)
public class ReplaceGlogusEggInRewardsPatch {

    @SpirePostfixPatch
    public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result) {
        // Check if the player has the Suspicious Ice relic
        if (AbstractDungeon.player.hasRelic(SuspiciousIce.ID)) {
            for (int i = 0; i < __result.size(); i++) {
                AbstractCard card = __result.get(i);
                if (card.cardID.equals(GlogusEgg.ID)) {
                    __result.set(i, new AmogusEgg()); // Replace Glogus Egg with Amogus Egg

                }
            }
        }
        return __result;
    }
}

