package adventurerexpansion.potions;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theFishing.FishingMod;
import theFishing.actions.AbandonQuestAction;
import theFishing.quest.QuestHelper;
import theFishing.quest.quests.AbstractQuest;
import theFishing.util.Wiz;

import static adventurerexpansion.AdventurerExpansion.makeID;

public class MessageInABottle extends AbstractPotion {
    public static final String POTION_ID = makeID("MessageInABottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public MessageInABottle() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.T, PotionColor.BLUE);
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        labOutlineColor = FishingMod.characterColor;
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        if (potency == 1) {
            description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        } else {
            description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[2];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        if (!QuestHelper.quests.isEmpty()) {
            Wiz.atb(new AbstractGameAction() {
                public void update() {
                    this.isDone = true;
                    QuestHelper.playCompleteQuestSfx();

                    for(int i = QuestHelper.quests.size() - 1; i >= 0; --i) {
                        AbstractQuest next = (AbstractQuest)QuestHelper.quests.get(i);
                        Wiz.att(new AbandonQuestAction(next));
                        next.grantRewardTop();
                    }

                }
            });
        }
        for (int i = 0; i < potency; i++) {
            AbstractCard cardToAdd = CardLibrary.getAnyColorCard(AbstractCard.CardRarity.UNCOMMON);
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(cardToAdd.makeCopy()));
            } else {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(cardToAdd.makeCopy()));
            }
        }
    }

    @Override
    public int getPotency(int ascensionlevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MessageInABottle();
    }
}