package systems.alexander.bellsandwhistles;

import net.fabricmc.api.ModInitializer;
import systems.alexander.bellsandwhistles.item.ModCreativeModeTabs;

public class BellsAndWhistles implements ModInitializer {
    public static final String MOD_ID = "bellsandwhistles";

    @Override
    public void onInitialize() {
        BellsAndWhistlesCreateRegisterPlugin.verifyEarlyRegistrationComplete();
        ModCreativeModeTabs.register();
    }
}
