package org.cyclops.cyclopscore;

import org.cyclops.cyclopscore.helper.MinecraftHelpers;

/**
 * Class that can hold basic static things that are better not hard-coded
 * like mod details, texture paths, ID's...
 * @author rubensworks
 */
public final class Reference {

    // Mod info
    public static final String MOD_ID = "cyclopscore";
    public static final String GA_TRACKING_ID = "UA-65307010-1";
    public static final String VERSION_URL = "https://raw.githubusercontent.com/CyclopsMC/Versions/master/" + MinecraftHelpers.getMinecraftVersionMajorMinor() + "/CyclopsCore.txt";

    // Mod ID's
    public static final String MOD_VANILLA = "minecraft";
    public static final String MOD_BAUBLES = "baubles";
    public static final String MOD_CURIOS = "curios";
    public static final String MOD_JEI = "jei";
    public static final String MOD_COMMONCAPABILITIES = "commoncapabilities";

    // Paths
    public static final String TEXTURE_PATH_PARTICLES = "textures/particle/";

}
