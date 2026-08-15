package systems.alexander.bellsandwhistles.block.custom;

import com.zurrtum.create.content.decoration.MetalLadderBlock;
import com.zurrtum.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class MetalGrabRailsBlock extends MetalLadderBlock implements IWrenchable {
    public MetalGrabRailsBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState pState) {
        return true;
    }
}
