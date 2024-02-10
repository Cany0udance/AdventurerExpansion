package adventurerexpansion.relics;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import theFishing.TheFishing;
import theFishing.patch.foil.FoilPatches;
import theFishing.relics.AbstractAdventurerRelic;
import theFishing.util.TexLoader;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class EelSkin extends AbstractAdventurerRelic {
    public static final String ID = makeID(EelSkin.class.getSimpleName());
    private static final String IMG = "adventurerexpansion/images/relics/EelSkin.png";
    private static final String OUTLINE_IMG = "adventurerexpansion/images/relics/EelSkinOutline.png";
    public EelSkin() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, TheFishing.Enums.FISHING_COLOR);
        this.img = TexLoader.getTexture(IMG);
        this.outlineImg = TexLoader.getTexture(OUTLINE_IMG);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (FoilPatches.isFoil(card)) {
            // Whenever a Foil card is played, channel 1 Lightning.
            this.flash(); // Visual feedback for relic activation.
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));
        }
    }

    public void onEquip() {
        if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT && AbstractDungeon.player.masterMaxOrbs == 0) {
            AbstractDungeon.player.masterMaxOrbs = 1;
        }

    }
}
