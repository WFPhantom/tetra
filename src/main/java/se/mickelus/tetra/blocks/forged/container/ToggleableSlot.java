package se.mickelus.tetra.blocks.forged.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ToggleableSlot extends SlotItemHandler {
    private boolean isEnabled = true;

    public ToggleableSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    public void toggle(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public boolean mayPickup(Player player) {
        return isEnabled && super.mayPickup(player);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return isEnabled && super.mayPlace(stack);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
