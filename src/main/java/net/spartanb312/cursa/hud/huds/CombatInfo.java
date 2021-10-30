package net.spartanb312.cursa.hud.huds;

import net.spartanb312.cursa.client.FontManager;
import net.spartanb312.cursa.client.ModuleManager;
import net.spartanb312.cursa.common.annotations.ModuleInfo;
import net.spartanb312.cursa.core.setting.Setting;
import net.spartanb312.cursa.engine.AsyncRenderer;
import net.spartanb312.cursa.hud.HUDModule;
import net.spartanb312.cursa.module.Category;
import net.spartanb312.cursa.module.modules.combat.CursaAura;
import net.spartanb312.cursa.utils.ItemUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.spartanb312.cursa.utils.ChatUtil;

@ModuleInfo(name = "CombatInfo", category = Category.HUD)
public class CombatInfo extends HUDModule {

    Setting<Boolean> items = setting("Items", true);
    Setting<Boolean> goldenApple = setting("GoldenApple", true).whenTrue(items);
    Setting<Boolean> expBottle = setting("ExpBottle", true).whenTrue(items);
    Setting<Boolean> crystal = setting("Crystal", true).whenTrue(items);
    Setting<Boolean> totem = setting("Totem", true).whenTrue(items);
    Setting<Boolean> obsidian = setting("Obsidian", true).whenTrue(items);

    Setting<Boolean> crystalAura = setting("Crystal Aura", true);
    Setting<Boolean> strength = setting("Strength", false);
    Setting<Boolean> weakness = setting("Weakness", false);

    public CombatInfo() {
        asyncRenderer = new AsyncRenderer() {
            @Override
            public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
                int maxWidth = 10;
                int startY = y;
                if (items.getValue()) {
                    if (goldenApple.getValue()) {
                        String text = "GAP " + getColoredCount(ItemUtils.getItemCount(Items.GOLDEN_APPLE), 64);
                        maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                        drawAsyncString(text, x, startY);
                        startY += FontManager.getHeight();
                    }
                    if (expBottle.getValue()) {
                        String text = "EXP " + getColoredCount(ItemUtils.getItemCount(Items.EXPERIENCE_BOTTLE), 64);
                        maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                        drawAsyncString(text, x, startY);
                        startY += FontManager.getHeight();
                    }
                    if (crystal.getValue()) {
                        String text = "CRY " + getColoredCount(ItemUtils.getItemCount(Items.END_CRYSTAL), 64);
                        maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                        drawAsyncString(text, x, startY);
                        startY += FontManager.getHeight();
                    }
                    if (totem.getValue()) {
                        String text = "TOU " + getColoredCount(ItemUtils.getItemCount(Items.TOTEM_OF_UNDYING), 1);
                        maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                        drawAsyncString(text, x, startY);
                        startY += FontManager.getHeight();
                    }
                    if (obsidian.getValue()) {
                        String text = "OBS " + getColoredCount(ItemUtils.getItemCount(Item.getItemFromBlock(Blocks.OBSIDIAN)), 64);
                        maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                        drawAsyncString(text, x, startY);
                        startY += FontManager.getHeight();
                    }
                }

                if (startY != y) startY += FontManager.getHeight();

                if (crystalAura.getValue()) {
                    String text = "CA " + (ModuleManager.getModule(CursaAura.class).isEnabled() ? ChatUtil.colored("aON") : ChatUtil.colored("cOFF"));
                    maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                    drawAsyncString(text, x, startY);
                    startY += FontManager.getHeight();
                }
                if (strength.getValue()) {
                    String text = (mc.player.isPotionActive(MobEffects.STRENGTH) ? ChatUtil.colored("a") : ChatUtil.colored("f")) + "STR";
                    maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                    drawAsyncString(text, x, startY);
                    startY += FontManager.getHeight();
                }
                if (weakness.getValue()) {
                    String text = (mc.player.isPotionActive(MobEffects.WEAKNESS) ? ChatUtil.colored("c") : ChatUtil.colored("f")) + "WEK";
                    maxWidth = Math.max(maxWidth, FontManager.getWidth(text));
                    drawAsyncString(text, x, startY);
                    startY += FontManager.getHeight();
                }

                if (startY == y) startY += FontManager.getHeight();

                width = maxWidth;
                height = startY - y;
            }
        };
    }

    private String getColoredCount(int count, int stackSize) {
        if (count < stackSize) return ChatUtil.colored("c") + count;
        else if (count < stackSize * 2) return ChatUtil.colored("e") + count;
        else return ChatUtil.colored("a") + count;
    }

    @Override
    public void onHUDRender(ScaledResolution resolution) {
        asyncRenderer.onRender();
    }

}
