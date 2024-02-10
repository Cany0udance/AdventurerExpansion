package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theFishing.cards.AbstractFishingCard;
import theFishing.patch.foil.FoilPatches;
import theFishing.util.Wiz;

import java.util.ArrayList;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class Aluminum extends AbstractFishingCard {
    public static final String ID = makeID("Aluminum");
    private static final String IMG = "adventurerexpansion/images/cards/skill/Aluminum.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/Aluminum_p.png";

    public Aluminum() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 2; // 2 Plated Armor, upgraded to 4
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PlatedArmorPower(p, this.magicNumber));

        for (AbstractCard card : new ArrayList<>(p.hand.group)) {
            if (FoilPatches.isFoil(card)) {
                AbstractMonster randomTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (card.target == AbstractCard.CardTarget.ENEMY && randomTarget != null) {
                    card.calculateCardDamage(randomTarget); // Optional, calculate damage if the card does damage based on target's stats
                    card.use(p, randomTarget);
                } else {
                    card.use(p, null); // Use the card without a specific target if not targeting enemies
                }
                // Check if the card should exhaust
                if (card.exhaust || card.exhaustOnUseOnce) {
                    p.hand.moveToExhaustPile(card); // Move the used Foil card to the exhaust pile
                } else {
                    p.hand.moveToDiscardPile(card); // Move the used Foil card to the discard pile if it does not exhaust
                }
                card.exhaustOnUseOnce = false; // Reset the exhaust flag if set
            }
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2); // Upgrade to 4 Plated Armor
        this.initializeDescription();
    }
}
