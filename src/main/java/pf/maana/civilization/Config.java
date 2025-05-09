package pf.maana.civilization;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Civilization.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.ConfigValue<String> NORMAL_RESOURCE_WORLD_ID = BUILDER
            .comment("Resource Normal World Id")
            .define("mw.resourceNormalWorldId", "civ:normal");

    static final ModConfigSpec SPEC = BUILDER.build();

    public static String normalResourceWorldId;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        normalResourceWorldId = NORMAL_RESOURCE_WORLD_ID.get();
    }
}
