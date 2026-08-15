package systems.alexander.bellsandwhistles.item;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import systems.alexander.bellsandwhistles.BellsAndWhistles;
import systems.alexander.bellsandwhistles.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final ResourceKey<CreativeModeTab> BELLS_AND_WHISTLES_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(BellsAndWhistles.MOD_ID, "bells_and_whistles_tab"));

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BELLS_AND_WHISTLES_TAB,
                FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.METAL_PILOT))
                        .title(Component.translatable("creativetab.bellsandwhistles.bells_and_whistles"))
                        .displayItems((itemDisplayParameters, output) -> {
                            output.accept(ModBlocks.ANDESITE_BOGIE_STEPS);
                            output.accept(ModBlocks.BRASS_BOGIE_STEPS);
                            output.accept(ModBlocks.COPPER_BOGIE_STEPS);

                            output.accept(ModBlocks.ANDESITE_GRAB_RAILS);
                            output.accept(ModBlocks.BRASS_GRAB_RAILS);
                            output.accept(ModBlocks.COPPER_GRAB_RAILS);

                            output.accept(ModBlocks.ANDESITE_DOOR_STEP);
                            output.accept(ModBlocks.BRASS_DOOR_STEP);
                            output.accept(ModBlocks.COPPER_DOOR_STEP);
                            output.accept(ModBlocks.ORNATE_IRON_TRAPDOOR);
                            output.accept(ModBlocks.HEADLIGHT);
                            output.accept(ModBlocks.STATION_PLATFORM);
                            output.accept(ModBlocks.METRO_CASING);
                            output.accept(ModBlocks.CORRUGATED_METRO_CASING);
                            output.accept(ModBlocks.METRO_PANEL);
                            output.accept(ModBlocks.CORRUGATED_METRO_PANEL);
                            output.accept(ModBlocks.METRO_TRAPDOOR);
                            output.accept(ModBlocks.METRO_WINDOW);

                            output.accept(ModBlocks.ANDESITE_PILOT);
                            output.accept(ModBlocks.BRASS_PILOT);
                            output.accept(ModBlocks.COPPER_PILOT);
                            output.accept(ModBlocks.METAL_PILOT);
                            output.accept(ModBlocks.POLISHED_ANDESITE_PILOT);
                            output.accept(ModBlocks.POLISHED_GRANITE_PILOT);
                            output.accept(ModBlocks.POLISHED_DIORITE_PILOT);
                            output.accept(ModBlocks.POLISHED_DEEPSLATE_PILOT);
                            output.accept(ModBlocks.POLISHED_DRIPSTONE_PILOT);
                            output.accept(ModBlocks.POLISHED_TUFF_PILOT);
                            output.accept(ModBlocks.POLISHED_CALCITE_PILOT);
                            output.accept(ModBlocks.POLISHED_LIMESTONE_PILOT);
                            output.accept(ModBlocks.POLISHED_SCORIA_PILOT);
                            output.accept(ModBlocks.POLISHED_SCORCHIA_PILOT);
                            output.accept(ModBlocks.POLISHED_CRIMSITE_PILOT);
                            output.accept(ModBlocks.POLISHED_OCHRUM_PILOT);
                            output.accept(ModBlocks.POLISHED_VERIDIUM_PILOT);
                            output.accept(ModBlocks.POLISHED_ASURINE_PILOT);
                        }).build());
    }
}
