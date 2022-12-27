package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.arena.Arena;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignsManager {
    @Getter
    private static final SignsManager manager = new SignsManager();

    public void updateSigns(Arena arena) {
        if (arena == null) {
            return;
        }
        for (Block sign : arena.getSigns()) {
            if (!(sign.getState() instanceof Sign)) {
                return;
            }
            String path = "", data = "";
            switch (arena.getStatus()) {
                case WAITING:
                    path = "Status.Waiting.Material";
                    data = "Status.Waiting.Data";
                    break;
                case STARTING:
                    path = "Status.Starting.Material";
                    data = "Status.Starting.Data";
                    break;
                case PLAYING:
                    path = "Status.Playing.Material";
                    data = "Status.Playing.Data";
                    break;
                case ENDING:
                    path = "Status.Ending.Material";
                    data = "Status.Ending.Data";
                    break;
                case RESTARTING:
                    path = "Status.Restarting.Material";
                    data = "Status.Restarting.Data";
                    break;
                case DISABLED:
                    path = "Status.Disabled.Material";
                    data = "Status.Disabled.Data";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arena.getStatus());
            }
            Main.VERSION_HANDLER.setBackground(sign.getState(), Material.valueOf(Main.getSignsConfiguration().getString(path)));
            Main.VERSION_HANDLER.setBlockData(sign.getState(), (byte) Main.getSignsConfiguration().getInt(data));
            arena.refreshSigns();
        }
    }
}
