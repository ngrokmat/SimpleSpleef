package io.thadow.simplespleef.utils.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainConfiguration extends ConfigurationFile {

    public MainConfiguration(String configName, String dir) {
        super(configName, dir);
        YamlConfiguration configuration = getConfiguration();

        configuration.addDefault("Configuration.Storage.Type", "LOCAL");
        configuration.addDefault("Configuration.Storage.Local.Save Every", 10);
        configuration.addDefault("Configuration.Storage.MySQL.Host", "host");
        configuration.addDefault("Configuration.Storage.MySQL.Port", 1313);
        configuration.addDefault("Configuration.Storage.MySQL.Database", "database");
        configuration.addDefault("Configuration.Storage.MySQL.Username", "username");
        configuration.addDefault("Configuration.Storage.MySQL.Password", "password");
        configuration.addDefault("Configuration.Storage.MySQL.SSL", false);

        configuration.addDefault("Configuration.Lobby.Cancel Damage", true);
        configuration.addDefault("Configuration.Lobby.Allow Flight", false);
        configuration.addDefault("Configuration.Lobby.Set Flying", false);
        configuration.addDefault("Configuration.Lobby.Clear Inventory", true);
        configuration.addDefault("Configuration.Lobby.Clear Armor Contents", true);
        configuration.addDefault("Configuration.Lobby.GameMode", "ADVENTURE");

        configuration.addDefault("Configuration.Global.No Hunger", true);

        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Material", "EMERALD");
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Slot", 1);
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Name", "&a&lPlay &7(Right click)");
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Lobby.Arena Selector Item.Lore", Arrays.asList("&7Use this item to", "&7select an arena and play"));

        configuration.addDefault("Configuration.Items.Lobby.Party Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Material", "CAKE");
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Slot", 8);
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Name", "&d&lParty &7(Right click)");
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Lobby.Party Item.Lore", Arrays.asList("&7Use this item to", "&7create a party, manage your party", "&7or join to a public party."));

        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Material", "BED");
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Slot", 9);
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Name", "&cLeave");
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Server To Connect", "hub-1");
        configuration.addDefault("Configuration.Items.Lobby.Leave Item.Lore", Collections.singletonList("&7Use this item to leave this server."));

        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Material", "BED");
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Slot", 9);
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Name", "&cLeave");
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Waiting Items.Leave Item.Lore", "&7Use this item to leave.");

        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Material", "BED");
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Slot", 9);
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Name", "&cLeave");
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Playing Items.Leave Item.Lore", "&7Use this item to leave.");

        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Material", "PAPER");
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Slot", 8);
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Name", "&ePlay again!");
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Spectator Items.Play Again Item.Lore", "&7Right click to play again!");

        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Enabled", true);
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Material", "BED");
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Slot", 9);
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Name", "&cLeave");
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Unbreakable", true);
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Amount", 1);
        configuration.addDefault("Configuration.Items.Spectator Items.Leave Item.Lore", "&7Use this item to leave.");

        configuration.addDefault("Configuration.Parties.Invite Expire Time", 60);
        configuration.addDefault("Configuration.Parties.Max Size", 4);

        configuration.addDefault("Configuration.Arenas.Status.Waiting", "&aWAITING");
        configuration.addDefault("Configuration.Arenas.Status.Starting", "&6STARTING");
        configuration.addDefault("Configuration.Arenas.Status.Playing", "&cPLAYING");
        configuration.addDefault("Configuration.Arenas.Status.Ending", "&2ENDING");
        configuration.addDefault("Configuration.Arenas.Status.Restarting", "&1RESTARTING");
        configuration.addDefault("Configuration.Arenas.Status.Disabled", "&1DISABLED");
        configuration.addDefault("Configuration.Arenas.Per Arena Chat", true);
        configuration.addDefault("Configuration.Arenas.Clear Inventory", true);
        configuration.addDefault("Configuration.Arenas.Clear Armor Contents", true);
        configuration.addDefault("Configuration.Arenas.Allow Flight", false);
        configuration.addDefault("Configuration.Arenas.Set Flying", false);
        configuration.addDefault("Configuration.Arenas.Waiting GameMode", "ADVENTURE");
        configuration.addDefault("Configuration.Arenas.Playing GameMode", "SURVIVAL");
        configuration.addDefault("Configuration.Arenas.Ending GameMode", "ADVENTURE");
        configuration.addDefault("Configuration.Arenas.Spectating GameMode", "ADVENTURE");
        configuration.addDefault("Configuration.Arenas.Winner Fireworks Ticks", 5);
        configuration.addDefault("Configuration.Arenas.Draw.Add Lose", false);
        configuration.addDefault("Configuration.Arenas.On Leave.Add Lose", true);
        configuration.addDefault("Configuration.Arenas.Nobody", "Nobody");

        configuration.addDefault("Messages.Arenas.Party Member In Game", "&6&lParty &8&l>> &cUno de los integrantes de la arena sigue en juego!");

        configuration.addDefault("Messages.Arenas.Not Enough Space", "&6&lParty &8&l>> &cNo hay espacio suficiente para los integrantes de tu fiesta.");

        configuration.addDefault("Messages.Arenas.Play Again.No Arenas Available", "&cNo hay arenas disponibles en este momento");

        configuration.addDefault("Messages.Arenas.Player Join", "&7%player% &ese ha unido (&b%current%&e/&b%max%&e)");
        configuration.addDefault("Messages.Arenas.Player Leave", "&7%player% &eha salido!");
        configuration.addDefault("Messages.Arenas.Player Death", "&7%player% &cha muerto!");

        configuration.addDefault("Messages.Arenas.Starting.Countdown Shorted", "&eLa arena se ha llenado! La arena comezara en &a%time%");
        configuration.addDefault("Messages.Arenas.Starting.Countdown Started", "&eIniciando en &a%time% &esegundos");
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
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Sound.Sound", "NOTE_PLING:10:10");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Enabled", true);
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Title", "&c%time%");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 3.Titles.Sub Title", "");

        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Message", "&eIniciando en %time% &esegundos");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Sound.Enabled", true);
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Sound.Sound", "NOTE_PLING:10:10");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Enabled", true);
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Title", "&c%time%");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 2.Titles.Sub Title", "");

        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Message", "&eIniciando en %time% &esegundo");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Sound.Enabled", true);
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Sound.Sound", "NOTE_PLING:10:10");
        configuration.addDefault("Messages.Arenas.Starting.Announce.Second 1.Titles.Enabled", true);
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

        configuration.addDefault("Messages.Commands.Main Command.Only In Arena", "&8[&6SimpleSpleef&8] &cYou only can use this command in arena.");
        configuration.addDefault("Messages.Commands.Main Command.Lobby Location Set", "&8[&6SimpleSpleef&8] &aLobby Location set successfully.");
        configuration.addDefault("Messages.Commands.Main Command.Force Started", "&8[&6SimpleSpleef&8] &aThe countdown has been started.");
        configuration.addDefault("Messages.Commands.Main Command.Arena Already Starting", "&8[&6SimpleSpleef&8] &cAlready starting!");
        configuration.addDefault("Messages.Commands.Main Command.Arena Created", "&8[&6SimpleSpleef&8] &aArena created successfully!");
        configuration.addDefault("Messages.Commands.Main Command.Arena Already Created", "&8[&6SimpleSpleef&8] &aArena already created!");
        configuration.addDefault("Messages.Commands.Main Command.Arena Deleted", "&8[&6SimpleSpleef&8] &aArena deleted successfully!");
        configuration.addDefault("Messages.Commands.Main Command.Can't Delete", "&8[&6SimpleSpleef&8] &cUnable to delete the arena, maybe is enabled or does not exist!");
        configuration.addDefault("Messages.Commands.Main Command.Arena Does Not Exist", "&8[&6SimpleSpleef&8] &cUknown Arena");
        configuration.addDefault("Messages.Commands.Main Command.Arena Is Enabled", "&8[&6SimpleSpleef&8] &aArena is enabled!");
        configuration.addDefault("Messages.Commands.Main Command.Arena Enabled", "&8[&6SimpleSpleef&8] &aArena enabled successfully.");


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
        configuration.addDefault("Messages.Commands.Party Command.Inviting Someone", "&6&lParty &8&l>> &aEscribre el nombre de la persona que quieres invitar.");
        configuration.addDefault("Messages.Commands.Party Command.Already Invited", "&6&lParty &8&l>> &cYa has inivitado a ese jugador.");
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
        configuration.addDefault("Messages.Commands.Party Command.Joined Public", "&6&lParty &8>> &aTe has unido a la fiesta de &e%leader%");
        configuration.addDefault("Messages.Commands.Party Command.Party Is Private", "&6&lParty &8>> &cLa fiesta a la que te intentas unir es privada.");
        configuration.addDefault("Messages.Commands.Party Command.No Public Parties", "&6&lParty &8>> &cNo hay fiestas publicas a las que te puedas unir.");
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
