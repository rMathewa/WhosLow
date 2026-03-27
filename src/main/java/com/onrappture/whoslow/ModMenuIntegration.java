package com.onrappture.whoslow;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            GlowConfig cfg = GlowConfig.get();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.literal("WhosLow?"));

            ConfigCategory general = builder.getOrCreateCategory(Component.literal("Settings"));
            ConfigEntryBuilder eb = builder.entryBuilder();

            general.addEntry(eb.startBooleanToggle(Component.literal("Enable Mod"), cfg.isEnabled())
                    .setDefaultValue(true)
                    .setSaveConsumer(cfg::setEnabled)
                    .build());

            general.addEntry(eb.startColorField(Component.literal("Your / Higher Health Color"), cfg.getHigherHealthColor())
                    .setDefaultValue(0x55FF55)
                    .setSaveConsumer(cfg::setHigherHealthColor)
                    .build());

            general.addEntry(eb.startColorField(Component.literal("Enemy / Lower Health Color"), cfg.getLowerHealthColor())
                    .setDefaultValue(0xFF5555)
                    .setSaveConsumer(cfg::setLowerHealthColor)
                    .build());

            general.addEntry(eb.startColorField(Component.literal("Equal Health Color"), cfg.getEqualHealthColor())
                    .setDefaultValue(0xFFFF55)
                    .setSaveConsumer(cfg::setEqualHealthColor)
                    .build());

            builder.setSavingRunnable(GlowConfig::save);

            return builder.build();
        };
    }
}

// #made by mathew love u all <3
