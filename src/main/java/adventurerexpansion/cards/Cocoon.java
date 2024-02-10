package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theFishing.cards.AbstractFishingCard;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class Cocoon extends AbstractFishingCard {
    public static final String ID = makeID("Cocoon");
    private static final String IMG = "adventurerexpansion/images/cards/skill/Cocoon.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/skill/Cocoon_p.png";

    public Cocoon() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 11; // Base block, upgraded to 15
        this.exhaust = true; // Card exhausts after use
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Gain block
        this.addToBot(new GainBlockAction(p, p, this.block));

        // Convert Plated Armor to Metallicize
        if (p.hasPower(PlatedArmorPower.POWER_ID)) {
            int platedArmorAmount = p.getPower(PlatedArmorPower.POWER_ID).amount;
            this.addToBot(new RemoveSpecificPowerAction(p, p, PlatedArmorPower.POWER_ID));

            // Add Metallicize power equivalent to the amount of lost Plated Armor
            this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, platedArmorAmount), platedArmorAmount));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(4); // Upgrade block from 11 to 15
    }
}
