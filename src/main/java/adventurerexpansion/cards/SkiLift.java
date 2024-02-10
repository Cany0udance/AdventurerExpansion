package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.cards.AbstractFishingCard;
import theFishing.util.Wiz;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class SkiLift extends AbstractFishingCard {
    public static final String ID = makeID("SkiLift");
    private static final String IMG = "adventurerexpansion/images/cards/skill/SkiLift.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/SkiLift_p.png";

    public SkiLift() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 3; // 3 cards to be put into hand from discard
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
       return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hand.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(
                    new DiscardAction(p, p, p.hand.size(), false)
            );
        }

        for(int i = 0; i < this.magicNumber; ++i) {
            Wiz.atb(new RandomCardFromDiscardPileToHandAction());
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1); // Now puts 5 cards into the hand from discard when upgraded
        this.initializeDescription();
    }
}
