package vapourdrive.primitive_quarry.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigSettings {
    public static final String CATEGORY_MOD = "primitive_quarry";

    public static final ForgeConfigSpec SERVER_CONFIG;
    //    public static ForgeConfigSpec CLIENT_CONFIG;
    public static final String SUBCATEGORY_PRIMITIVE_QUARRY = "quarry";
    public static ForgeConfigSpec.IntValue PRIMITIVE_QUARRY_FUEL_STORAGE;
    public static ForgeConfigSpec.IntValue PRIMITIVE_QUARRY_FUEL_TO_WORK;
    public static ForgeConfigSpec.IntValue PRIMITIVE_QUARRY_PROCESS_TIME;
    public static ForgeConfigSpec.IntValue PRIMITIVE_QUARRY_MAX_RADIUS;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
//        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Primitive Quarry Settings").push(CATEGORY_MOD);

        setupFirstBlockConfig(SERVER_BUILDER);
//        setupFirstBlockConfig(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
//        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Primitive Quarry Settings").push(SUBCATEGORY_PRIMITIVE_QUARRY);
        PRIMITIVE_QUARRY_FUEL_STORAGE = SERVER_BUILDER.comment("Fuel Storage for the Primitive Quarry").defineInRange("quarryFuelStorage", 2560000, 5, 10000000);
        PRIMITIVE_QUARRY_FUEL_TO_WORK = SERVER_BUILDER.comment("Fuel to do one unit of work, mining").defineInRange("quarryFuelConsumption", 4000, 10, 10000);
        PRIMITIVE_QUARRY_PROCESS_TIME = SERVER_BUILDER.comment("Ticks between mining").defineInRange("quarryTicksBetweenMining", 10, 1, 100);
        PRIMITIVE_QUARRY_MAX_RADIUS = SERVER_BUILDER.comment("Max Radius for the Primitive Quarry").defineInRange("quarryMiningRadius", 7, 1, 16);
        SERVER_BUILDER.pop();

    }

}
