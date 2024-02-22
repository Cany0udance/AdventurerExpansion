package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.cards.AbstractFishingCard;
import theFishing.patch.foil.FoilPatches;
import theFishing.util.Wiz;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class AmogusEgg extends AbstractFishingCard {
    public static final String ID = makeID("AmogusEgg");
    private static final String IMG = "adventurerexpansion/images/cards/skill/AmogusEgg.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/AmogusEgg_p.png";

    public AmogusEgg() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        AbstractCard c = new Amogus();
        FoilPatches.makeFoil(c);
        this.cardsToPreview = c;
        loadCardImage(IMG);
        isEthereal = true;
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnExhaust() {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        AbstractCard c = new Amogus();
        if (this.upgraded) {
            c.upgrade();
        }

        FoilPatches.makeFoil(c);
        Wiz.makeInHandTop(c);
    }

    @Override
    public void upp() {
        AbstractCard c = new Amogus();
        c.upgrade();
        FoilPatches.makeFoil(c);
        this.cardsToPreview = c;
        this.uDesc();
    }
}
