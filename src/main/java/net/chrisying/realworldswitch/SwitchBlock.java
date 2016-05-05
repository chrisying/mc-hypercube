package net.chrisying.realworldswitch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.net.HttpURLConnection;
import java.net.URL;

public class SwitchBlock extends Block {

    public final String name = "switchBlock";
    private int power;

    public SwitchBlock (Material material) {
        super(material);

        setUnlocalizedName(RealWorldSwitch.MODID + "_" + name);

        // TODO: some of these are probably unnecessary
        setHardness(4.0F);
        setStepSound((Block.soundTypeGlass));
        setUnlocalizedName("switchBlock");
        setCreativeTab(CreativeTabs.tabRedstone);
        setHarvestLevel("pickaxe", 3); // Doesn't work?
        setLightLevel(0.0F);

        power = 0;
    }

    @Override
    public void onNeighborBlockChange(World w, BlockPos pos, IBlockState state, Block b) {
        int powernow = w.isBlockIndirectlyGettingPowered(pos);
        if (powernow != power && !w.isRemote) {
            power = powernow;
            setLightLevel(power * 1.0F / 16);
            String url = String.format("http://128.237.221.69:8080/set?n=%d", power);
            URL obj = null;
            try {
                obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.printf("\nSent GET request with power %d\nReceived response code %d\n", power, responseCode);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            w.markBlockForUpdate(pos);
        }
    }

    public String getName() {
        return name;
    }

}
