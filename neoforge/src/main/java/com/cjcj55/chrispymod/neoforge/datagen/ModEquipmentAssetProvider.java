package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.item.ModItems;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Writes `assets/chrispymod/equipment/<material>.json`, pointing at `textures/entity/equipment/humanoid[_leggings]/<material>.png`.
 * There is no baby layer because no baby armor textures exist yet.
 */
public class ModEquipmentAssetProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetProvider(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> assets = new LinkedHashMap<>();
        for (ModItems.ArmorSet set : ModItems.armorSets()) {
            EquipmentClientInfo.Layer layer = new EquipmentClientInfo.Layer(ChrispyMod.id(set.material()));
            assets.put(set.asset(), EquipmentClientInfo.builder()
                    .addLayers(EquipmentClientInfo.LayerType.HUMANOID, layer)
                    .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, layer)
                    .build());
        }
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, pathProvider::json, assets);
    }

    @Override
    public String getName() {
        return "Chrispy Mod Equipment Assets";
    }
}
