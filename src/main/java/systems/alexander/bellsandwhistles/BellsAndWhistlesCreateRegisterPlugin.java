package systems.alexander.bellsandwhistles;

import com.zurrtum.create.api.registry.CreateRegisterPlugin;
import systems.alexander.bellsandwhistles.block.ModBlocks;

public final class BellsAndWhistlesCreateRegisterPlugin implements CreateRegisterPlugin {
    private static boolean blocksRegistered;

    @Override
    public void onBlockRegister() {
        if (blocksRegistered) {
            throw new IllegalStateException("Create Fly invoked Bells & Whistles block registration more than once");
        }
        ModBlocks.register();
        blocksRegistered = true;
    }

    public static void verifyEarlyRegistrationComplete() {
        if (!blocksRegistered) {
            throw new IllegalStateException("Create Fly did not invoke Bells & Whistles early block registration");
        }
    }
}
