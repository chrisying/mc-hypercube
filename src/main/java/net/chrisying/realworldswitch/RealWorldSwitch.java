package net.chrisying.realworldswitch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.init.Items;

@Mod(modid=RealWorldSwitch.MODID, name=RealWorldSwitch.MODNAME, version=RealWorldSwitch.MODVER)
public class RealWorldSwitch {
    public static final String MODID = "realworldswitch";
    public static final String MODNAME = "Real World Switch";
    public static final String MODVER = "0.0.0";

    public static Block switchBlock;

    @Instance(value = RealWorldSwitch.MODID)
    public static RealWorldSwitch instance;

    @SidedProxy(clientSide="net.chrisying.realworldswitch.client.ClientProxy", serverSide="net.chrisying.realworldswitch.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        switchBlock = new SwitchBlock(Material.ground);
        GameRegistry.registerBlock(switchBlock, "switchBlock");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        GameRegistry.addRecipe(new ItemStack(switchBlock), new Object[]{
                "AAA",
                "ABA",
                "CCC",
                'A', Items.glowstone_dust,
                'B', Items.nether_star,
                'C', Items.redstone
        });

        if (event.getSide() == Side.CLIENT) {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            renderItem.getItemModelMesher().register(Item.getItemFromBlock(switchBlock), 0, new ModelResourceLocation(RealWorldSwitch.MODID + ":" + ((SwitchBlock) switchBlock).getName(), "inventory"));

        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
