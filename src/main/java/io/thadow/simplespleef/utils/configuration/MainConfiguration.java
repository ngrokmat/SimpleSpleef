package io.thadow.simplespleef.utils.configuration;

import com.google.common.collect.Lists;
import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainConfiguration extends ConfigurationFile {

    public MainConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Configuration.Storage.Type", "LOCAL");
            configuration.addDefault("Configuration.Storage.Local.Save Every", 10);
            configuration.addDefault("Configuration.Storage.MySQL.Host", "host");
            configuration.addDefault("Configuration.Storage.MySQL.Port", 1313);
            configuration.addDefault("Configuration.Storage.MySQL.Database", "database");
            configuration.addDefault("Configuration.Storage.MySQL.Username", "username");
            configuration.addDefault("Configuration.Storage.MySQL.Password", "password");
            configuration.addDefault("Configuration.Storage.MySQL.SSL", false);

            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Material", "EMERALD");
            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Slot", 1);
            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Name", "&a&lPlay &7(Right click)");
            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Unbreakable", true);
            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Amount", 1);
            configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Lore", Arrays.asList("&7Use this item to", "&7select an arena and play"));

            configuration.addDefault("Configuration.Parties.Expire Time", 60);
            configuration.addDefault("Configuration.Parties.Max Size", 4);

            configuration.addDefault("Configuration.Arenas.Status.Waiting", "&aWAITING");
            configuration.addDefault("Configuration.Arenas.Status.Starting", "&6STARTING");
            configuration.addDefault("Configuration.Arenas.Status.Playing", "&cPLAYING");
            configuration.addDefault("Configuration.Arenas.Status.Ending", "&2ENDING");
            configuration.addDefault("Configuration.Arenas.Status.Restarting", "&1RESTARTING");
            configuration.addDefault("Configuration.Arenas.Status.Disabled", "&1DISABLED");

            configuration.addDefault("Configuration.Arenas.Nobody", "Nobody");

            configuration.addDefault("Messages.Arenas.Party Member In Game", "&6&lParty &8&l>> &cUno de los integrantes de la arena sigue en juego!");

            configuration.addDefault("Messages.Arenas.Not Enough Space", "&6&lParty &8&l>> &cNo hay espacio suficiente para los integrantes de tu fiesta.");

            configuration.addDefault("Messages.Arenas.Player Join", "&7%player% &ese ha unido (&b%current%&e/%b%max%");
            configuration.addDefault("Messages.Arenas.Player Leave", "&7%player% &eha salido!");
            configuration.addDefault("Messages.Arenas.Player Death", "&7%player% &cha muerto!");

            configuration.addDefault("Messages.Arenas.Starting.Starting In", "&eIniciando en &a%time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Titles Settings.Fade In", 10);
            configuration.addDefault("Messages.Arenas.Starting.Titles Settings.Stay", 20);
            configuration.addDefault("Messages.Arenas.Starting.Titles Settings.Fade Out", 10);

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Sound.Sound", "NOTE_PLING:10:10");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 10.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Sound.Sound", "NOTE_PLING:10:10");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 5.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Sound.Sound", "NOTE_PLING:10:10");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 4.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Sound.Sound", "NOTE_PLING:10:10");            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Sound.Sound", "NOTE_PLING:10:10");            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Message", "&eIniciando en %time% &esegundos");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Sound.Sound", "NOTE_PLING:10:10");            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Titles.Title", "&c%time%");
            configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Titles.Sub Title", "");

            configuration.addDefault("Messages.Arenas.Started.Message", Arrays.asList("Iniciamos", "xdd"));
            configuration.addDefault("Messages.Arenas.Started.Sound.Enabled", true);
            configuration.addDefault("Messages.Arenas.Started.Sound.Sound", "NOTE_PLING:10:10");
            configuration.addDefault("Messages.Arenas.Started.Titles Settings.Fade In", 10);
            configuration.addDefault("Messages.Arenas.Started.Titles Settings.Stay", 20);
            configuration.addDefault("Messages.Arenas.Started.Titles Settings.Fade Out", 10);
            configuration.addDefault("Messages.Arenas.Started.Titles.Enabled", true);
            configuration.addDefault("Messages.Arenas.Started.Titles.Title", "&cIniciamos");
            configuration.addDefault("Messages.Arenas.Started.Titles.Sub Title", "&eBuena suerte!");

            configuration.addDefault("Messages.Arenas.Starting.Countdown Stopped", "&cNo hay jugadores suficientes para iniciar la arena. La cuenta regresiva a sido parada.");

            configuration.addDefault("Messages.Commands.Build Command.Can't Use In Arena", "&cNo puedes usar el modo de construcción si estas en una arena!");
            configuration.addDefault("Messages.Commands.Build Command.Build Mode Enabled", "&aTu modo de construcción ha sido activado!");
            configuration.addDefault("Messages.Commands.Build Command.Build Mode Disabled", "&cTu modo de construcción ha sido desactivado.");

            configuration.addDefault("Messages.Commands.Leave Command.Only In Arena", "&cEste comando solo se puede ejecutar en arena.");

            List<String> mainUsage = new ArrayList<>();
            mainUsage.add("/sp createArena (id)");
            mainUsage.add("/sp deleteArena (id)");
            configuration.addDefault("Messages.Commands.Main Command.Usage", mainUsage);

            List<String> partyUsage = new ArrayList<>();
            partyUsage.add("&7&m---------------------------------------------------");
            partyUsage.add("&6&lParty");
            partyUsage.add("&e/party public - private &7- Cambia el tipo de fiesta.");
            partyUsage.add("&e/party create &7- Crear una fiesta.");
            partyUsage.add("&e/party invite (player) &7- Invitar a un jugador.");
            partyUsage.add("&e/party kick (player) &7- Expulsar a un jugador.");
            partyUsage.add("&e/party leader (player) &7- Dar el lider a otro jugador.");
            partyUsage.add("&e/party leave &7- Salir de la fiesta");
            partyUsage.add("&7&m---------------------------------------------------");
            configuration.addDefault("Messages.Commands.Party Command.Usage", partyUsage);

            configuration.addDefault("Messages.Commands.Party Command.Can't Use In Game", "&6&lParty &8&l>> &cNo puedes usar este comando estando en una arena!");
            configuration.addDefault("Messages.Commands.Party Command.Player Offline", "&6&lParty &8&l>> &e%target% &cno esta conectado!");
            configuration.addDefault("Messages.Commands.Party Command.Already In Party", "&6&lParty &8&l>> &aYa te estas en una party!");
            configuration.addDefault("Messages.Commands.Party Command.Not In Party", "&6&lParty &8&l>> &c¡No te encuentras en una fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Only Leader", "&6&lParty &8&l>> &cSolo el lider de la fiesta puede hacer eso.");
            configuration.addDefault("Messages.Commands.Party Command.Already In Public", "&6&lParty &8&l>> &eLa fiesta ya es pública.");
            configuration.addDefault("Messages.Commands.Party Command.Changed To Public", "&6&lParty &8&l>> &aLa fiesta ahora es pública.");
            configuration.addDefault("Messages.Commands.Party Command.Already In Private", "&6&lParty &8&l>> &eLa fiesta ya es privada.");
            configuration.addDefault("Messages.Commands.Party Command.Changed To Private", "&6&lParty &8&l>> &aLa fiesta ahora es privada.");
            configuration.addDefault("Messages.Commands.Party Command.Party Created", "&6&lParty &l&8>> &e!Has creado una fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Accept Usage", "&6&lParty &8&l>> &e/party accept (player)");
            configuration.addDefault("Messages.Commands.Party Command.Invite Usage", "&6&lParty &8&l>> &e/party invite (player)");
            configuration.addDefault("Messages.Commands.Party Command.Not Invited", "&6&lParty &8&l>> &e%target% &cno te ha invitado a su fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Not Leader", "&6&lParty &8&l>> &e%target% &cno es el lider de su fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.No Party Invited", "&6&lParty &8&l>> &e%target% &cno tiene una fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Can't Invite Self", "&6&lParty &8&l>> &cNo puedes invitarte a ti mismo!");
            configuration.addDefault("Messages.Commands.Party Command.Invited Already In Party", "&6&lParty &8&l>> &e%target% &cya se encuentra en una fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Party Full", "&6&lParty &8&l>> &cLa fiesta esta llena.");
            configuration.addDefault("Messages.Commands.Party Command.Invite Sent", "&6&lParty &8&l>> &aInvitación enviada!");
            configuration.addDefault("Messages.Commands.Party Command.Invite Message", "&6&lParty &8&l>> &e%from% Te ha invitado a su fiesta! Tienes &a%time% &epara aceptar la invitación. &aClick aqui!");
            configuration.addDefault("Messages.Commands.Party Command.Invite Message Hover", "&aClick to join!");
            configuration.addDefault("Messages.Commands.Party Command.Invite Expired", "&6&lParty &8&l>> &eLa invitación de &6%from% &eha expirado.");
            configuration.addDefault("Messages.Commands.Party Command.Kick Usage", "&6&lParty &8&l>> &e/party kick (player)");
            configuration.addDefault("Messages.Commands.Party Command.Can't Kick Self", "&6&lParty &8&l>> &cNo puedes expulsarte a ti mismo.");
            configuration.addDefault("Messages.Commands.Patty Command.Kick Message", "&6&lParty &8&l>> &cHas sido expulsado de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Player Kicked", "&6&lParty &8&l>> &e%target% ha sido expulsado de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Leader Usage", "&6&lParty &8&l>> &e/party leader (player)");
            configuration.addDefault("Messages.Commands.Party Command.Already Leader", "&6&lParty &8&l>> &aYa eres el lider de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Not In Members", "&6&lParty &8&l>> &e%target% no esta en tu fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Leader Set", "&6&lParty &8&l>> &aAhora eres el lider de la fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Leader Changed", "&6&lParty &8&l>> &e%target% ahora es el lider de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Invite Accept", "&6&lParty &8&l>> &aTe has unido a la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Player Joined", "&6&lParty &8&l>> &e%target% se ha unido a la fiesta!");
            configuration.addDefault("Messages.Commands.Party Command.Party Leave", "&6&lParty &l&8>> &eHas salido de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Player Left", "&6&lParty &8&l>> &e%target% ha salido de la fiesta.");
            configuration.addDefault("Messages.Commands.Party Command.Party Disband Message", "&6&lParty &8&l>> &cLa party a sido disuelta.");

            configuration.addDefault("Messages.Signs.Sign Added", "Sign added: %arenaID%");
            configuration.addDefault("Messages.Signs.Sign Removed", "Sign removed: %arenaID%");

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
