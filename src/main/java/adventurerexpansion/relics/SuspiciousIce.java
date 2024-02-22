package adventurerexpansion.relics;

import adventurerexpansion.cards.AmogusEgg;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import theFishing.TheFishing;
import theFishing.cards.GlogusEgg;
import theFishing.patch.foil.FoilPatches;
import theFishing.relics.AbstractAdventurerRelic;
import theFishing.util.TexLoader;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class SuspiciousIce extends AbstractAdventurerRelic {
    public static final String ID = makeID(SuspiciousIce.class.getSimpleName());
    private static final String IMG = "adventurerexpansion/images/relics/SuspiciousIce.png";
    private static final String OUTLINE_IMG = "adventurerexpansion/images/relics/SuspiciousIceOutline.png";
    public SuspiciousIce() {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL, TheFishing.Enums.FISHING_COLOR);
        this.img = TexLoader.getTexture(IMG);
        this.outlineImg = TexLoader.getTexture(OUTLINE_IMG);
    }

    @Override
    public void onEquip() {
        super.onEquip(); // Call to the superclass's method if necessary

        for (int i = 0; i < AbstractDungeon.player.masterDeck.size(); i++) {
            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
            if (card.cardID.equals(GlogusEgg.ID)) {
                AbstractCard amogusEgg = new AmogusEgg(); // Assuming AmogusEgg is a valid card class
                AbstractDungeon.player.masterDeck.group.set(i, amogusEgg);
            }
        }
    }

}