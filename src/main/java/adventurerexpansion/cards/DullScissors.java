package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theFishing.cards.AbstractFishingCard;
import theFishing.patch.foil.FoilPatches;

import java.util.ArrayList;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class DullScissors extends AbstractFishingCard implements OnObtainCard {
    public static final String ID = makeID("DullScissors");
    private static final String IMG = "adventurerexpansion/images/cards/attack/DullScissors.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/attack/DullScissors_p.png";

    public DullScissors() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 3; // Base damage, upgraded to 4
        this.isMultiDamage = true; // Needed for attacking all enemies
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            int damageAmount = this.damage;
            if (mo.hasPower(WeakPower.POWER_ID)) {
                damageAmount *= this.upgraded ? 3 : 2; // Triple damage if upgraded, otherwise double
            }
            this.addToBot(new DamageAction(mo, new DamageInfo(p, damageAmount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            this.addToBot(new DamageAction(mo, new DamageInfo(p, damageAmount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> foilCards = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (FoilPatches.isFoil(card)) {
                foilCards.add(card);
            }
        }
        if (!foilCards.isEmpty()) {
            AbstractCard cardToUpgrade = foilCards.get(AbstractDungeon.cardRandomRng.random(foilCards.size() - 1));

            // Use AbstractDungeon.actionManager to schedule the upgrade action
            if (!cardToUpgrade.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        cardToUpgrade.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(cardToUpgrade.makeStatEquivalentCopy()));
                        this.isDone = true;
                    }
                });
            }
        }
    }


    @Override
    public void upp() {
        upgradeDamage(1); // Increase damage from 3 to 4
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
}
