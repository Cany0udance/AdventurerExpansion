package adventurerexpansion.patches;


import adventurerexpansion.boards.dailies.FeedingFrenzy;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import theFishing.FishingMod;

@SpirePatch(
        clz = FishingMod.class, // Target the main class of the Adventurer mod
        method = "receivePostInitialize" // Target the receivePostInitialize method
)
public class FishingModPatch {

    @SpirePrefixPatch // This makes our method run before the targeted method
    public static void Prefix(FishingMod _instance) {
        // Your code to register the FeedingFrenzy Delve bonus
        FeedingFrenzy.initializeBoard();
    }
}