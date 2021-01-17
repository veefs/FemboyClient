package com.deneb.client.features.modules.render;

import com.deneb.client.client.FriendManager;
import com.deneb.client.features.Category;
import com.deneb.client.features.Module;
import com.deneb.client.utils.ChatUtil;
import com.deneb.client.value.IValue;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

/**
 * Created by 086 on 8/04/2018.
 */
@Module.Info(name = "ExtraTab", description = "Expands the player tab menu", category = Category.RENDER)
public class ExtraTab extends Module {

    public IValue tabSize = setting("Players",80,1,200);

    public static ExtraTab INSTANCE;

    public ExtraTab() {
        ExtraTab.INSTANCE = this;
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (FriendManager.isFriend(dname)) return String.format("%sa%s", ChatUtil.SECTIONSIGN, dname);
        return dname;
    }

}
