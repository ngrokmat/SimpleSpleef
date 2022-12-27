package io.thadow.simplespleef.api.storage.mysql;

import io.thadow.simplespleef.api.playerdata.PlayerData;

public interface Callback {

    public void onEnd(PlayerData playerData);

}
