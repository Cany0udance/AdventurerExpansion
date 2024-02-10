package adventurerexpansion.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import theFishing.cards.AbstractFishingCard;
import theFishing.util.Wiz;

import static adventurerexpansion.AdventurerExpansion.makeID;
import static theFishing.util.Wiz.atb;

public class TheyAteMyFish extends AbstractFishingCard {
    public static final String ID = makeID("TheyAteMyFish");
    private static final String IMG = "adventurerexpansion/images/cards/attack/TheyAteMyFish.png";
    private static final String IMG_P = "adventurerexpansion/images/cards/attack/TheyAteMyFish_p.png";

    public TheyAteMyFish() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 14; // Base damage, upgraded to 19
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true; // Card exhausts after use
        loadCardImage(IMG);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(IMG_P);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage to the target
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        // Check if the target is Gremlin Nob or Angry Gremlin
        if (m.id.equals(GremlinNob.ID) || m.id.equals(GremlinWarrior.ID)) {
            CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
            AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
            atb(new TalkAction(true, "freakin jerk!!!", 0.5F, 2F));
            // Apply 1 Anger power to the player
            Wiz.applyToSelf(new AngerPower(p, magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(5); // Increase damage from 14 to 19
        upgradeMagicNumber(1); // Increase damage from 14 to 19
        this.initializeDescription();
    }
}
