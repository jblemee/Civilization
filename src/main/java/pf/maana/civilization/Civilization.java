package pf.maana.civilization;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Civilization.MODID)
public class Civilization {
    public static final String MODID = "civilization";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Civilization(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Civilization) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("CIVILIZATION SETUP");

        LOGGER.info("Normal Resource World ID: " + Config.normalResourceWorldId);
    }

    @SubscribeEvent
    public void onCommandRegistration(RegisterCommandsEvent event) {
        LOGGER.info("Registering commands");

        GooeyButton mainWorldButton = GooeyButton.builder()
                .display(new ItemStack(Items.GRASS_BLOCK))
                .with(DataComponents.CUSTOM_NAME, Component.literal("Main World").withColor(0x32a852))
                .onClick((buttonAction) -> {
                    ServerPlayer player = buttonAction.getPlayer();
                    MinecraftServer server = player.getServer();
                    if(server == null) return;
                    server.getCommands().performPrefixedCommand(player.createCommandSourceStack(), "/mw tp minecraft:overworld");
                })
                .build();

        GooeyButton normalResourceWorldButton = GooeyButton.builder()
                .display(new ItemStack(Items.STONE))
                .with(DataComponents.CUSTOM_NAME, Component.literal("Resource World").withColor(0x32a852))
                .onClick((buttonAction) -> {
                    ServerPlayer player = buttonAction.getPlayer();
                    MinecraftServer server = player.getServer();
                    if(server == null) return;
                    server.getCommands().performPrefixedCommand(player.createCommandSourceStack(), "/mw tp "+Config.normalResourceWorldId);
                })
                .build();

        GooeyButton border = GooeyButton.builder()
                .display(new ItemStack(Items.BLACK_STAINED_GLASS_PANE))
                .with(DataComponents.CUSTOM_NAME, Component.empty())
                .build();

        Template template = ChestTemplate.builder(3)
                .border(0, 0, 3, 9, border)
                .set(10, mainWorldButton)
                .set(16, normalResourceWorldButton)
                .build();

        Page page = new GooeyPage(template, null, Component.literal("GooeyLibs Test"), null, null);

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("civilization")
                        .executes(context -> {
                            try {
                                ServerPlayer source = context.getSource().getPlayerOrException();
                                UIManager.openUIForcefully(source, page);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return 0;
                        })
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
