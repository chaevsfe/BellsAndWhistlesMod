package systems.alexander.bellsandwhistles.block.custom;

import com.zurrtum.create.AllShapes;
import com.zurrtum.create.catnip.placement.IPlacementHelper;
import com.zurrtum.create.catnip.placement.PlacementHelpers;
import com.zurrtum.create.catnip.placement.PlacementOffset;
import com.zurrtum.create.content.decoration.copycat.CopycatPanelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import systems.alexander.bellsandwhistles.block.ModBlocks;

import java.util.List;
import java.util.function.Predicate;

public class PanelBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    protected static final int AABB_THICKNESS = 3;

    private static final int placementHelperId = PlacementHelpers.register(new PlacementHelper());

    public PanelBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP));
    }

    public static BlockState getMaterial(BlockGetter reader, BlockPos targetPos) {
        return Blocks.MOSSY_COBBLESTONE.defaultBlockState();
    }

    public boolean canFaceBeOccluded(BlockState state, Direction face) {
        return state.getValue(FACING)
                .getOpposite() == face;
    }

    public boolean shouldFaceAlwaysRender(BlockState state, Direction face) {
        return canFaceBeOccluded(state, face.getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState stateForPlacement = super.getStateForPlacement(pContext);
        return stateForPlacement.setValue(FACING, pContext.getNearestLookingDirection()
                .getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isShiftKeyDown() || !player.mayBuild()) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        IPlacementHelper helper = PlacementHelpers.get(placementHelperId);
        if (helper.matchesItem(stack) && helper.matchesState(state)) {
            return helper.getOffset(player, level, state, pos, hitResult)
                    .placeInWorld(level, (BlockItem) stack.getItem(), player, hand);
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    public static boolean isOccluded(BlockState state, BlockState other, Direction pDirection) {
        Direction facing = state.getValue(FACING);
        if (facing.getOpposite() == other.getValue(FACING) && pDirection == facing)
            return true;
        if (other.getValue(FACING) != facing)
            return false;
        return pDirection.getAxis() != facing.getAxis();
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.CASING_3PX.get(pState.getValue(FACING));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    private static class PlacementHelper implements IPlacementHelper {
        private PlacementHelper() {
        }

        public Predicate<ItemStack> getItemPredicate() {
            return (itemStack) -> itemStack.getItem() == ModBlocks.METRO_PANEL.asItem();
        }

        public Predicate<BlockState> getStatePredicate() {
            return (blockState) -> blockState.is(ModBlocks.METRO_PANEL);
        }

        public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(), state.getValue(CopycatPanelBlock.FACING).getAxis(), (dir) -> world.getBlockState(pos.relative(dir)).canBeReplaced());
            return directions.isEmpty() ? PlacementOffset.fail() : PlacementOffset.success(pos.relative(directions.get(0)), (s) -> s.setValue(CopycatPanelBlock.FACING, state.getValue(CopycatPanelBlock.FACING)));
        }
    }
}
