package adventurerexpansion.boards.dailies;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import theFishing.FishingMod;
import theFishing.boards.AbstractBoard;

import static theFishing.util.Wiz.att;

public class JudgmentDay extends AbstractBoard {
    public static final String ID = FishingMod.makeID(JudgmentDay.class.getSimpleName());

    public JudgmentDay() {
        super(ID);
    }

    @Override
    public void effect() {
        if (AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
            // If the player is in Calm, switch to Wrath
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(WrathStance.STANCE_ID));
        } else if (AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID)) {
            // If the player is in Wrath, switch to Calm
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(CalmStance.STANCE_ID));
        } else {
            // If the player is not in any stance, enter Calm
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(CalmStance.STANCE_ID));
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        att(new ChangeStanceAction(CalmStance.STANCE_ID));
    }

    public static void initializeBoard() {
        AbstractBoard.ids.put(JudgmentDay.ID, JudgmentDay.class);
        AbstractBoard.idsList.add(JudgmentDay.ID);
    }

}