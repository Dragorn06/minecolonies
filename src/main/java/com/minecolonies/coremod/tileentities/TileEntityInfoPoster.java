package com.minecolonies.coremod.tileentities;

import net.minecraft.command.CommandResultStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityInfoPoster extends TileEntity
{
    public final ITextComponent[] signText =
      new ITextComponent[] {new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};

    private final CommandResultStats stats = new CommandResultStats();

    @Override
    public void readFromNBT(final CompoundNBT compound)
    {
        super.readFromNBT(compound);

        for (int i = 0; i < signText.length; ++i)
        {
            final String s = compound.getString("Text" + (i + 1));
            final ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
            this.signText[i] = itextcomponent;
        }

        this.stats.readStatsFromNBT(compound);
    }

    @Override
    public CompoundNBT writeToNBT(final CompoundNBT compound)
    {
        super.writeToNBT(compound);

        for (int i = 0; i < signText.length; ++i)
        {
            final String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            compound.putString("Text" + (i + 1), s);
        }

        this.stats.writeStatsToNBT(compound);
        return compound;
    }

    @Override
    protected void setWorldCreate(final World worldIn)
    {
        this.setWorld(worldIn);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0x9, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.writeToNBT(new CompoundNBT());
    }

    /**
     * Return the stats of the poster.
     *
     * @return the stats.
     */
    public CommandResultStats getStats()
    {
        return this.stats;
    }
}
