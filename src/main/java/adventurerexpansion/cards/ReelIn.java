package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.actions.EnterTheDungeonAction;
import theFishing.boards.AbstractBoard;
import theFishing.cards.AbstractFishingCard;
import theFishing.cards.fish.AbstractFishCard;

import java.util.ArrayList;
import java.util.Collections;

import static adventurerexpansion.AdventurerExpansion.makeID;
import static theFishing.util.Wiz.atb;

public class ReelIn extends AbstractFishingCard {
    public static final String ID = makeID("ReelIn");
    private static final String IMG = "adventurerexpansion/images/cards/skill/ReelIn.png";
    private static final String UPGRADED_IMG = "adventurerexpansion/images/cards/skill/ReelInUpg.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/ReelIn_p.png";
    private static final String UPGRADED_IMG_P = "adventurerexpansion/images/cards/skill/ReelInUpg_p.png";


    public ReelIn() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        loadCardImage(IMG);
        AbstractBoard.postInitDelveState(this);
    }

    @Override
    protected Texture getPortraitImage() {
        // Override to provide the correct portrait image based on the upgrade state
        if (upgraded) {
            return ImageMaster.loadImage(UPGRADED_IMG_P);
        } else {
            return ImageMaster.loadImage(IMG_P);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> fishInDrawPile = new ArrayList<>();
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof AbstractFishCard) {
                fishInDrawPile.add(c);
            }
        }

        if (!fishInDrawPile.isEmpty()) {
            Collections.shuffle(fishInDrawPile, AbstractDungeon.cardRandomRng.random);
            int fishToReelIn = upgraded ? 2 : this.magicNumber;
            for (int i = 0; i < Math.min(fishToReelIn, fishInDrawPile.size()); i++) {
                AbstractCard fish = fishInDrawPile.get(i);
                p.drawPile.moveToHand(fish, p.drawPile);
                fish.upgrade();
            }
        }
        atb(new EnterTheDungeonAction());
    }

    public void upp() {
        upgradeMagicNumber(1); // Increases the number of fish to reel in and upgrade when upgraded
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
        loadCardImage(UPGRADED_IMG);
    }
}