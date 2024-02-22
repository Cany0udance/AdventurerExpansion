package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.FishingMod;
import theFishing.cards.AbstractFishingCard;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class Amogus extends AbstractFishingCard {
    public static final String ID = makeID("Amogus");
    private static final String IMG = "adventurerexpansion/images/cards/attack/Amogus.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/attack/Amogus_p.png";

    public Amogus() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        this.baseDamage = 15;
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.dmg(m, AbstractGameAction.AttackEffect.POISON);
        this.dmg(m, AbstractGameAction.AttackEffect.POISON);
    }

    public void upp() {
        this.upgradeDamage(5);
    }
}