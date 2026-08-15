package systems.alexander.bellsandwhistles.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModItems {

    public static void registerBlockItem(Identifier id, Block block) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        BlockItem item = new BlockItem(block, new Item.Properties().setId(key).useBlockDescriptionPrefix());
        Registry.register(BuiltInRegistries.ITEM, id, item);
        item.registerBlocks(Item.BY_BLOCK, item);
    }

    public static void register() {
    }
}
