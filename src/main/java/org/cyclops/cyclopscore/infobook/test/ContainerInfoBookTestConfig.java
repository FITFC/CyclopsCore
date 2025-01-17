package org.cyclops.cyclopscore.infobook.test;

import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.cyclops.cyclopscore.CyclopsCore;
import org.cyclops.cyclopscore.client.gui.ScreenFactorySafe;
import org.cyclops.cyclopscore.config.extendedconfig.GuiConfig;
import org.cyclops.cyclopscore.inventory.container.ContainerTypeData;

/**
 * Config for {@link ContainerInfoBookTest}.
 * @author rubensworks
 */
public class ContainerInfoBookTestConfig extends GuiConfig<ContainerInfoBookTest> {

    public ContainerInfoBookTestConfig() {
        super(CyclopsCore._instance,
                "test_infobook",
                eConfig -> new ContainerTypeData<>(ContainerInfoBookTest::new));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public <U extends Screen & MenuAccess<ContainerInfoBookTest>> MenuScreens.ScreenConstructor<ContainerInfoBookTest, U> getScreenFactory() {
        return new ScreenFactorySafe<>(ContainerScreenInfoBookTest::new);
    }

}
