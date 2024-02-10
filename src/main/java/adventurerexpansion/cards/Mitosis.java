package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.cards.AbstractFishingCard;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class Mitosis extends AbstractFishingCard implements OnObtainCard {
    public static final String ID = makeID("Mitosis");
    private static final String IMG = "adventurerexpansion/images/cards/skill/Mitosis.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/Mitosis_p.png";

    public Mitosis() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Duplicate a random colorless card in hand
        ArrayList<AbstractCard> colorlessCardsInHand = p.hand.group.stream()
                .filter(card -> card.color == AbstractCard.CardColor.COLORLESS)
                .collect(Collectors.toCollection(ArrayList::new));

        if (!colorlessCardsInHand.isEmpty()) {
            AbstractCard cardToCopy = colorlessCardsInHand.get(AbstractDungeon.cardRandomRng.random(colorlessCardsInHand.size() - 1));
            AbstractCard copy = cardToCopy.makeStatEquivalentCopy();
            this.addToTop(new MakeTempCardInHandAction(copy.makeStatEquivalentCopy()));
            if (upgraded) {
                AbstractCard secondCopy = cardToCopy.makeStatEquivalentCopy();
                this.addToTop(new MakeTempCardInHandAction(secondCopy.makeStatEquivalentCopy()));
            }
            p.hand.refreshHandLayout();
            p.hand.applyPowers();
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> colorlessCardsInDeck = AbstractDungeon.player.masterDeck.group.stream()
                .filter(card -> card.color == AbstractCard.CardColor.COLORLESS)
                .collect(Collectors.toCollection(ArrayList::new));

        if (!colorlessCardsInDeck.isEmpty()) {
            AbstractCard cardToDuplicate = colorlessCardsInDeck.get(AbstractDungeon.cardRandomRng.random(colorlessCardsInDeck.size() - 1));
            AbstractCard firstDuplicate = cardToDuplicate.makeStatEquivalentCopy();

            // Add the first duplicate to the deck and show it on screen
            AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(firstDuplicate));

            if (this.upgraded) { // Check if Mitosis is upgraded
                AbstractCard secondDuplicate = cardToDuplicate.makeStatEquivalentCopy();
                // Add the second duplicate to the deck and show it on screen
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(secondDuplicate));
            }
        }
    }



    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
}
